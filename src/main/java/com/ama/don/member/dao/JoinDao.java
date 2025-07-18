package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.dto.UserDtailDto;
import com.ama.don.member.dto.UserDtailDto.Gender;

@Mapper
public interface JoinDao {
	
	int checkId(String loginId);
	void insertUserDtail(JoinformDto joinformDto);
	void insertUserLogin(JoinformDto joinformDto);
	
	
}
