package com.ama.don.member.service;

import java.util.List;

import com.ama.don.community.dto.CommunityPostListDto;
import com.ama.don.member.dto.MemberDto;

public interface MypageDataServiceInter {
	
	List<CommunityPostListDto> getUserCommunityReview(MemberDto memberDto);

}
