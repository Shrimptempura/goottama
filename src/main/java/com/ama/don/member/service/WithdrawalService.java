package com.ama.don.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ama.don.member.dao.WithdrawalDao;
import com.ama.don.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WithdrawalService implements WithdrawalServiceInter{
	
	private final WithdrawalDao withdrawalDao;

	@Override
	@Transactional
	public void deletedMember(String agree, int reason, MemberDto memberDto) {
		
		long user_id = memberDto.getUser_id();
		
		if (agree.equals("yes")) {
			withdrawalDao.changeMemberStatus(user_id); //회원상태 변경
			withdrawalDao.insertWithdrawalMember(reason,user_id); //탈퇴회원테이블에 insert
		}
		
	}

}
