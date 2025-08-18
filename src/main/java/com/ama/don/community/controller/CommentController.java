package com.ama.don.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ama.don.community.dto.Comment.CommentCreateDto;
import com.ama.don.community.service.CommentService;
import com.ama.don.member.dao.LoginDao;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/comment")
public class CommentController {

	private final CommentService commentService;
	private final LoginDao loginDao;

	// 댓글/대댓글 작성
	@PostMapping("/create")
	public String createComment(@ModelAttribute CommentCreateDto dto) {
		Long userId = getCurrentUserId(); // 로그인 사용자 user_id 주입
		dto.setUser_id(userId);

		commentService.createComment(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getTargetId();
	}

	// 댓글/대댓글 수정
	@PostMapping("/update")
	public String updateComment(@ModelAttribute CommentCreateDto dto) {
		Long userId = getCurrentUserId(); // 로그인 사용자 user_id 주입
		dto.setUser_id(userId);

		commentService.updateComment(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getTargetId();
	}

	// 댓글/대댓글 삭제
	@PostMapping("/delete")
	public String deleteComment(@RequestParam("comment_id") Long commentId, @RequestParam("post_id") Long postId) {
		commentService.deleteComment(commentId);
		return "redirect:/community/post_detail_view?post_id=" + postId;
	}

	// 현재 로그인 사용자의 user_id 조회
	private Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
			throw new SecurityException("로그인이 필요합니다.");
		}
		String loginId = auth.getName(); // 어떤 Principal이든 username(login_id)을 반환
		Long userId = loginDao.findUserIdByLoginId(loginId);
		if (userId == null) {
			throw new IllegalStateException("사용자 정보를 찾을 수 없습니다.");
		}
		return userId;
	}
}
