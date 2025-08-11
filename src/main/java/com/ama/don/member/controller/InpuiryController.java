package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.InquiryService;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.member.service.SendEmailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InpuiryController {
	
	private final InquiryService inquiryService;
	private final LoginMemberService loginMemberService;
	private final SendEmailService sendEmailService;
	
	@PostMapping("/kakaoInquiry")
	@ResponseBody
	public String kakaoInquiry(MemberDto memberDto) {
		
		memberDto = loginMemberService.getCurrentLoginMemberDto();
		inquiryService.insertInquiryKaKao(memberDto);
		
		return "https://open.kakao.com/o/sOwqYpLh";
	}
	
	@GetMapping("/inquiryEmail")
	public String inquiryEmail() {
		return "member/inquiryEmail";
	}
	
	 @PostMapping("/sendInquiry")
	 public String sendInquiry(@RequestParam("email") String email, 
			 					@RequestParam("subject") String subject, 
			 					@RequestParam("message") String message,
			 					MemberDto memberDto) {
		 memberDto = loginMemberService.getCurrentLoginMemberDto();
		 inquiryService.insertInquiryEmail(memberDto);
		 sendEmailService.sendInquiryEmail(email, subject, message);
		 
		 return "redirect:/mypage/customerCenter";
	 }

}
