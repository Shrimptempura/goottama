package com.ama.don.common.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.common.dto.PostDto;

@Mapper
public interface PostDao {
	// 게시글 목록 조회
	ArrayList<PostDto> findByTarget(String targetType, Long targetId);
	
	// 게시글 찾기
	PostDto findById(Long postId);

	// 게시글 작성
	void create(PostDto dto);

	// 게시글 삭제
	void delete(Long postId);

	// 게시글 수정
	void update(PostDto dto);

}
