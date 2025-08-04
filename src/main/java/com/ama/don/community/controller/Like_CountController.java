package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ama.don.community.Dao.CommunityDetailDao;

@RestController
@RequestMapping("/community/like")
public class Like_CountController {

	@Autowired
	private CommunityDetailDao communityDetailDao;


	@PostMapping("/toggle")
	public String toggleLike(@RequestParam("post_id") Long postId) {
		communityDetailDao.increaseLikeCount(postId);
		int likeCount = communityDetailDao.findById(postId).getReview_like_count();
		return String.valueOf(likeCount);
	}
}