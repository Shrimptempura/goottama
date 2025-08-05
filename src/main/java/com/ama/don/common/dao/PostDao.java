package com.ama.don.common.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ama.don.common.dto.PostDto;

@Mapper
public interface PostDao {

	void polyTest(PostDto dto);
		
	// 게시글 작성
	void create(PostDto dto);

	// 게시글 수정
	void update(PostDto dto);

	// 게시글 삭제
	void delete(Long postId);

}
