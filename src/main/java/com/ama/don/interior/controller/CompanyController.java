package com.ama.don.interior.controller;

import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
import com.ama.don.interior.service.CompanyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyController {

    private final CompanyService companyService;

    // 업체 등록 폼으로 이동
    @GetMapping("interior/newCompany")
    public String companyForm(Model model) {
        model.addAttribute("detail", new CompanyCreateDto());
        model.addAttribute("location", new CompanyCreateLocationDto());
        return "interior/create-company-form";
    }

    // 업체 등록 처리
    @PostMapping("interior/newCompany")
    public String companyCreate(@ModelAttribute("detail") CompanyCreateDto detail,
                                @ModelAttribute("location") CompanyCreateLocationDto location,
                                @ModelAttribute("file") MultipartFile file,
                                HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        try {
            companyService.createCompany(userId, detail, location, file);
            return "redirect:/interior/home";
        } catch (Exception e) {
            log.warn("CompanyController - 업체 등록 실패 - {}", e.getMessage());
            return "redirect:/interior/newCompany";
        }
    }



}
