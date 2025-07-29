package com.ama.don.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dto.Detail_viewDto;

@Mapper
public interface Detail_viewDao {

	List<Detail_viewDto> selectAllReviews();

	Detail_viewDto selectReviewById(Long post_id);

	List<Detail_viewDto> selectByTargetType(TargetType targetType);

	// 수정 버튼 Dao
	void updateReview(Detail_viewDto dto);
}
