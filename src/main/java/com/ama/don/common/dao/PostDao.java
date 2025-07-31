package com.ama.don.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.dto.PostDto;

@Mapper
public interface PostDao {
	
	// 페이징 메소드
    int countByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

    List<PostDto> findPagedByTarget(
        @Param("targetType") String targetType,
        @Param("targetId") Long targetId,
        @Param("rowStart") int rowStart,
        @Param("rowCount") int rowCount
    );

	// 게시글 목록 조회
	List<PostDto> findByTarget(@Param("targetType") String targetType, @Param("targetId") Long targetId);

	// 게시글 찾기
	PostDto findById(Long postId);

	// 게시글 작성
	void create(PostDto dto);

	// 게시글 수정
	void update(PostDto dto);

	// 게시글 삭제
	void delete(Long postId);

}
