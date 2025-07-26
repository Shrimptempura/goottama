package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.service.JoinService;
import com.ama.don.member.service.SendEmailService;
import com.ama.don.member.service.ValidationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;
	private final ValidationService validationService;
	private final SendEmailService sendEmailService;

	@GetMapping("/join_view")
	public String join_view(Model model) {
		model.addAttribute("joinformDto", new JoinformDto());
		return "member/join_view";
	}

	// 회원가입
	@PostMapping("/join")
	public String join(@Valid @ModelAttribute JoinformDto joinformDto, BindingResult bindingResult, Model model,
			HttpSession session) {

		// 폼에 입력된 값 하나로 dto에 주입
		joinformDto.combineAddress();
		joinformDto.combineEmail();

		// 입력값 검증 실패 시 메시지를 model에 담아 회원가입페이지로
		if (bindingResult.hasErrors()) {
			model.addAttribute("joinformDto", joinformDto);
			// 검증 결과 콘솔에 에러출력
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println("Error in field: " + error.getField());
				System.out.println("Message: " + error.getDefaultMessage());
			}
			return "member/join_view";
		}

		// 중복 검증
		validationService.emailCheck(joinformDto, model);
		validationService.loginIdCheck(joinformDto, model);
		validationService.nicknameCheck(joinformDto, model);
		validationService.passwordCheck(joinformDto, model);
		if (model.containsAttribute("email_error") || model.containsAttribute("id_error")
				|| model.containsAttribute("nickname_error") || model.containsAttribute("pw_error")) {
			model.addAttribute("joinformDto", joinformDto);
			return "member/join_view";
		}

		// 이메일 전송
		sendEmailService.emailSendAction(joinformDto, model);
		session.setAttribute("tempJoinUser", joinformDto);

		return "member/emailSent_view";

	}

	@RequestMapping("/emailCheck")
	public String emailCheck(HttpServletRequest request, JoinformDto joinformDto, Model model, HttpSession session) {

		joinformDto = (JoinformDto) session.getAttribute("tempJoinUser");
		model.addAttribute("request", request);

		boolean isRight = validationService.emailvalidation(joinformDto, model, session);

		if (isRight == true) {
			joinService.join(joinformDto, model);
			session.removeAttribute("tempJoinUser");
			return "redirect:/login_view";
		}

		return "member/join_view";

	}
}