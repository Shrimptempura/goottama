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

	// 인테리어, 다형성 조회
	int polyFindById(Long postId);

	// 인테리어, 게시글 조회
	PostDto findById(Long postId);

	// 인테리어 postId 찾기 보조용
	Long findPostIdByCompanyPostId(Long companyPostId);

}
