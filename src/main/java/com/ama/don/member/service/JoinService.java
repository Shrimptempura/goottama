package com.ama.don.member.service;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService implements JoinServiceInter {

	private final JoinDao joinDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void join(JoinformDto joinformDto, Model model) {
		
		// 비밀번호 암호화
		String encodedPw = bCryptPasswordEncoder.encode(joinformDto.getPw());
		joinformDto.setPw(encodedPw);

		joinDao.insertUserDetail(joinformDto);  // user_detail 테이블 insert

		joinDao.insertUserLogin(joinformDto);  // user_login 테이블 정보입력

	}
	

}
