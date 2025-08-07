package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.common.dao.PostDao;

@Controller
@RequestMapping("/community")
public class DeleteController {

	@Autowired
	private PostDao postDao;

	// 삭제 처리
	@PostMapping("/delete")
	public String deleteReview(@RequestParam("post_id") Long postId) {
		postDao.delete(postId);
		return "redirect:/community/review_view";
	}
}
