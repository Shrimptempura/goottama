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
		String addr1 = request.getParameter("addr");
		String detailAddr = request.getParameter("detailAddr");
		String emailId = request.getParameter("emailId");
		String emailDomain = request.getParameter("emailDomain");
		String email = emailId+"@"+emailDomain;
		String addr = addr1+detailAddr;
		
		//비밀번호 암호화
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPw = encoder.encode(pw);
		
		joinDao.insertUserDtail(name,nickname,gender,birth,tel,zipcode,addr,email);
		long user_id = joinDao.selectMaxUserId();
		joinDao.insertUserLogin(user_id,loginId,encodedPw,roles);
		

	}

}
