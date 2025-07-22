package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService implements JoinServiceInter {

	private final JoinDao joinDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ValidationService validationService;

	@Override
	public void join(JoinformDto joinformDto, Model model) {
		
		//중복 검증
		validationService.emailCheck(joinformDto, model);
		validationService.loginIdCheck(joinformDto, model);
		validationService.nicknameCheck(joinformDto, model);
		validationService.passwordCheck(joinformDto, model);
		
		if (model.containsAttribute("email_error") ||
			model.containsAttribute("id_error") ||
			model.containsAttribute("nickname_error") ||
			model.containsAttribute("pw_error")) {
			model.addAttribute("joinformDto",joinformDto);
			return;
		}
		
		// 비밀번호 암호화
		String encodedPw = bCryptPasswordEncoder.encode(joinformDto.getPw());
		joinformDto.setPw(encodedPw);

		joinDao.insertUserDetail(joinformDto);  // user_detail 테이블 insert

		joinDao.insertUserLogin(joinformDto);  // user_login 테이블 정보입력

	}

}
