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
		
		   boolean hasError = false;

		    // 연락처 처리
		    String newTel = memberEditDto.getChangeTel();
		    if (newTel == null || newTel.isBlank()) {
		        memberEditDto.setChangeTel(memberDto.getUser_tel()); // 기존 값 유지
		    } else if (!newTel.matches("^(01[016789])(-?\\d{3,4})(-?\\d{4})$")) {
		        model.addAttribute("validationError_tel", "올바른 연락처 형식이 아닙니다.");
		        hasError = true;
		    }

		    // 닉네임 처리
		    String newNickname = memberEditDto.getChangeNickname();
		    if (newNickname == null || newNickname.isBlank()) {
		        memberEditDto.setChangeNickname(memberDto.getUser_nickname()); // 기존 값 유지
		    } else if (!newNickname.equals(memberDto.getUser_nickname())) { // 닉네임이 바뀌었을 때만 체크
		        boolean checkNickname = validationService.nicknameEditCheck(newNickname);
		        if (!checkNickname) {
		            model.addAttribute("validationError_nickname", "이미 사용중인 닉네임 입니다.");
		            hasError = true;
		        }
		    }

		    // 에러가 하나라도 있으면 업데이트 중단
		    if (hasError) {
		        model.addAttribute("loginMember", memberDto);
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
