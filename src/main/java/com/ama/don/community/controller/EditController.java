package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.common.dto.PostDto;
import com.ama.don.community.Dao.CommunityDetailDao;
import com.ama.don.community.Dto.Review.ReviewDetailDto;

@Controller
@RequestMapping("/community")
public class EditController {

	@Autowired
	private CommunityDetailDao communityDetailDao;

	// 수정 페이지
	@GetMapping("/edit")
	public String editForm(@RequestParam("post_id") Long postId, Model model) {
		ReviewDetailDto dto = communityDetailDao.findById(postId);
		model.addAttribute("review", dto);
		return "community/post_edit_view";
	}

	// 수정된 내용 저장
	@PostMapping("/edit")
	public String editPost(@ModelAttribute PostDto dto) {
		communityDetailDao.update(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getPost_id();
	}
}
