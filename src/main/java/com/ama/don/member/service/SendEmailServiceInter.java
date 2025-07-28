package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.JoinformDto;

public interface SendEmailServiceInter {
	
	void emailSendAction(JoinformDto joinformDto,Model model);
	void  sendPwcodeEmailAction(FindPwDto findPwDto, String code);
}
