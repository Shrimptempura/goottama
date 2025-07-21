package com.ama.don.member.service;

import org.springframework.stereotype.Service;

import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.dto.LoginformDto;
import com.ama.don.member.dto.MemberDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginServiceInter{
	
	private final LoginDao loginDao;

	@Override
	public MemberDto login(LoginformDto loginformDto,HttpSession session) {
		String loginId = loginDao.findByLoginId(loginformDto);
		String pw = loginDao.findByPw(loginformDto);
		return null;
	}

	@Override
	public MemberDto findByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(HttpSession session) {
		// TODO Auto-generated method stub
		
	}

}
