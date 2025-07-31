package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.member.dto.FindLoginIdDto;
import com.ama.don.member.dto.FindPwDto;
import com.ama.don.member.dto.LoginformDto;
import com.ama.don.member.dto.MemberDto;

@Mapper
public interface LoginDao {

	MemberDto findByMember(LoginformDto loginformDto); //회원정보 조회

	String findByLoginId(FindLoginIdDto findLoginIdDto); //아이디 검색

	int findMemberCount(FindPwDto findPwDto);

	String findByOnlyLoginId();



}
