package com.ama.don.community.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.common.utils.CommunityPageVO;
import com.ama.don.community.dao.CommunityPostDao;
import com.ama.don.community.dto.Review.ReviewPostDto;

@Controller
@RequestMapping("/community")
public class Review_viewController {

	@Autowired
	private CommunityPostDao CommunityPostDao;

	@Autowired
	private FileDao fileDao;

	// 글쓰기 페이지 이동
	@GetMapping("/write_con")
	public String writeView() {
		return "community/write_view";
	}

	// 리뷰 목록
	@GetMapping("/review_view")
	public String reviewList(@RequestParam(defaultValue = "1") int page, Model model) {
		CommunityPageVO pageVO = new CommunityPageVO();
		pageVO.setPage(page);

		String targetType = TargetType.COMMUNITY_REVIEW.name();

		int totalCount = CommunityPostDao.countTargetType(targetType);
		pageVO.pageCalculate(totalCount);

		List<ReviewPostDto> list = CommunityPostDao.findTargetType(targetType, pageVO.getRowStart(),
				pageVO.getDisplayRowCount());

		for (ReviewPostDto review : list) {
			List<FileDto> fileList = fileDao.findByTargetId(TargetType.COMMUNITY_REVIEW, review.getPost_id());
			review.setFileList(fileList);
		}

		model.addAttribute("reviewList", list);
		model.addAttribute("pageVO", pageVO);

		return "community/review_view";
	}
		// 조회수 와 좋아요 수 조회
	@GetMapping("/review_live_counts")
	@ResponseBody
	public List<Map<String, Object>> getLiveReviewCounts() {
	    return CommunityPostDao.findReviewCounts();
	}


}