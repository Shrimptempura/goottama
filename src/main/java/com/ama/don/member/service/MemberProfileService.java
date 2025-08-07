package com.ama.don.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.member.dao.MemberProfileDao;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberEditDto;
import com.ama.don.member.dto.ResetPwDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberProfileService implements MemberProfileServiceInter{
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final MemberProfileDao memberProfileDao;
	private final ValidationService validationService;

	@Override
	@Transactional
	public boolean resetPw(ResetPwDto resetPwDto, HttpSession session, Model model) {
		
		FindPwDto findPwDto = (FindPwDto) session.getAttribute("tempPwMember");
		
		if (!resetPwDto.getResetPw().equals(resetPwDto.getResetPw2())) {
			model.addAttribute("pw_error","비밀번호가 일치하지 안습니다.");
			return false;
		}
		
		String encodePw = bCryptPasswordEncoder.encode(resetPwDto.getResetPw());
		
		memberProfileDao.updatePw(encodePw,findPwDto);
		
		session.removeAttribute("authCode");
		session.removeAttribute("tempPwMember");
		
		return true;
		
	}

	@Override
	public boolean updateProfile(MemberDto memberDto, MemberEditDto memberEditDto,Model model) {
		
		if (memberEditDto.getChangeTel() == null || memberEditDto.getChangeTel().isEmpty()) {
	        memberEditDto.setChangeTel(memberDto.getUser_tel());
	    }
		
		if (!memberEditDto.getChangeTel().matches("^(01[016789])(-?\\d{3,4})(-?\\d{4})$")) {
	        model.addAttribute("validationError", "올바른 연락처 형식이 아닙니다.");
	        model.addAttribute("loginMember",memberDto);
	        return false;
	    }
		
		if (memberEditDto.getChangeNickname() == null || memberEditDto.getChangeNickname().isEmpty()) {
	        memberEditDto.setChangeNickname(memberDto.getUser_nickname());
	    }
		
		//닉네임 중복확인
		String nickname = memberEditDto.getChangeNickname();
		boolean chechNickname = validationService.nicknameEditCheck(nickname);
	 	if (chechNickname == false) {
	 		model.addAttribute("validationError", "이미 사용중인 닉네임 입니다.");
	 		model.addAttribute("loginMember",memberDto);
			return false;
		}		
		//db update
		memberProfileDao.updateMember(memberDto,memberEditDto);		
		
		return true;
	}
	
	@Override
	public UserTotalDataDTO getupdatedMember(String login_id) {
		UserTotalDataDTO updated = memberProfileDao.updated(login_id);
		return updated;
	}

}
