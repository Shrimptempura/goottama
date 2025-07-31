package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.member.dto.FindPwDto;

@Mapper
public interface MemberProfileDao {

	void updatePw(@Param("encodePw") String encodePw,@Param("findPwDto") FindPwDto findPwDto);
	
	

}
