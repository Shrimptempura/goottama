package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	// 지우면 저주 받음(아이디 찾기)
	// 인테리어 파트 사용 중
	MemberDto interiorFindByLoginId(String loginId);
	
	// 커뮤니티 사용
	Long findUserIdByLoginId(@Param("login_id") String loginId);
}
