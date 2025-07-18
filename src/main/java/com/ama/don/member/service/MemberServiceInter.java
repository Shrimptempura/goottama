package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.JoinformDto;

public interface MemberServiceInter {
	
	public void execute(JoinformDto joinformDto,Model model);

}
