package com.ama.don.member.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.utill.EmailSHA;
import com.ama.don.member.utill.Gmail;

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
		
		// 비밀번호 암호화
		String encodedPw = bCryptPasswordEncoder.encode(joinformDto.getPw());
		joinformDto.setPw(encodedPw);

		joinDao.insertUserDetail(joinformDto);  // user_detail 테이블 insert

		joinDao.insertUserLogin(joinformDto);  // user_login 테이블 정보입력

	}
	

}
