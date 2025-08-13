package com.ama.don.community.controller;

import com.ama.don.community.service.CommunityDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class DeleteController {

	private final CommunityDetailService detailService;

	@PostMapping("/delete")
	public String delete(@RequestParam("post_id") Long postId, RedirectAttributes ra) {
		detailService.deletePost(postId);
		ra.addFlashAttribute("msg", "삭제되었습니다.");
		return "redirect:/community/review_view";
	}
}
