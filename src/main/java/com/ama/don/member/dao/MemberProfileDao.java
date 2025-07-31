package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberEditDto;

@Mapper
public interface MemberProfileDao {

	void updatePw(@Param("encodePw") String encodePw,@Param("findPwDto") FindPwDto findPwDto);

	void updateMember(@Param("memberDto")MemberDto memberDto, @Param("memberEditDto")MemberEditDto memberEditDto);

	MemberDto updated(String login_id);


}
