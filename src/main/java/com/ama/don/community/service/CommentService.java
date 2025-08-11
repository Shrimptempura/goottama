package com.ama.don.community.service;

import com.ama.don.community.dao.CommunityCommentDao;
import com.ama.don.community.dto.Comment.CommentCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommunityCommentDao commentDao;

	public void createComment(CommentCreateDto commentDto) {
		// 임시로 user_id를 1로 세팅
		if (commentDto.getUser_id() == null) {
			commentDto.setUser_id(1L);
		}

		commentDao.insert(commentDto);
	}

	// 특정 게시글 댓글 조회
	public List<CommentCreateDto> getComments(Long targetId, String targetType) {
		return commentDao.findByTargetId(targetId, Enum.valueOf(com.ama.don.common.enums.TargetType.class, targetType));
	}

	// 수정
	public void updateComment(CommentCreateDto dto) {
		commentDao.update(dto);
	}

	// 삭제
	public void deleteComment(Long comment_id) {
		commentDao.delete(comment_id);
	}
}
