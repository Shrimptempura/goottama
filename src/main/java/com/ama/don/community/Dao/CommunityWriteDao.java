package com.ama.don.community.Dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.community.Dto.Review.ReviewWriteDto;

@Mapper
public interface CommunityWriteDao {

	void createReview(ReviewWriteDto dto); 

}
