package com.ama.don.member.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ama.don.member.dto.FindLoginIdDto;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.service.FindMemberService;
import com.ama.don.member.service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.userManage.CustomUserDetailsService;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import com.ama.don.member.dto.FindLoginIdDto;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.FindMemberService;
import com.ama.don.member.service.ValidationService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	//private final LoginService loginService;
	private final FindMemberService findMemberService;
	private final ValidationService validationService;
	
	@GetMapping("/")
	   public String index() {
	    
	      return "list";
	   }
	
	@GetMapping("/login_view")
	public String login_view() {
		return "member/login_view";
	}
	
	@GetMapping("/findLoginId_view")
	public String findId_view(Model model) {
		model.addAttribute("findLoginIdDto",new FindLoginIdDto());
		return "member/findLoginId_view";
	}
	
	@GetMapping("/findPw_view")
	public String findPw_view(Model model) {		
		return "member/findPw_view";
	}
	
	@PostMapping("/find_loginId")
	public String find_loginId(@Valid @ModelAttribute FindLoginIdDto findLoginIdDto,BindingResult bindingResult,Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("findLoginIdDto", findLoginIdDto);
			return "member/findLoginId_view";
		}
		
		String loginId = findMemberService.findLoginId(findLoginIdDto);
		if (loginId == null) {
			model.addAttribute("id_error","해당 정보로 가입된 아이디가 없습니다.");
			return "member/findLoginId_view";
		}
		
		model.addAttribute("loginId",loginId);
		
		return "member/findLoginId_view";
	}
	
	@PostMapping("/findPw")
	public String findPw(@Valid @ModelAttribute FindPwDto findPwDto,BindingResult bindingResult,HttpSession session,Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("findPwDto", findPwDto);
			return "member/findPw_view";
		}
		
		boolean success = findMemberService.findPw(findPwDto,session,model);
		
		if (!success) {
			return"member/findPw_view";
		}
		return "member/checkPwCode_view"; //성공 시 인증코드 입력 화면으로
	}
	
	@PostMapping("/checkPwCode")
	public String checkPwCode(@RequestParam("inputCode") String inputCode,HttpSession session,Model model) {
		
		boolean isRight = validationService.pwCodeValidation(inputCode, session, model);
		
		if (isRight == true) {
			return "member/resetPw_view";
		}
		
		return "member/checkPwCode_view";
	}
}
