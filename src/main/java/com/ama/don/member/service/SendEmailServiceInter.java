package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.dto.MemberDto;

public interface SendEmailServiceInter {
	
	void emailSendAction(JoinformDto joinformDto,Model model);
	void sendPwcodeEmailAction(FindPwDto findPwDto, String code);
	void sendInquiryEmail(MemberDto memberDto,String subject,String message);
}
