package com.ama.don.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;

@Service
public class EmailCheckService implements MemberServiceInter{
	
	@Autowired
	private JoinDao joinDao;

	@Override
	public void execute(JoinformDto joinformDto, Model model) {
		
		if (joinDao.checkEmail(joinformDto) > 0) {
			model.addAttribute("email_error", "이미 사용중인 이메일입니다.");
			return;
		}
		
	}

}
