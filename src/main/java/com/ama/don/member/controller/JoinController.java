package com.ama.don.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.service.JoinService;
import com.ama.don.member.service.MemberServiceInter;
import com.ama.don.member.service.checkIdService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JoinController {

	private final JoinService joinService;
	private final checkIdService checkIdService;
	
	@RequestMapping("/")
	public String index() {
		return "list";
	}
	
	@RequestMapping("join_view")
	public String join_view() {
		return "member/join_view";
	}
	
	//회원가입
	@RequestMapping("join")
	public String join(HttpServletRequest request,Model model) {
		
		model.addAttribute("request",request);
		
		//아이디 중복검사,에러 메세지리턴
		checkIdService.execute(model);		
		if (model.containsAttribute("id_error")) {
			return "member/join_view";
		}
		
		//회워정보 db저장,회원가입완료
		joinService.execute(model);		
		return "redirect:login_view";
	}
	

}
