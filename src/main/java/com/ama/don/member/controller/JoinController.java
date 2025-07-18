package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.service.JoinService;
import com.ama.don.member.service.LoginIdCheckService;
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
	
	@RequestMapping("/")
	public String index() {
		return "list";
	}
	
	@RequestMapping("join_view")
	public String join_view() {
		return "member/join_view";
	}
	
	//수정
	//회원가입
	@RequestMapping("join")
	public String join(@Valid @ModelAttribute JoinformDto joinformDto, BindingResult bindingResult, Model model,
							HttpServletRequest request) {
		
		String email = request.getParameter("emailId")+"@"+request.getParameter("emailDomain");
		String addr = request.getParameter("addr")+"@"+request.getParameter("detailAddr");
		joinformDto.setEmail(email);
		joinformDto.setAddr(addr);
		
		//입력값 검증 실패 시 메시지를 model에 담아 회원가입페이지로 
		if (bindingResult.hasErrors()) {
			model.addAttribute("joinformDto",joinformDto);
			 for (FieldError error : bindingResult.getFieldErrors()) {
			        System.out.println("Error in field: " + error.getField());
			        System.out.println("Message: " + error.getDefaultMessage());
			    }
			return "member/join_view";
		}
		
		//아이디 중복검사
		loginIdCheckService.execute(joinformDto, model);
		if (model.containsAttribute("id_error")) {
			System.out.println("아이디 중복");
			return "member/join_view";
		}
		
		//비밀번호 검증
		passwordCheckService.execute(joinformDto, model);
		if (model.containsAttribute("pw_error")) {
			System.out.println("비밀번호 틀림");
			return "member/join_view";
		}
		
		//비밀번호 암호화,회원정보 db저장,회원가입완료
		joinService.execute(joinformDto, model);
		return "redirect:login_view";
		
		
		
	}
	

}
