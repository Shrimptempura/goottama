package com.ama.don.community.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ama.don.community.dao.CommunityDetailDao;
import com.ama.don.community.dto.Review.ReviewDetailDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityDetailService {

	private final CommunityDetailDao detailDao;

	@Transactional
	public void updatePostAndReview(ReviewDetailDto dto) {

		detailDao.updateReview(dto);
	}

	@Transactional
	public void deletePost(Long postId) {
		// (선택) 댓글 소프트 딜리트
		detailDao.softDeleteCommentsByTarget("COMMUNITY_REVIEW", postId);

		// (선택) 파일 삭제
		detailDao.deleteFilesByTarget("COMMUNITY_REVIEW", postId);

		// 1) 리뷰(자식) 먼저 삭제
		detailDao.deleteReviewByPostId(postId);

		// 2) 포스트(부모) 삭제
		detailDao.delete(postId);
	}
}
