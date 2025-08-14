package com.ama.don.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.common.utils.CommunityPageVO;
import com.ama.don.community.dao.CommunityPostDao;
import com.ama.don.community.dto.Review.ReviewPostDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageDataService implements MypageDataServiceInter{
	
	private final CommunityPostDao communityPostDao;
	private final FileDao fileDao;

	@Override
	public List<ReviewPostDto> getUserCommunityReview(int page) {

		CommunityPageVO pageVO = new CommunityPageVO();
		pageVO.setPage(page);
		
		String targetType = TargetType.COMMUNITY_REVIEW.name();
		
		int totalCount = communityPostDao.countTargetType(targetType);
		pageVO.pageCalculate(totalCount);
		
		List<ReviewPostDto> list = communityPostDao.findTargetType(targetType, pageVO.getRowStart(), pageVO.getDisplayRowCount());
		
		for (ReviewPostDto reviewPostDto : list) {
			List<FileDto> fileList = fileDao.findByTargetId(TargetType.COMMUNITY_REVIEW, reviewPostDto.getPost_id());
			reviewPostDto.setFileList(fileList);
		}
		
		return list;
	}

}
