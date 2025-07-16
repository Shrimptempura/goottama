package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.UserDtailDto;
import com.ama.don.member.dto.UserDtailDto.Gender;

@Mapper
public interface JoinDao {
	
	int checkId(String loginId);
	void insertUserDtail(UserDtailDto dto);
	void insertUserLogin(long user_id, String loginId, String encodedPw, String roles);
	
	

	
}
