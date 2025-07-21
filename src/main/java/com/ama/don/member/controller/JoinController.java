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
import com.ama.don.member.service.EmailCheckService;
import com.ama.don.member.service.JoinService;
import com.ama.don.member.service.LoginIdCheckService;
import com.ama.don.member.service.NicknameCheckService;
import com.ama.don.member.service.PasswordCheckService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;
	private final LoginIdCheckService loginIdCheckService;
	private final PasswordCheckService  passwordCheckService;
	private final NicknameCheckService nicknameCheckService;
	private final EmailCheckService emailCheckService;
	
	
	@GetMapping("join_view")
	public String join_view() {
		return "member/join_view";
	}
	
	//회원가입
	@PostMapping("join")
	public String join(@Valid @ModelAttribute JoinformDto joinformDto, BindingResult bindingResult, Model model,
							HttpServletRequest request) {
		
		//폼에 입력된 값 하나로 dto에 주입
		joinformDto.combineAddress();
		joinformDto.combineEmail();
		
		//입력값 검증 실패 시 메시지를 model에 담아 회원가입페이지로 
		if (bindingResult.hasErrors()) {
			model.addAttribute("joinformDto",joinformDto);
			//검증 결과 콘솔에 에러출력
			 for (FieldError error : bindingResult.getFieldErrors()) {
			        System.out.println("Error in field: " + error.getField());
			        System.out.println("Message: " + error.getDefaultMessage());
			    }
			return "member/join_view";
		}
		
		//아이디 중복검사
		loginIdCheckService.execute(joinformDto, model);
		if (model.containsAttribute("id_error")) {
			return "member/join_view";
		}
		
		//닉네임 중복검사
		nicknameCheckService.execute(joinformDto, model);
		if (model.containsAttribute("nickname_error")) {
			return "member/join_view";
		}
		
		//이메일 중복 검증
		emailCheckService.execute(joinformDto, model);
		if (model.containsAttribute("email_error")) {
			return "member/join_view";
		}
		
		//비밀번호 일치 확인,검증은 joinformdto에서 pattern처리
		passwordCheckService.execute(joinformDto, model);
		if (model.containsAttribute("pw_error")) {
			return "member/join_view";
		}
		
		//비밀번호 암호화,회원정보 db저장,회원가입완료
		joinService.execute(joinformDto, model);
		return "redirect:/login_view";
		
		
		
	}
	

}
