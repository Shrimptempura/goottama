package com.ama.don.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.community.dao.Review_viewDao;
import com.ama.don.community.dto.Review_viewDto;

@Controller
@RequestMapping("/community")
public class Review_viewController {

	@Autowired
	private Review_viewDao review_viewDao;

	@GetMapping({ "/review", "/review/list", "/review_view" })
	public String reviewList(Model model) {
		List<Review_viewDto> list = review_viewDao.selectAllReviews();
		model.addAttribute("reviewList", list);
		return "community/review_view";
	}

	@GetMapping("/write_con")
	public String writeView() {
		System.out.println("write_view() controller");
		return "community/write_view";
	}

	@GetMapping("/post_detail_view")
	public String postDetail(@RequestParam("post_id") Long postId, Model model) {
		Review_viewDto review = review_viewDao.selectReviewById(postId);
		model.addAttribute("review", review);
		return "community/post_detail_view";
	}

}
