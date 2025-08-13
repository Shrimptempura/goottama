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

		dto.setTargetType(com.ama.don.common.enums.TargetType.COMMUNITY_REVIEW);
		dto.setTargetId(dto.getReview_id());

		java.util.List<com.ama.don.common.dto.FileDto> files = detailDao.selectFilesByTarget(dto.getTargetType().name(),
				dto.getTargetId());
		dto.setFileList(files);

		model.addAttribute("detail", dto);
		model.addAttribute("fileList", files);
		return "community/post_edit_view"; // 수정 폼 JSP 경로
	}

	// 수정 처리
	@PostMapping("/update")
	public String update(@ModelAttribute ReviewDetailDto dto,
			@RequestParam(value = "deleted_file_ids", required = false) String deletedFileIds, RedirectAttributes ra) {

		detailService.updatePostAndReview(dto);

		// ★ 파일 삭제
		if (deletedFileIds != null && !deletedFileIds.isBlank()) {
			java.util.List<Long> ids = java.util.Arrays.stream(deletedFileIds.split(",")).map(String::trim)
					.filter(s -> !s.isEmpty()).map(Long::valueOf).toList();
			detailService.deleteFilesByIds(ids);
		}

		ra.addFlashAttribute("msg", "수정되었습니다.");
		return "redirect:/community/post_detail_view?post_id=" + dto.getPost_id();
	}

}
