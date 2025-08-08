package com.ama.don.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.community.dto.Review.ReviewPostDto;

@Mapper
public interface CommunityReviewDao {

	// 리뷰 전체 목록 조회
	List<ReviewPostDto> findAll();

	// 특정 리뷰 상세 조회
	ReviewPostDto findById(@Param("postId") Long postId);
}
