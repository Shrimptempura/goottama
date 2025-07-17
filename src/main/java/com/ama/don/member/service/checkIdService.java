package com.ama.don.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class checkIdService implements MemberServiceInter {
	
	@Autowired
	private JoinDao joinDao;

	@Override
	public void execute(Model model) {
		Map<String, Object> map=model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String loginId = request.getParameter("loginId");
		
		if (joinDao.checkId(loginId)>0) {
			model.addAttribute("id_error","이미 사용중인 아이디입니다.");
			return;
		}


	}

}
