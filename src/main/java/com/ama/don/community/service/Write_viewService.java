package com.ama.don.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.community.Dao.CommunityWriteDao;
import com.ama.don.community.Dto.Review.ReviewWriteDto;

@Service
public class Write_viewService {

	@Autowired
	private CommunityWriteDao CommunityWriteDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	@Transactional
	public ReviewWriteDto createReviewWithPost(Long userId, ReviewWriteDto dto) {
		// 게시글 생성
		PostDto postDto = new PostDto();

		postDto.setUser_id(userId);
		postDto.setTargetType(TargetType.COMMUNITY_REVIEW);

		postDao.polyTest(postDto);
		Long postId = postDto.getPost_id();
		System.out.println("생성된 post_id@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@: " + postId);
		System.out.println("------------------------------------------------");
		dto.setPost_id(postId);
		dto.setUser_id(userId);
		dto.setTargetId(postId);
		CommunityWriteDao.createReview(dto);
		System.out.println("연결된 target_id@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@): " + dto.getTargetId());
		System.out.println("target_type@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:" + dto.getTargetType());
		System.out.println("------------------------------------------------");
		
		// file 테이블에서 target_id가 null인 항목들을 해당 postId로 변경
		System.out.println("[파일 테이블 업데이트 시작]");
		System.out.println("조건 - target_type: " + TargetType.COMMUNITY_REVIEW);
		System.out.println("조건 - file_uploader(userId): " + userId);
		System.out.println("조건 - oldTargetId: null (IS NULL)");
		System.out.println("변경할 newTargetId: " + postId);
		System.out.println("------------------------------------------------");
		fileDao.updateTargetId(TargetType.COMMUNITY_REVIEW, userId.toString(), null, postId);
		
		System.out.println("[파일 테이블 업데이트 완료]");
		System.out.println("------------------------------------------------");

		return dto;
	}
}
