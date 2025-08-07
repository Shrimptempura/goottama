package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WithdrawalDao {

	void changeMemberStatus(long user_id); //회원상태 변경

	void insertWithdrawalMember(@Param("reason") int reason,@Param("user_id") long user_id);//탈퇴회원테이블에 insert

}
