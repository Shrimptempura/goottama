package com.ama.don.member.service;

import com.ama.don.member.dto.FindLoginIdDto;

public interface FindMemberServiceInter {
	
	String findLoginId(FindLoginIdDto findLoginIdDto);
	String findPw(String loginId,String email);

}
