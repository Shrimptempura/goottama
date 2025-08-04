package com.ama.don.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.PostDto;

@Mapper
public interface PostDao {

	// 페이징 메소드
	int countByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

	// targetType에 해당하는 게시글 목록을 페이징 처리하여 반환
	List<PostDto> findTargetType(@Param("targetType") String targetType, @Param("start") int start,
			@Param("count") int count);

	// targetType에 해당하는 게시글의 총 개수를 반환
	int countTargetType(@Param("targetType") String targetType);

	// 게시글 목록 조회
	List<PostDto> findByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

	// 조회수 증가
	void increaseViewCount(Long postId);

	// 좋이요 수 증가
	void increaseLikeCount(Long postId);
	
	
	void polyTest(PostDto dto);
		
	// 게시글 작성
	void create(PostDto dto);

	// 게시글 수정
	void update(PostDto dto);

	// 타겟 아이디 수정
	void update_target_id(PostDto dto);

	// 게시글 삭제
	void delete(Long postId);

}
