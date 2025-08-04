package com.ama.don.community.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.PostDto;
import com.ama.don.community.Dto.Review.ReviewDetailDto;

@Mapper
public interface CommunityDetailDao {

	ReviewDetailDto findById(@Param("post_id") Long postId);

	// 게시글 수정
	void update(PostDto dto);

	// 게시글 삭제
	void delete(Long postId);

	// 조회수 증가
	void increaseViewCount(Long postId);

	// 좋아요 수 증가
	void increaseLikeCount(Long postId);

	// 타겟 아이디 수정
	void update_target_id(PostDto dto);
}
