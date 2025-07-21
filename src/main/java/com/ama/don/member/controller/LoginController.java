package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ama.don.member.dto.LoginformDto;
import com.ama.don.member.service.LoginService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	
	@GetMapping("login_view")
	public String login_view() {
		return "member/login_view";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute LoginformDto loginformDto,HttpSession session,Model model) {
		
		loginService.login(loginformDto, session);
		
		return null;
	}


}
