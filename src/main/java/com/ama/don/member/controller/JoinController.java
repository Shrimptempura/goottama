package com.ama.don.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.service.JoinService;
import com.ama.don.member.service.MemberServiceInter;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class JoinController {
	
	MemberServiceInter memberServiceInter;
	
	@Autowired
	private JoinDao joinDao;
	
	@RequestMapping("join_view")
	public String join_view() {
		return "member/join_view";
	}
	
	@RequestMapping("join")
	public String join(HttpServletRequest request,Model model) {
		
		model.addAttribute("request",request);
		memberServiceInter = new JoinService(joinDao);
		
		return "member/login_view";
	}

}
