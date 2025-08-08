package com.ama.don.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InquiryDao {

	void insertInquiry(@Param("user_id")long user_id,@Param("inquity_id") int inquity_id);

}
