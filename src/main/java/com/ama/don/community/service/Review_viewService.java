package com.ama.don.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;

@Service
public class Review_viewService {

	@Autowired
	private PostDao postDao;

	public PostDto getPost(Long postId) {
		return postDao.findById(postId);
	}
}
