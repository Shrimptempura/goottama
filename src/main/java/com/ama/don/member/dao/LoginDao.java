package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.LoginformDto;

@Mapper
public interface LoginDao {

	String findByLoginId(LoginformDto loginformDto);
	String findByPw(LoginformDto loginformDto);

}
