package com.ama.don.member.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberEditDto;
import com.ama.don.member.dto.ResetPwDto;
import com.ama.don.member.service.MemberProfileService;
import com.ama.don.member.service.ProfileImgUploadService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberProfileService memberProfileService;
	private final ProfileImgUploadService profileImgUploadService;

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
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("memberEditDto", memberEditDto);
			// 검증 결과 콘솔에 에러출력
			for (FieldError error : bindingResult.getFieldErrors()) {
				System.out.println("Error in field: " + error.getField());
				System.out.println("Message: " + error.getDefaultMessage());
			}
			return "member/mypage/editProfile_view";
		}
		
		memberEditDto.combineAddress(); // 폼에 입력된 값 하나로 dto에 주입
		MemberDto memberDto = (MemberDto) session.getAttribute("loginMember");
		boolean success = memberProfileService.updateProfile(memberDto, memberEditDto, model); //db업데이트
		
		if (!success) {
			model.addAttribute("nickname_error", "이미 사용중인 닉네임입니다.");
			return "member/mypage/editProfile_view";
		}
		MemberDto updated = memberProfileService.getupdatedMember(memberDto.getLogin_id()); //최신정보 조회
		session.setAttribute("loginMember", updated); //세션 갱신	
		
		return "redirect:/mypage/editProfile_view";
	}
	
	@PostMapping("/profileImgUpload")
	public String profileImgUpload(@RequestParam("profileImg") MultipartFile file, HttpSession session, MemberDto memberDto, Model model) throws IllegalStateException, IOException {
		
		memberDto = (MemberDto) session.getAttribute("loginMember");
		
		profileImgUploadService.changeProfileImg(memberDto, file);
		
		session.setAttribute("loginMember", memberDto);
		model.addAttribute("loginMember", memberDto);
		
		return "member/mypage/editProfile_view";
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
