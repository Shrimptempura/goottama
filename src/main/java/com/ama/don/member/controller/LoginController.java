package com.ama.don.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.service.MemberServiceInter;

@Controller
public class LoginController {
	
	MemberServiceInter memberServiceInter;
	
	@Autowired
	private LoginDao loginDao;
	
	
	@GetMapping("login_view")
	public String login_view() {
		return "member/login_view";
	}


}
