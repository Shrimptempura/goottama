package com.ama.don.member.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.UserDtailDto.Gender;

import jakarta.servlet.http.HttpServletRequest;

public class JoinService implements MemberServiceInter {

	private JoinDao joinDao;
	public JoinService(JoinDao joinDao) {
		this.joinDao=joinDao;
	}

	@Override
	public void execute(Model model) {
		Map<String, Object> map=model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		String loginId = request.getParameter("loginId");
		String pw = request.getParameter("pw");
		String roles = request.getParameter("roles");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String genderStr = request.getParameter("gender");
		Gender gender = Gender.valueOf(genderStr.toUpperCase());
		String birth = request.getParameter("birth");
		String tel = request.getParameter("tel");
		String zipcode = request.getParameter("zipcode");
		String addr = request.getParameter("addr");
		String email = request.getParameter("email");
		
		//비밀번호 암호화
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPw = encoder.encode(pw);
		
		joinDao.join(loginId,encodedPw,roles,name,nickname,gender,birth,tel,zipcode,addr,email);
		
		

	}

}
