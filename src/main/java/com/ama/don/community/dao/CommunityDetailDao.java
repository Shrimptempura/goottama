package com.ama.don.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.PostDto;
import com.ama.don.community.dto.Review.ReviewDetailDto;
//주석
@Mapper
public interface CommunityDetailDao {

	Long findReviewIdByPostId(Long postId);

	ReviewDetailDto findById(@Param("reviewId") Long reviewId);

	// 게시글 수정
	void update(PostDto dto);

	// 게시글 삭제
	void delete(Long postId);

	// 조회수 증가
	void increaseViewCount(@Param("review_id") Long reviewId);

	// 좋아요 수 증가
	void increaseLikeCount(@Param("reviewId") Long reviewId);


	// 타겟 아이디 수정
	void update_target_id(PostDto dto);

}
