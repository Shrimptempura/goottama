package com.ama.don.interior.controller;

import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.post.CompanyPostCreateDto;
import com.ama.don.interior.dto.post.CompanyPostDetailView;
import com.ama.don.interior.service.CompanyAuthService;
import com.ama.don.interior.service.CompanyPostService;
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
public class CompanyPostController {

    private final CompanyPostDao companyPostDao;
    private final CompanyPostService companyPostService;
    private final CompanyAuthService companyAuthService;

    // 게시글 작성 폼
    @GetMapping("/interior/myhome/{companyId}/posts/new")
    public String showCreatePostForm(@PathVariable Long companyId, Model model) {
        CompanyPostCreateDto form = new CompanyPostCreateDto();
        form.setCompanyId(companyId);
        model.addAttribute("form", form);

        // 업체 게시글이니까 업체 확인
        boolean isOwner = companyAuthService.isOwner(companyId);
        model.addAttribute("isOwner", isOwner);

        return "interior/post/form";
    }

    // 게시글 작성
    @PostMapping("/interior/myhome/{companyId}/posts/new")
    public String createPost(@PathVariable Long companyId,
                             @RequestParam("files")List<MultipartFile> files,
                             @ModelAttribute("createDto") CompanyPostCreateDto createDto,
                             RedirectAttributes ra) {
        createDto.setCompanyId(companyId);
        Long companyPostId = companyPostService.createCompanyPost(createDto, files);
        ra.addFlashAttribute("msg", "게시글 등록 완료");

        return "redirect:/interior/posts/" + companyPostId;
    }

    // 게시글 상세조회
    @GetMapping("/interior/posts/{companyPostId}")
    public String getPostDetail(@PathVariable Long companyPostId,
                                 Model model) {
        CompanyPostDetailView detail = companyPostService.getPostDetail(companyPostId);
        model.addAttribute("detail", detail);
        
        // 본인 인증
        boolean isOwner = companyAuthService.isOwner(detail.getPost().getCompanyId());
        model.addAttribute("isOwner", isOwner);

        return "interior/post/detail";
    }
}
