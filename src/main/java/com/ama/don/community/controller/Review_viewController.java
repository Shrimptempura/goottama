package com.ama.don.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;

@Controller
@RequestMapping("/community")
public class Review_viewController {

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	@GetMapping({ "/review", "/review/list", "/review_view" })
	public String reviewList(Model model) {
		List<PostDto> list = postDao.findByTarget(TargetType.COMMUNITY_REVIEW.name(), 1L); // target_id 반드시 지정
		for (PostDto post : list) {
			post.setFileList(fileDao.findByTarget(TargetType.COMMUNITY_REVIEW.name(), post.getPost_id()));
		}
		model.addAttribute("reviewList", list);
		return "community/review_view";
	}

	@GetMapping("/write_con")
	public String writeView() {
		System.out.println("write_view() controller");
		return "community/write_view";
	}
}
