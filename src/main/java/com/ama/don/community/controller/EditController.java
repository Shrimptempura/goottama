package com.ama.don.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;

@Controller
@RequestMapping("/community")
public class EditController {

	@Autowired
	private PostDao postDao;

	// 수정 페이지
	@GetMapping("/edit")
	public String editForm(@RequestParam("post_id") Long postId, Model model) {
		PostDto dto = postDao.findById(postId);
		model.addAttribute("review", dto);
		return "community/post_edit_view";
	}

	// 수정된 내용 저장
	@PostMapping("/edit")
	public String editPost(@ModelAttribute PostDto dto) {
		postDao.update(dto);
		return "redirect:/community/post_detail_view?post_id=" + dto.getPost_id();
	}
}
