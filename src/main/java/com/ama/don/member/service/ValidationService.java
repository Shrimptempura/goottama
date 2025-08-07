package com.ama.don.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.member.dao.ValidationDao;
import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.utill.EmailSHA;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class ValidationService implements ValidationServiceInter {

	@Autowired
	private ValidationDao validationDao;

	@Override //이메일 중복확인
	public void emailCheck(JoinformDto joinformDto, Model model) {
		if (validationDao.checkEmail(joinformDto) > 0) {
			model.addAttribute("email_error", "이미 사용중인 이메일입니다.");
		}

	}

	@Override
	public void loginIdCheck(JoinformDto joinformDto, Model model) {
		// 아이디 중복확인
		if (validationDao.checkId(joinformDto) > 0) {
			model.addAttribute("id_error", "이미 사용중인 아이디입니다.");
		}

	}

	@Override
	public void nicknameCheck(JoinformDto joinformDto, Model model) {
		// 닉네임 중복확인
		if (validationDao.checkNickname(joinformDto) > 0) {
			model.addAttribute("nickname_error", "이미 사용중인 닉네임입니다.");
		}

	}

	@Override
	public void passwordCheck(JoinformDto joinformDto, Model model) {
		// 비밀번화 일치 확인
		if (!joinformDto.getPw().equals(joinformDto.getPw2())) {
			model.addAttribute("pw_error", "비밀번호가 일치하지 않습니다.");
		}

	}

	@Override
	public boolean emailvalidation(JoinformDto joinformDto,Model model,HttpSession session) {
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=(HttpServletRequest) map.get("request");
		
		String code=request.getParameter("code");
		
		if (joinformDto == null) {
			model.addAttribute("emailValFail", "세션이 만료되었거나 유효하지 않은 접근입니다.");
			return false;
		}
		
		String memberEmail = joinformDto.getEmail();
		boolean isRight=(new EmailSHA().getSHA256(memberEmail).equals(code))?true:false;
		
		if(isRight==true){
			model.addAttribute("emailValSuccess","이메일 인증 성공");			
			return isRight;
		}
		model.addAttribute("emailValFail","이메일 인증 실패");
		return isRight;
	}

	@Override
	public boolean pwCodeValidation(String inputcode, HttpSession session, Model model) {

		String sessionCode = (String) session.getAttribute("authCode");
		
		if (sessionCode != null && sessionCode.equals(inputcode)) {
			return true;
		}
		model.addAttribute("pwCode_error","코드가 일치하지 않습니다.");
		return false;
	}

	public boolean nicknameEditCheck(String nickname) {
		if (validationDao.nicknameEditCheck(nickname) > 0) {
			return false;
		}
		return true;
		
	}

}
