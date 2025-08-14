package com.ama.don.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dao.CommunityPostQueryDao;
import com.ama.don.community.dto.CommunityPostListDto;
import com.ama.don.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageDataService implements MypageDataServiceInter{
	
	private final CommunityPostQueryDao communityPostQueryDao;
	private final LoginMemberService loginMemberService;

	@Override
	public List<CommunityPostListDto> getUserCommunityReview(MemberDto memberDto) {
		
//		memberDto = loginMemberService.getCurrentLoginMemberDto();
//		Long userId = memberDto.getUser_id();
//		String targetType = TargetType.COMMUNITY_REVIEW.name();
//		
//		List<CommunityPostListDto> list = communityPostQueryDao.findMyPostsByType(userId, targetType);
		
		return null;
	}

}
