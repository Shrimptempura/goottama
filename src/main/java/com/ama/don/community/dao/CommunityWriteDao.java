package com.ama.don.community.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.community.dto.Review.ReviewWriteDto;
//주석
@Mapper
public interface CommunityWriteDao {

	void createReview(ReviewWriteDto dto); 

}
