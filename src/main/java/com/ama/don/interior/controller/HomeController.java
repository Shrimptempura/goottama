package com.ama.don.interior.controller;

import com.ama.don.interior.dto.company.CompanyHomeDto;
import com.ama.don.interior.dto.review.CompanyHomeReviewDto;
import com.ama.don.interior.service.CompanyReviewService;
import com.ama.don.interior.service.CompanyService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final CompanyService companyService;
    private final CompanyReviewService companyReviewService;

    @GetMapping("/interior/home")
    public String interiorHome() {
        return "interior/home";
    }

    // 인테리어의 홈
    // 업체 랜덤 리스트, 업체에 대한 리뷰 최신 리스트
    // 업체 게시글(최신, 인기, 랜덤, 지역)
    @GetMapping("/interior/ihome")
    public String showIhome(@RequestParam(defaultValue = "6") @Min(1) @Max(10) int limit,
                                   Model model) {
        List<CompanyHomeDto> companyList = companyService.getHomeCompanyList(limit);
        List<CompanyHomeReviewDto> reviewList = companyReviewService.findRecentForHome();

        model.addAttribute("companyList", companyList);
        model.addAttribute("reviewList", reviewList);
        return "interior/ihome";
    }





}
