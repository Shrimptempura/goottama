package com.ama.don.member.service;

import com.ama.don.member.dto.MemberDto;

public interface WithdrawalServiceInter {
	
	void deletedMember(String agree, int reasom,MemberDto memberDto);

}
