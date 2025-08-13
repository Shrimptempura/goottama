package com.ama.don.community.controller;

import com.ama.don.community.dao.CommunityDetailDao;
import com.ama.don.community.dto.Review.ReviewDetailDto;
import com.ama.don.community.service.CommunityDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class EditController {

    private final CommunityDetailDao detailDao;
    private final CommunityDetailService detailService;

    @GetMapping("/edit")
    public String edit(@RequestParam("post_id") Long postId, Model model) {
		Long reviewId = detailDao.findReviewIdByPostId(postId);
        ReviewDetailDto dto = detailDao.findById(reviewId);
        model.addAttribute("detail", dto);
        return "community/post_edit_view"; // 수정 폼 JSP 경로
    }

    // 수정 처리
    @PostMapping("/update")
    public String update(@ModelAttribute ReviewDetailDto dto, RedirectAttributes ra) {
        detailService.updatePostAndReview(dto);
        ra.addFlashAttribute("msg", "수정되었습니다.");
        return "redirect:/community/post_detail_view?post_id=" + dto.getPost_id();
    }
}
