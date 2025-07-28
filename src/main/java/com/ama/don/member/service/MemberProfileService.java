package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.member.dao.MemberProfileDao;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.ResetPwDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberProfileService implements MemberProfileServiceInter{
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final MemberProfileDao memberProfileDao;

	@Override
	@Transactional
	public boolean resetPw(ResetPwDto resetPwDto, HttpSession session, Model model) {
		
		FindPwDto findPwDto = (FindPwDto) session.getAttribute("tempPwMember");
		
		if (!resetPwDto.getResetPw().equals(resetPwDto.getResetPw2())) {
			model.addAttribute("pw_error","비밀번호가 잃지하지 안습니다.");
			return false;
		}
		
		String encodePw = bCryptPasswordEncoder.encode(resetPwDto.getResetPw());
		
		memberProfileDao.updatePw(encodePw,findPwDto);
		
		session.removeAttribute("authCode");
		session.removeAttribute("tempPwMember");
		
		return true;
		
	}

}
