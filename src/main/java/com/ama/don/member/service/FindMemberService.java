package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.dto.FindLoginIdDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindMemberService implements FindMemberServiceInter{
	
	private final LoginDao loginDao;

	@Override
	public String findLoginId(FindLoginIdDto findLoginIdDto) {
		String loginId = loginDao.findByLoginId(findLoginIdDto);
		return loginId;
	}

	@Override
	public String findPw(String loginId, String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
