package com.ama.don.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class LoginIdCheckService implements MemberServiceInter {
	
	@Autowired
	private JoinDao joinDao;

	@Override
	public void execute(JoinformDto joinformDto, Model model) {
		
		String loginId = joinformDto.getLoginId();
		
		if (joinDao.checkId(loginId)>0) {
			model.addAttribute("id_error","이미 사용중인 아이디입니다.");
			return;
		}
		
	}


}
