package com.ama.don.member.service;

import com.ama.don.member.dto.LoginformDto;
import com.ama.don.member.dto.MemberDto;

import jakarta.servlet.http.HttpSession;

public interface LoginServiceInter {
	
	MemberDto login(LoginformDto loginformDto,HttpSession session); //로그인 처리(세션생성)
	MemberDto findByLoginId(String loginId);  //로그인 아이디로 사용자 정보 조회
	void logout(HttpSession session); //로그아웃처리(세션정리)

}
