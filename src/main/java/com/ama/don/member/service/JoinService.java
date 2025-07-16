package com.ama.don.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.member.dao.JoinDao;
import com.ama.don.member.dto.UserDtailDto;
import com.ama.don.member.dto.UserDtailDto.Gender;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class JoinService implements MemberServiceInter {

	@Autowired
	private JoinDao joinDao;

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
		
				
		UserDtailDto dto = new UserDtailDto();
		dto.setUser_name(name);
		dto.setUser_nickname(nickname);
		dto.setUser_gender(gender);
		dto.setUser_birth(birth);
		dto.setUser_tel(tel);
		dto.setUser_zipcode(zipcode);
		dto.setUser_addr(addr);
		dto.setUser_email(email);
		
		//user_detail 테이블 정보입력
		joinDao.insertUserDtail(dto);
		
		long user_id = dto.getUser_id();
		
		//user_login 테이블 정보입력
		joinDao.insertUserLogin(user_id,loginId,encodedPw,roles);
		

	}

}
