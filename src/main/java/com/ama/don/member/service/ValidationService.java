package com.ama.don.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dao.ValidationDao;
import com.ama.don.member.dto.JoinformDto;

@Service
public class ValidationService implements ValidationServiceInter {

	@Autowired
	private ValidationDao validationDao;

	@Override //이메일 중복확인
	public void emailCheck(JoinformDto joinformDto, Model model) {
		if (validationDao.checkEmail(joinformDto) > 0) {
			model.addAttribute("email_error", "이미 사용중인 이메일입니다.");
		}

	}

	@Override
	public void loginIdCheck(JoinformDto joinformDto, Model model) {
		// 아이디 중복확인
		if (validationDao.checkId(joinformDto) > 0) {
			model.addAttribute("id_error", "이미 사용중인 아이디입니다.");
		}

	}

	@Override
	public void nicknameCheck(JoinformDto joinformDto, Model model) {
		// 닉네임 중복확인
		if (validationDao.checkNickname(joinformDto) > 0) {
			model.addAttribute("nickname_error", "이미 사용중인 닉네임입니다.");
		}

	}

	@Override
	public void passwordCheck(JoinformDto joinformDto, Model model) {
		// 비밀번화 일치 확인
		if (!joinformDto.getPw().equals(joinformDto.getPw2())) {
			model.addAttribute("pw_error", "비밀번호가 일치하지 않습니다.");
		}

	}

}
