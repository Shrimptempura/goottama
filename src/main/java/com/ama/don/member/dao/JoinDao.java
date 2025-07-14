package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.UserDtailDto.Gender;

@Mapper
public interface JoinDao {

	void join(String loginId, String encodedPw, String roles, String name, String nickname, Gender gender, String birth,
			String tel, String zipcode, String addr, String email);
	

}
