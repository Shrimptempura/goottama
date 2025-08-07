package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.JoinformDto;

import jakarta.servlet.http.HttpSession;

public interface ValidationServiceInter {
	void emailCheck(JoinformDto joinformDto, Model model);  //이메일 중복확인
	void loginIdCheck(JoinformDto joinformDto, Model model);  //로그인아이디 중복확인
	void nicknameCheck(JoinformDto joinformDto, Model model);  //닉네임 중복확인
	void passwordCheck(JoinformDto joinformDto, Model model);  // pw 일치확인
	boolean emailvalidation(JoinformDto joinformDto,Model model,HttpSession session);
	boolean pwCodeValidation(String inputcode,HttpSession session,Model model); //비밀번호 변경 이증번호 확인 

}
