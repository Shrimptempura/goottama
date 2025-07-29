package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.community.dao.Detail_viewDao;
import com.ama.don.community.dto.Detail_viewDto;

@Controller
@RequestMapping("/community")
public class DetailController {

	@Autowired
	private Detail_viewDao detail_viewDao;

	@GetMapping("/post_detail_view")
	public String postDetail(@RequestParam("post_id") Long postId, Model model) {
		Detail_viewDto detail = (Detail_viewDto) detail_viewDao.selectReviewById(postId);
		model.addAttribute("review", detail);
		return "community/post_detail_view";
	}
}
