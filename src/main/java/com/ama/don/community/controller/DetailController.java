package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;

@Controller
@RequestMapping("/community")
public class DetailController {

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	@GetMapping("/post_detail_view")
	public String detail(@RequestParam("post_id") Long postId, Model model) {
		PostDto post = postDao.findById(postId);

		if (post != null) {
			post.setFileList(fileDao.findByTarget(TargetType.COMMUNITY_REVIEW.name(), postId));
		}

		model.addAttribute("review", post);
		return "community/post_detail_view";
	}

}
