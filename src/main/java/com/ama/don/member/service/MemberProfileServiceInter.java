package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberEditDto;
import com.ama.don.member.dto.ResetPwDto;

import jakarta.servlet.http.HttpSession;

public interface MemberProfileServiceInter {
	
	boolean resetPw(ResetPwDto resetPwDto,HttpSession session,Model model);
	
	boolean updateProfile(MemberDto memberDto, MemberEditDto memberEditDto, Model model);
	
	MemberDto getupdatedMember(String login_id);

}
