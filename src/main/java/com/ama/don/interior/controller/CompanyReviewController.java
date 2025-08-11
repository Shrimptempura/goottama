package com.ama.don.interior.controller;

import com.ama.don.interior.service.CompanyReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyReviewController {

    private final CompanyReviewService companyReviewService;

    // 리뷰 등록 폼으로 이동
    @GetMapping("/interior/myhome/{companyId}/reviews-add")
    public String addReviewForm(@PathVariable Long companyId, Model model) {
        return "interior/create-company-review-form";
    }

    // 리뷰 등록 처리
    @PostMapping("interior/myhome/{companyId}review-add")
    public String addReview(@PathVariable Long companyId) {
        return "redirect:/interior/myhome/{companyId}/reviews";     // 상세페이지 리뷰로 생각중
    }




}
