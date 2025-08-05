package com.ama.don.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.dto.PostDto;

@Service
public class PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	@Transactional
	public void createPost(PostDto postDto) {
		// 게시글 저장 post_id 자동 생성
		postDao.create(postDto);

		Long generatedPostId = postDto.getPost_id();

		// target_id를 post_id로 설정
		if (postDto.getFileList() != null && !postDto.getFileList().isEmpty()) {
			for (FileDto file : postDto.getFileList()) {
				file.setTargetId(generatedPostId);
				file.setTarget_type(postDto.getTargetType());
				fileDao.create(file);
			}
		}
	}
}
