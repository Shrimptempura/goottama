package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.JoinformDto;
import com.ama.don.member.dto.UserDetailDto;
import com.ama.don.member.dto.UserDetailDto.Gender;

@Mapper
public interface ValidationDao {
	
	int checkId(JoinformDto joinformDto);
	int checkNickname(JoinformDto joinformDto);
	int checkEmail(JoinformDto joinformDto);
	
	
}
