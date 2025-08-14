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
	public void deleteFilesByIds(java.util.List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			detailDao.deleteFilesByIds(ids);
		}
	}

	@Transactional
	public void deletePost(Long postId) {
		// 댓글 소프트 딜리트
		detailDao.softDeleteCommentsByTarget("COMMUNITY_REVIEW", postId);

		// 파일 삭제
		detailDao.deleteFilesByTarget("COMMUNITY_REVIEW", postId);

		// 리뷰 자식 먼저 삭제
		detailDao.deleteReviewByPostId(postId);

		// 포스트 부모 삭제
		detailDao.delete(postId);
	}
}
