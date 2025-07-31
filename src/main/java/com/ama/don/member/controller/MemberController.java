package com.ama.don.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberEditDto;
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
	
	@GetMapping("/mypage/editProfile_view")
	public String editProfile_view(HttpSession session, MemberDto memberDto, Model model) {
		
		memberDto = (MemberDto) session.getAttribute("loginMember");
		model.addAttribute("loginMember", memberDto);
		
		return "member/mypage/editProfile_view";
	}
	
	@PostMapping("/editProfile")
	public String editProfile(@Valid @ModelAttribute MemberEditDto memberEditDto,BindingResult bindingResult, Model model, HttpSession session) {
		
		memberEditDto.combineAddress(); // 폼에 입력된 값 하나로 dto에 주입
		MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");
		memberProfileService.updateProfile(memberDto, memberEditDto);
		
		return "";
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
