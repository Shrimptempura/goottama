package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.dto.UserDetailDto;
import com.ama.don.member.dto.UserDetailDto.Gender;

@Mapper
public interface JoinDao {
	
	void insertUserDetail(JoinformDto joinformDto);  //user_detail 테이블 insert
	void insertUserLogin(JoinformDto joinformDto);  //user_login 테이블 insert
	
	
}
