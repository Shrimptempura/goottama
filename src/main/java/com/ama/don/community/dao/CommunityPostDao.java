package com.ama.don.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.PostDto;
import com.ama.don.community.dto.Review.ReviewPostDto;
//주석
@Mapper
public interface CommunityPostDao {

	// 페이징 메소드
	int countByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

	// targetType에 해당하는 게시글 목록을 페이징 처리하여 반환
	List<ReviewPostDto> findTargetType(@Param("targetType") String targetType, @Param("start") int start,
			@Param("count") int count);


	// 게시글 목록 조회
	List<PostDto> findByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

	// targetType에 해당하는 게시글의 총 개수를 반환
	int countTargetType(@Param("targetType") String targetType);

	// 게시글 찾기
	ReviewPostDto findById(Long review_id);
	
	// 조회수 좋아요수 최신화
	List<Map<String, Object>> findReviewCounts();


}