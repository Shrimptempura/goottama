package com.ama.don.community.service;

import org.springframework.ui.Model;

import com.ama.don.community.dao.ReviewDao;

public class ReviewService implements CommunityService {

	private ReviewDao reviewdao;

	public ReviewService(ReviewDao reviewDao) {
		this.reviewdao = reviewdao;
	}

	@Override
	public void execute(Model model) {

	}

}
