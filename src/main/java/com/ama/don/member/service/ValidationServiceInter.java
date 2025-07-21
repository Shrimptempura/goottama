package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.JoinformDto;

public interface ValidationServiceInter {
	void emailCheck(JoinformDto joinformDto, Model model);
	void loginIdCheck(JoinformDto joinformDto, Model model);
	void nicknameCheck(JoinformDto joinformDto, Model model);
	void passwordCheck(JoinformDto joinformDto, Model model);

}
