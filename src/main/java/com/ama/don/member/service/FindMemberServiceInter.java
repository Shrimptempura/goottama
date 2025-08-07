package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.FindLoginIdDto;
import com.ama.don.member.dto.FindPwDto;

import jakarta.servlet.http.HttpSession;

public interface FindMemberServiceInter {
	
	String findLoginId(FindLoginIdDto findLoginIdDto);
	boolean findPw(FindPwDto findPwDto,HttpSession session,Model model);

}
