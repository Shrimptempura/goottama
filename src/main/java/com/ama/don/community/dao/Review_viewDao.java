package com.ama.don.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dto.Review_viewDto;

@Mapper
public interface Review_viewDao {
	List<Review_viewDto> selectAllReviews();

	Review_viewDto selectReviewById(Long post_id);

	List<Review_viewDto> selectByTargetType(TargetType targetType);

}
