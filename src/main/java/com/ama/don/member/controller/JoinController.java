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

@Controller
public class JoinController {
	
	MemberServiceInter memberServiceInter;
	
	@Autowired
	private JoinDao joinDao;
	
	@RequestMapping("join_view")
	public String join_view() {
		return "member/join_view";
	}
	
	//아이디 중복확인
	@RequestMapping("checkId")
	@ResponseBody
	public Map<String, Boolean> checkId(@RequestParam String loginId,Model model) {	
		
		model.addAttribute("loginId",loginId);
		memberServiceInter = new checkIdService(joinDao);
		memberServiceInter.execute(model);
		
		Map<String, Object> map = model.asMap();
	    Boolean exists = (Boolean) map.get("exists");
	    
	    Map<String, Boolean> result = new HashMap<>();
	    result.put("exists", exists);
		
		return result;
	}
	
	@RequestMapping("join")
	public String join(HttpServletRequest request,Model model) {
		
		model.addAttribute("request",request);
		memberServiceInter = new JoinService(joinDao);
		memberServiceInter.execute(model);
		
		return "member/login_view";
	}

}
