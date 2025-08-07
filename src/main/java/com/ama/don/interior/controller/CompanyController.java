package com.ama.don.interior.controller;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.company.*;
import com.ama.don.interior.service.CompanyService;
import com.ama.don.interior.service.CompanyServiceImpl;
import com.ama.don.interior.service.FileService;
import com.ama.don.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyController {

    private final CompanyService companyService;
    private final FileService fileService;
    private final CompanyServiceImpl companyServiceImpl;

    // 업체 등록 폼으로 이동
    @GetMapping("interior/new-company")
    public String companyForm(Model model) {
        model.addAttribute("detail", new CompanyCreateDto());
        model.addAttribute("location", new CompanyCreateLocationDto());
        return "interior/create-company-form";
    }

    // 업체 등록 처리
    @PostMapping("interior/new-company")
    public String companyCreate(@ModelAttribute("detail") CompanyCreateDto detail,
                                @ModelAttribute("location") CompanyCreateLocationDto location,
                                @ModelAttribute("file") MultipartFile file,
                                HttpSession session,
                                Model model) {
        Long userId = (Long) session.getAttribute("userId");
        log.info("CompanyController - 세션 확인 - userId: {}", userId);

        try {
            companyService.createCompany(userId, detail, location, file);
            return "redirect:/interior/home";
        } catch (Exception e) {
            log.warn("CompanyController - 업체 등록 실패 - {}", e.getMessage());
//            model.addAttribute("detail", detail);
//            model.addAttribute("location", location);
            return "interior/create-company-form";
        }
    }

    // 업체 상세페이지 탭 전환 + 요약 상자
    @GetMapping("interior/myhome/{companyId}")
    public String showCompanyHome(@PathVariable Long companyId,
                                  @RequestParam(defaultValue = "all") String type,
                                  Model model) {
        // 요약 상자 - 좌측에 존재
        CompanySummaryDto summary = companyService.getSummaryCompany(companyId);
        model.addAttribute("summary", summary);
        model.addAttribute("companyId", companyId);

        // 상세 정보 탭
        if (type.equals("details")) {
            CompanyDetailDto detail = companyService.getDetailCompany(companyId);
            log.info("CompanyController - detail 요청 - companyId: {}", companyId);
            model.addAttribute("detail", detail);
        } else if (type.equals("photos")) {
            List<FileDto> photoList = fileService.getFileList(TargetType.INTERIOR, companyId);
            log.info("CompanyController - File 요청 - userId: {}, targetType: {}, targetId: {}", null, TargetType.INTERIOR, companyId);
            model.addAttribute("photoList", photoList);
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
    @GetMapping("interior/update-company")
    public String updateCompanyForm(@RequestParam Long companyId, Model model) {
        CompanyUpdateDto form = companyService.getUpdateView(companyId);
        model.addAttribute("form", form);
        return "interior/update-company-form";
    }

    // 업체 수정 기능
    @PostMapping("interior/update-company")
    public String updateCompany(@RequestParam("companyId") Long companyId,
                                @ModelAttribute("updateDto") CompanyUpdateDto updateDto,
                                @ModelAttribute("file") MultipartFile file) {
        companyService.updateCompany(updateDto, companyId, file);
        return "redirect:/interior/myhome/" + companyId;
    }
}
