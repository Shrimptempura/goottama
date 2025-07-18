package com.ama.don.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.dto.UserDtailDto;
import com.ama.don.member.dto.UserDtailDto.Gender;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class JoinService implements MemberServiceInter {

	@Autowired
	private JoinDao joinDao;

	@Override
	public void execute(JoinformDto joinformDto, Model model) {

		//비밀번호 암호화
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String pw = joinformDto.getPw();
		String encodedPw = encoder.encode(pw);
		joinformDto.setPw(encodedPw);
		
		//user_detail 테이블 insert
		joinDao.insertUserDtail(joinformDto);
		
		//user_login 테이블 정보입력
		joinDao.insertUserLogin(joinformDto);
		
	}

}
