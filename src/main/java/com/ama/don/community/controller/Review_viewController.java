package com.ama.don.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.common.utils.CommunityPageVO;

@Controller
@RequestMapping("/community")
public class Review_viewController {

	@Autowired
	private PostDao postDao;

	@Autowired
	private FileDao fileDao;

	// 글쓰기 페이지 이동
	@GetMapping("/write_con")
	public String writeView() {
		return "community/write_view";
	}

	// 리뷰 목록
	@GetMapping("/review_view")
	
	// 페이징 처리 디폴트 번호
	public String reviewList(@RequestParam(defaultValue = "1") int page, Model model) {
		CommunityPageVO pageVO = new CommunityPageVO();
		pageVO.setPage(page);

			
		int totalCount = postDao.countTargetType(TargetType.COMMUNITY_REVIEW.name());
		pageVO.pageCalculate(totalCount);

		List<PostDto> list = postDao.findTargetType(TargetType.COMMUNITY_REVIEW.name(), pageVO.getRowStart(),
				pageVO.getDisplayRowCount());

		// 첨부 이미지 처리
		for (PostDto post : list) {
			post.setFileList(fileDao.findByTarget(TargetType.COMMUNITY_REVIEW.name(), post.getPost_id()));
		}

		model.addAttribute("reviewList", list);
		model.addAttribute("pageVO", pageVO);

		return "community/review_view";
	}

}
