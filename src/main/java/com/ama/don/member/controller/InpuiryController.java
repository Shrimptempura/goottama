package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.InquiryService;
import com.ama.don.member.service.LoginMemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InpuiryController {
	
	private final InquiryService inquiryService;
	private final LoginMemberService loginMemberService;
	
	@PostMapping("/kakaoInquiry")
	@ResponseBody
	public String kakaoInquiry(MemberDto memberDto) {
		
		memberDto = loginMemberService.getCurrentLoginMemberDto();
		inquiryService.insertInquiry(memberDto);
		
		return "https://open.kakao.com/o/sOwqYpLh";
	}

}
