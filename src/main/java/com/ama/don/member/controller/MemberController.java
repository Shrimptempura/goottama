package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ama.don.member.dto.ResetPwDto;
import com.ama.don.member.service.MemberProfileService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberProfileService memberProfileService;

	@PostMapping("/resetPw")
	public String resetPw(@Valid @ModelAttribute ResetPwDto resetPwDto,HttpSession session,Model model) {
		
		boolean success = memberProfileService.resetPw(resetPwDto, session, model);
		
		if (!success) {
			return "member/resetPw_view";
		}		
		return "redirect:/login_view";
	}
	
	@GetMapping("/mypage/myProfile")
	public String memberProfile() {
		return "member/mypage/myProfile";
	}
	
	@GetMapping("/mypage/myOrderList")
	public String memberOrderList() {
		return "member/mypage/myOrderList";
	}
	
	@GetMapping("/mypage/myScrapbook")
	public String memberScrapbook() {
		return "member/mypage/myScrapbook";
	}
	
	@GetMapping("/mypage/myInquiry")
	public String myInquiry() {
		return "member/mypage/myInquiry";
	}
	
	@GetMapping("/mypage/myReview")
	public String myReview() {
		return "member/mypage/myReview";
	}
	
	@GetMapping("/mypage/myFeed")
	public String myFeed() {
		return "member/mypage/myFeed";
	}
	
	@GetMapping("/mypage/myComment")
	public String myComment() {
		return "member/mypage/myComment";
	}
	
	@GetMapping("/mypage/editProfile")
	public String editProfile() {
		return "member/mypage/editProfile";
	}
	
	@GetMapping("/mypage/editPassword")
	public String editPassword() {
		return "member/mypage/editPassword";
	}
	
	@GetMapping("/mypage/customerCenter")
	public String customerCenter() {
		return "member/mypage/customerCenter";
	}
}
