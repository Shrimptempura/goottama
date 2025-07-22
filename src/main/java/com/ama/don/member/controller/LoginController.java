package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ama.don.member.dto.LoginformDto;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	
	@GetMapping("/")
	public String index() {
		return "list";
	}
	
	@GetMapping("/login_view")
	public String login_view() {
		return "member/login_view";
	}
	
	@PostMapping("/login")
	public String login(@Valid@ModelAttribute LoginformDto loginformDto, BindingResult bindingResult,HttpSession session,Model model) {
		
		// 입력값 검증 실패 시 메시지를 model에 담아 로그인페이지로
		if (bindingResult.hasErrors()) {
			model.addAttribute("loginformDto", loginformDto);
			return "member/login_view";
		}
		
		//로그인 성공시 memberdto반환
		MemberDto memberDto = loginService.login(loginformDto, session);
		if (memberDto == null) {  //로그인 실패
			model.addAttribute("login_error","아이디 또는 비밀호가 틀렸습니다.");
			return "member/login_view";
		}	
		return "redirect:/";  //로그인 성공
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		loginService.logout(session);
		return "redirect:/login_view";
	}


}
