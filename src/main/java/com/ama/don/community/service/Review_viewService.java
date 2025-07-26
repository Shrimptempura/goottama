package com.ama.don.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.don.community.dao.Review_viewDao;
import com.ama.don.community.dto.Review_viewDto;

@Service
public class Review_viewService {

	@Autowired
	private Review_viewDao reviewDao;

	public Review_viewDto getPost(Long postId) {
		return reviewDao.selectReviewById(postId);
	}
}
