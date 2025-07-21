package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.JoinformDto;

public interface ValidationServiceInter {
	void emailCheck(JoinformDto joinformDto, Model model);  //이메일 중복확인
	void loginIdCheck(JoinformDto joinformDto, Model model);  //로그인아이디 중복확인
	void nicknameCheck(JoinformDto joinformDto, Model model);  //닉네임 중복확인
	void passwordCheck(JoinformDto joinformDto, Model model);  // pw 일치확인

}
