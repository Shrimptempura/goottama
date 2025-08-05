package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.enums.TargetType;
import com.ama.don.community.Dao.CommunityDetailDao;
import com.ama.don.community.Dto.Review.ReviewDetailDto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/community")
public class DetailController {

	@Autowired
	private CommunityDetailDao communityDetailDao;

	@Autowired
	private FileDao fileDao;

	@GetMapping("/post_detail_view")
	public String detail(@RequestParam("post_id") Long postId, Model model, HttpSession session) {

		// ****나중에 적용예정****
//		// 세션에 조회 기록 가져오기
//		String viewKey = "viewed_post_" + postId;
//		Object hasViewed = session.getAttribute(viewKey);
//
//		// 처음 보는 글이면 조회수 증가
//		if (hasViewed == null) {
//			postDao.increaseViewCount(postId);
//			session.setAttribute(viewKey, true); // 본 글로 기록
//		}

		// post_id로부터 review_id 조회
		Long reviewId = communityDetailDao.findReviewIdByPostId(postId);

		// 기본 조회수 증가(임시 조치)
		communityDetailDao.increaseViewCount(reviewId);

		// 게시글 조회
		ReviewDetailDto review = communityDetailDao.findById(reviewId);

		// 이미지 조회
		if (review != null) {
			review.setFileList(fileDao.findByTargetId(TargetType.COMMUNITY_REVIEW, review.getPost_id()));
		}

		model.addAttribute("review", review);
		return "community/post_detail_view";
	}

}
