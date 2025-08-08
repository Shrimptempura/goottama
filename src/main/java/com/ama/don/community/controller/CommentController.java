package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.community.dao.CommunityCommentDao;
import com.ama.don.community.dto.Comment.CommentCommentCreateDto;
import com.ama.don.community.dto.Comment.CommentCreateDto;

@Controller
@RequestMapping("/community")
public class CommentController {

	@Autowired
	private CommunityCommentDao commentDao;

	// 댓글 등록
	@PostMapping("/comment")
	public String createComment(CommentCreateDto dto) {
		dto.setUser_id(1L);
		commentDao.insert(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getTargetId();
	}

	// 댓글 삭제
	@PostMapping("/comment/delete")
	public String deleteComment(@RequestParam Long commentId, @RequestParam Long postId) {
		commentDao.delete(commentId);
		return "redirect:/community/post_detail_view?post_id=" + postId;
	}

	// 댓글 수정
	@PostMapping("/comment/update")
	public String updateComment(CommentCreateDto dto) {
		commentDao.update(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getTargetId();
	}
}
