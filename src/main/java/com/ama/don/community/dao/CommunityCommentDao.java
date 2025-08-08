package com.ama.don.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dto.Comment.CommentCreateDto;

@Mapper
public interface CommunityCommentDao {

	// 댓글 등록
	void insert(CommentCreateDto commentDto);

	// 특정 게시글의 댓글 리스트 조회
	List<CommentCreateDto> findByTargetId(@Param("targetId") Long targetId, @Param("targetType") TargetType targetType);

	// 댓글 단건 조회
	CommentCreateDto findById(@Param("commentId") Long commentId);

	// 댓글 삭제
	void delete(@Param("commentId") Long commentId);

	// 댓글 수정
	void update(CommentCreateDto commentDto);
}
