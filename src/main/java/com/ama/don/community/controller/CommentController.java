package com.ama.don.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.community.dto.Comment.CommentCreateDto;
import com.ama.don.community.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/comment")
public class CommentController {

	private final CommentService commentService;

	// 댓글/대댓글 작성
	@PostMapping("/create")
	public String createComment(@ModelAttribute CommentCreateDto dto) {
		commentService.createComment(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getTargetId();
	}

	// 댓글/대댓글 수정
	@PostMapping("/update")
	public String updateComment(@ModelAttribute CommentCreateDto dto) {
		commentService.updateComment(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getTargetId();
	}

	// 댓글/대댓글 삭제
	@PostMapping("/delete")
	public String deleteComment(@RequestParam("comment_id") Long commentId, @RequestParam("post_id") Long postId) {
		commentService.deleteComment(commentId);
		return "redirect:/community/post_detail_view?post_id=" + postId;
	}
}
