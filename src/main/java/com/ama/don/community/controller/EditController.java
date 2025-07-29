package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.community.dao.Detail_viewDao;
import com.ama.don.community.dto.Detail_viewDto;

@Controller
@RequestMapping("/community")
public class EditController {

	@Autowired
	private Detail_viewDao detail_viewDao;

	// 수정 페이지
	@GetMapping("/edit")
	public String editForm(@RequestParam("post_id") Long postId, Model model) {
		Detail_viewDto dto = detail_viewDao.selectReviewById(postId);
		model.addAttribute("review", dto);
		return "community/post_edit_view";
	}

	// 수정된 내용 저장
	@PostMapping("/edit")
	public String editPost(@ModelAttribute Detail_viewDto dto) {
		detail_viewDao.updateReview(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getPost_id();
	}

}
