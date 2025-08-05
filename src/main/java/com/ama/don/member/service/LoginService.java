package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.dto.LoginformDto;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberDto.Status;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginServiceInter{
	
	private final LoginDao loginDao;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public MemberDto login(LoginformDto loginformDto,HttpSession session) {
		
		//user_detail,user_login 테이블에 있는 모든정보 memberdto로 반환
		MemberDto memberDto = loginDao.findByMember(loginformDto);
		
		//회원정보가 없거나 회원상태가 탈퇴회원인 경우
		if (memberDto == null || memberDto.getUser_status() == Status.deleted) {
			return null;
		}
		
		boolean pwMaches = passwordEncoder.matches(loginformDto.getPw(), memberDto.getUser_password());
		if (!pwMaches) {
			return null;
		}
		
		
		session.setAttribute("loginMember", memberDto);		
		return memberDto;
	}

	@Override
	public void logout(HttpSession session) {
		session.invalidate();
		
	}

}
