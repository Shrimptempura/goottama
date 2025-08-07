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

	// 업체 다형성 게시글 생성
	int insertPolyPostForCompany(PostDto dto);

	// 다형성 조회
	void polyFindById(Long postId);

	PostDto findById(Long postId);

}
