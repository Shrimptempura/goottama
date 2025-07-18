package com.ama.don.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PasswordCheckService implements MemberServiceInter{
	
	@Autowired
	private JoinDao joinDao;

	@Override
	public void execute(JoinformDto joinformDto, Model model) {
		
		//비밀번화 일치 확인
		if (!joinformDto.getPw().equals(joinformDto.getPw2())) {
			model.addAttribute("pw_error","비밀번호가 일치하지 않습니다.");
			return;
		}
		
		
	}	

}
