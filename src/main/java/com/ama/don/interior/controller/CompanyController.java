package com.ama.don.interior.controller;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.company.*;
import com.ama.don.interior.dto.post.CompanyPostPreviewDto;
import com.ama.don.interior.dto.review.CompanyReviewDto;
import com.ama.don.interior.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyController {

    private final CompanyService companyService;
    private final FileService fileService;
    private final CompanyAuthService authService;
    private final CompanyReviewService companyReviewService;
    private final CompanyPostService companyPostService;

    // 업체 등록 폼으로 이동
    @GetMapping("/interior/new-company")
    public String companyForm(Model model) {
        model.addAttribute("detail", new CompanyCreateDto());
        model.addAttribute("location", new CompanyCreateLocationDto());
        return "interior/create-company-form";
    }

    // 업체 등록 처리
    @PostMapping("/interior/new-company")
    public String companyCreate(@ModelAttribute("detail") CompanyCreateDto detail,
                                @ModelAttribute("location") CompanyCreateLocationDto location,
                                @RequestParam("file") MultipartFile file,
                                Model model) {
        try {
            companyService.createCompany(detail, location, file);
            return "redirect:/interior/home";
        } catch (Exception e) {
            log.warn("CompanyController - 업체 등록 실패 - {}", e.getMessage());
            // check rebase substring
            model.addAttribute("detail", detail);
            model.addAttribute("location", location);
            return "interior/create-company-form";
        }
    }

    // 업체 상세페이지 탭 전환 + 요약 상자
    @GetMapping("/interior/myhome/{companyId}")
    public String showCompanyHome(@PathVariable Long companyId,
                                  @RequestParam(defaultValue = "all") String type,
                                  Model model) {
        // 요약 상자 - 좌측에 존재
        CompanySummaryDto summary = companyService.getSummaryCompany(companyId);
        model.addAttribute("summary", summary);
        model.addAttribute("companyId", companyId);

        boolean isOwner = authService.isOwner(companyId);
        model.addAttribute("isOwner", isOwner);

        // 상세 정보 탭
        if (type.equals("details")) {
            CompanyDetailDto detail = companyService.getDetailCompany(companyId);
            log.info("CompanyController - detail 탭 요청 - companyId: {}", companyId);
            model.addAttribute("detail", detail);
        } else if (type.equals("photos")) {
            List<FileDto> photoList = fileService.getFileList(TargetType.INTERIOR, companyId);
            log.info("CompanyController - photos 탭 요청 - targetType: {}, targetId: {}", TargetType.INTERIOR, companyId);
            model.addAttribute("photoList", photoList);
        } else if (type.equals("reviews")) {
            List<CompanyReviewDto> reviews = companyReviewService.listByCompanyId(companyId);
            log.info("CompanyController - reviews 탭 요청 - companyId: {}", companyId);
            model.addAttribute("reviews", reviews);
        } else if (type.equals("posts")) {
            List<CompanyPostPreviewDto> posts = companyPostService.listByCompanyId(companyId);
            log.info("CompanyController - posts 탭 요청 - companyId: {}", companyId);
            model.addAttribute("posts", posts);
        }

        String tabName = switch (type) {
            case "details" -> "/WEB-INF/views/interior/tabs/company-details.jsp";
            case "photos" -> "/WEB-INF/views/interior/tabs/company-photos.jsp";
            case "reviews" -> "/WEB-INF/views/interior/tabs/company-reviews.jsp";
            case "posts" -> "/WEB-INF/views/interior/tabs/company-posts.jsp";
            default -> "/WEB-INF/views/interior/tabs/company-all.jsp";
        };
        model.addAttribute("tabName", tabName);

        return "interior/company-layout";
    }

    // 업체 수정 폼
    @GetMapping("/interior/update-company")
    public String updateCompanyForm(Model model) {
        model.addAttribute("updateDto", companyService.getMyCompanyUpdateView());
        return "interior/update-company-form";
    }

    // 업체 수정 기능
    @PostMapping("/interior/update-company")
    public String updateCompany(@ModelAttribute("updateDto") CompanyUpdateDto updateDto,
                                @RequestParam("file") MultipartFile file) {
        Long companyId = companyService.updateCompany(updateDto, file);
        return "redirect:/interior/myhome/" + companyId;
    }

    // 업체 탈퇴
    @PostMapping("/interior/company/delete")
    public String deleteCompany() {
        companyService.deleteCompany();
        return "redirect:/interior/ihome";
    }
}
