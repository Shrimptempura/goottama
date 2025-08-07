package com.ama.don.community.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dto.Review.ReviewWriteDto;
import com.ama.don.community.service.Write_viewService;

@Controller
@RequestMapping("/community")
public class Write_viewController {

	@Autowired
	private Write_viewService write_viewService;

	@GetMapping("/write_view")
	public String write_form() {

		return "community/write_view";
	}

	@PostMapping("/write")
	@ResponseBody
	public Map<String, Object> write_post(@RequestParam("review_title") String reviewTitle,
			@RequestParam("review_content") String reviewContent, @RequestParam("target_type") String targetTypeStr) {

		Long userId = 1L;
		
		ReviewWriteDto dto = new ReviewWriteDto();
		dto.setReview_title(reviewTitle);
		dto.setReview_content(reviewContent);
		dto.setTargetType(TargetType.valueOf(targetTypeStr));

		// 서비스 호출
		ReviewWriteDto savedDto = write_viewService.createReviewWithPost(userId, dto);
		Long postId = savedDto.getPost_id();

		// JSON 응답
		Map<String, Object> result = new HashMap<>();
		result.put("post_id", postId);
		return result;
	}

}
