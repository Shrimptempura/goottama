package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.JoinformDto;

@Mapper
public interface ValidationDao {
	
	int checkId(JoinformDto joinformDto);  //아이디 중복 검증
	int checkNickname(JoinformDto joinformDto);  //닉네임 중복 검증
	int checkEmail(JoinformDto joinformDto);  //이메일 중복 검증
	int nicknameEditCheck(String nickname);
	
	
}
