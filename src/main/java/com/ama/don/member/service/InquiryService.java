package com.ama.don.member.service;

import org.springframework.stereotype.Service;

import com.ama.don.member.dao.InquiryDao;
import com.ama.don.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryService implements InquiryInter{
	
	private final InquiryDao inquiryDao;

	@Override
	public void insertInquiryKaKao(MemberDto memberDto) {
		
		long user_id = memberDto.getUser_id();
		int inquity_id = 1;
		
		inquiryDao.insertInquiryKakao(user_id,inquity_id);
		
		
	}

	@Override
	public void insertInquiryEmail(MemberDto memberDto) {
		long user_id = memberDto.getUser_id();
		int inquity_id = 2;
		
		inquiryDao.insertInquiryEmail(user_id,inquity_id);
		
	}

}
