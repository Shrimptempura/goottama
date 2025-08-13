package com.ama.don.interior.controller;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.review.CompanyReviewCreateDto;
import com.ama.don.interior.dto.review.CompanyReviewDto;
import com.ama.don.interior.dto.review.CompanyReviewUpdateDto;
import com.ama.don.interior.service.CompanyReviewService;
import com.ama.don.interior.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyReviewController {

    private final CompanyReviewService companyReviewService;
    private final FileService fileService;

    // 리뷰 등록 폼으로 이동
    @GetMapping("/interior/myhome/{companyId}/review-form")
    public String showCreateReviewForm(@PathVariable Long companyId, Model model) {
        CompanyReviewCreateDto form = new CompanyReviewCreateDto();
        form.setCompanyId(companyId);
        model.addAttribute("form", form);

        return "interior/review-form";
    }

    // 리뷰 등록 처리
    @PostMapping("/interior/myhome/{companyId}/review-form")
    public String createReview(@PathVariable Long companyId,
                            @ModelAttribute("form") CompanyReviewCreateDto form,
                            @RequestParam("files") List<MultipartFile> files,
                            RedirectAttributes ra) {
        form.setCompanyId(companyId);
        companyReviewService.createReview(form, files);

        // 상세페이지 리뷰로 생각중
        return "redirect:/interior/myhome/" + companyId + "?type=reviews&focus=" + form.getReviewId();
    }

    // 리뷰 상세보기(레거시)
    @GetMapping("/interior/review-detail")
    public String getReviewDetail(@RequestParam Long reviewId,
                                  Model model,
                                  RedirectAttributes ra) {
        CompanyReviewDto review = companyReviewService.getReviewDetail(reviewId);
        model.addAttribute("review", review);
        ra.addAttribute("reviewId", reviewId);
        return "interior/review-detail";
    }

    // 리뷰 수정 폼
    @GetMapping("/interior/myhome/{companyId}/reviews/{reviewId}/edit")
    public String showReviewEditForm(@PathVariable Long companyId,
                                     @PathVariable Long reviewId,
                                     Model model,
                                     RedirectAttributes ra) {
        if (!companyReviewService.isAuthor(reviewId)) {
            ra.addFlashAttribute("error", "수정 권한이 없습니다");
            return "redirect:/interior/myhome/" + companyId + "?type=reviews&focus=" + reviewId;
        }
        model.addAttribute("companyId", companyId);
        CompanyReviewUpdateDto form = companyReviewService.getEditView(reviewId);
        model.addAttribute("form", form);

        model.addAttribute("images",
                fileService.getFileList(TargetType.INTERIOR_REVIEW, reviewId));
        return "interior/review-edit";
    }

    // 리뷰 수정 처리
    @PostMapping("/interior/myhome/{companyId}/reviews/{reviewId}/edit")
    public String updateReview(@PathVariable Long companyId,
                               @PathVariable Long reviewId,
                               @ModelAttribute("form") CompanyReviewUpdateDto form,
                               @RequestParam("files") List<MultipartFile> files,
                               RedirectAttributes ra) {
        try {
            form.setReviewId(reviewId);
            companyReviewService.updateReview(form, files);
            ra.addFlashAttribute("msg", "리뷰가 수정되었습니다.");
            return "redirect:/interior/myhome/" + companyId + "?type=reviews&focus=" + reviewId;
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());      // 이미지는 최소 1장이 필요합니다.
            return "redirect:/interior/myhome/" + companyId + "/reviews/" + reviewId + "/edit";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "리뷰 수정 중 오류가 발생했습니다 잠시 후 다시 시도해 주세요");
            return "redirect:/interior/myhome/" + companyId + "/reviews/" + reviewId + "/edit";
        }
    }

    // 리뷰 삭제
    @PostMapping("/interior/myhome/{companyId}/reviews/{reviewId}/delete")
    public String deleteReview(@PathVariable Long companyId,
                               @PathVariable Long reviewId,
                               RedirectAttributes ra) {
        try {
            companyReviewService.deleteReview(reviewId);
            ra.addFlashAttribute("msg", "리뷰가 삭제되었습니다");
            return "redirect:/interior/myhome/" + companyId + "?type=reviews";
        } catch (Exception e) {
            log.error("CRController - 리뷰 삭제 실패 - reviewId: {}", reviewId, e);
            ra.addFlashAttribute("error", "리뷰 삭제에 실패했습니다. 관리자에게 문의바랍니다.");
            return "redirect:/interior/myhome/" + companyId + "?type=reviews&focus=" + reviewId;
        }
    }

}
