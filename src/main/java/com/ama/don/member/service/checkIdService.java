package com.ama.don.member.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;

public class checkIdService implements MemberServiceInter {
	
	private JoinDao joinDao;
	public checkIdService(JoinDao joinDao) {
		this.joinDao=joinDao;
	}

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map=model.asMap();
		String loginId = (String) map.get("loginId");
		
		int count = joinDao.checkId(loginId);
		boolean exists = count > 0;
		
		model.addAttribute("exists", exists);
		

	}

}
