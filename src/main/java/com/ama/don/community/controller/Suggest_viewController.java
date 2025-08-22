package com.ama.don.community.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.community.dto.Review.ReviewPostDto;
import com.ama.don.community.service.Write_viewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class Suggest_viewController {

	private final Write_viewService write_viewService;

	// 기존 경로
	@GetMapping("/recommend")
	public String recommend(Model model) {
		List<ReviewPostDto> popularList = write_viewService.getPopularReviews(10);
		model.addAttribute("reviewList", popularList);
		return "community/recommend";
	}

	// suggest_view 로도 동일 페이지 제공
	@GetMapping("/suggest_view")
	public String suggestView(Model model) {
		List<ReviewPostDto> popularList = write_viewService.getPopularReviews(10);
		model.addAttribute("popularList", popularList);
		return "community/suggest_view";
	}
}
