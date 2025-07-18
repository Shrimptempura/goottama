package com.ama.don.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;

@Service
public class NicknameCheckService implements MemberServiceInter {

	@Autowired
	private JoinDao joinDao;

	@Override
	public void execute(JoinformDto joinformDto, Model model) {
		
		// 닉네임 중복확인
		if (joinDao.checkNickname(joinformDto) > 0) {
			model.addAttribute("nickname_error", "이미 사용중인 닉네임입니다.");
			return;
		}

	}

}
