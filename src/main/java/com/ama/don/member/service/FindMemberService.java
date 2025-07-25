package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.dto.FindLoginIdDto;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.utill.EmailSHA;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindMemberService implements FindMemberServiceInter{
	
	private final LoginDao loginDao;
	private final SendEmailService sendEmailService;
	

	@Override
	public String findLoginId(FindLoginIdDto findLoginIdDto) {
		String loginId = loginDao.findByLoginId(findLoginIdDto);
		return loginId;
	}

	@Override
	public boolean findPw(FindPwDto findPwDto,HttpSession session,Model model) {
		
		
		if (loginDao.findMemberCount(findPwDto) == 0) {
			model.addAttribute("email_error","존재하지 않는 회원입니다.");
			return false;
		}
		
		//6자리 인증 코드 생성
		int randomCode = (int) (Math.random() * 90000) + 10000;
		String code = String.valueOf(randomCode);
		
		//세션 저장
		session.setAttribute("authCode", code);
		session.setAttribute("tempPwMember", findPwDto);
		
		//메일전송
		sendEmailService.sendPwcodeEmailAction(findPwDto, code);
		
		return true;
	}

}
