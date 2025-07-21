package com.ama.don.member.service;

import org.springframework.ui.Model;

import com.ama.don.member.dto.JoinformDto;

public interface JoinServiceInter {
	
	public void join(JoinformDto joinformDto,Model model); //

}
