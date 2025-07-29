package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.community.dao.Detail_viewDao;
import com.ama.don.community.dto.Detail_viewDto;

@Controller
@RequestMapping("/community")
public class DeleteController {

	@Autowired
	private Detail_viewDao detailViewDao;

	// 삭제 처리
	@PostMapping("/delete")
	public String deleteReview(@RequestParam("post_id") Long postId) {
		Detail_viewDto dto = new Detail_viewDto();
		dto.setPost_id(postId);

		detailViewDao.deleteReview(dto);

		return "redirect:/community/review_view";
	}

}
