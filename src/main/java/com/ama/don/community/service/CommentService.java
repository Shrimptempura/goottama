package com.ama.don.community.service;

import com.ama.don.community.dao.CommunityCommentDao;
import com.ama.don.community.dto.Comment.CommentCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommunityCommentDao commentDao;

	@Transactional
	public void createComment(CommentCreateDto commentDto) {
		// 서비스에서 user_id 검증
		if (commentDto.getUser_id() == null) {
			throw new IllegalArgumentException("user_id가 없습니다.");
		}
		commentDao.insert(commentDto);
	}

	// 특정 게시글 댓글 조회
	public List<CommentCreateDto> getComments(Long targetId, String targetType) {
		return commentDao.findByTargetId(targetId, Enum.valueOf(com.ama.don.common.enums.TargetType.class, targetType));
	}

	// 수정
	@Transactional
	public void updateComment(CommentCreateDto dto) {
		commentDao.update(dto);
	}

	// 삭제
	@Transactional
	public void deleteComment(Long comment_id) {
		commentDao.delete(comment_id);
	}
}
