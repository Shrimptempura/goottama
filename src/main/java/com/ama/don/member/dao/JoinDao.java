package com.ama.don.member.dao;

import com.ama.don.member.dto.JoinformDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JoinDao {
	
	void insertUserDetail(JoinformDto joinformDto);  //user_detail 테이블 insert
	void insertUserLogin(JoinformDto joinformDto);  //user_login 테이블 insert
	
	
}
