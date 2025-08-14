package com.ama.don.member.service;

import java.util.List;

import org.springframework.ui.Model;

import com.ama.don.community.dto.Review.ReviewPostDto;

public interface MypageDataServiceInter {
	
	List<ReviewPostDto> getUserCommunityReview(int page);

}
