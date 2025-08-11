package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InquiryDao {

	void insertInquiryKakao(@Param("user_id")long user_id,@Param("inquity_id") int inquity_id);

	void insertInquiryEmail(@Param("user_id")long user_id,@Param("inquity_id") int inquity_id);

}
