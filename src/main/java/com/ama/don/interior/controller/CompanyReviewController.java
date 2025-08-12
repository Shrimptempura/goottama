package com.ama.don.interior.controller;

import com.ama.don.interior.dto.review.CompanyHomeReviewDto;
import com.ama.don.interior.dto.review.CompanyReviewCreateDto;
import com.ama.don.interior.dto.review.CompanyReviewDto;
import com.ama.don.interior.service.CompanyAuthService;
import com.ama.don.interior.service.CompanyReviewService;
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
    private final CompanyAuthService companyAuthService;

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
        ra.addAttribute("companyId", companyId);
        Long reviewId = companyReviewService.createReview(form, files);

        return "redirect:/interior/myhome/{companyId}?type=reviews";     // 상세페이지 리뷰로 생각중
    }

    // 리뷰 상세보기
    @GetMapping("/interior/review-detail")
    public String getReviewDetail(@RequestParam Long reviewId,
                                  Model model,
                                  RedirectAttributes ra) {
        CompanyReviewDto review = companyReviewService.getReviewDetail(reviewId);
        model.addAttribute("review", review);
        ra.addAttribute("reviewId", reviewId);
        return "interior/review-detail";
    }





}
