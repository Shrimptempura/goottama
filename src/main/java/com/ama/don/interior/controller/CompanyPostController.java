package com.ama.don.interior.controller;

import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;
import com.ama.don.interior.dto.post.CompanyPostCreateDto;
import com.ama.don.interior.dto.post.CompanyPostDetailView;
import com.ama.don.interior.dto.post.CompanyPostUpdateDto;
import com.ama.don.interior.service.CompanyAuthService;
import com.ama.don.interior.service.CompanyCommentService;
import com.ama.don.interior.service.CompanyPostService;
import com.ama.don.interior.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyPostController {

    private final CompanyPostDao companyPostDao;
    private final CompanyPostService companyPostService;
    private final CompanyAuthService companyAuthService;
    private final FileService fileService;
    private final CompanyCommentService companyCommentService;

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
    public String getPostDetail(@PathVariable Long companyPostId, Model model) {
        CompanyPostDetailView detail = companyPostService.getPostDetail(companyPostId);
        model.addAttribute("detail", detail);
        
        // 본인 인증
        boolean isOwner = companyAuthService.isOwner(detail.getPost().getCompanyId());
        model.addAttribute("isOwner", isOwner);

        List<CompanyCommentTreeDto> comments = companyCommentService.listComments(companyPostId);
        model.addAttribute("comments", comments);

        return "interior/post/detail";
    }

    // 게시글 수정 뷰
    @GetMapping("/interior/posts/{companyPostId}/edit")
    public String showEditPostform(@PathVariable Long companyPostId, Model model) {
        CompanyPostUpdateDto form = companyPostService.getEditView(companyPostId);
        model.addAttribute("form", form);

        List<FileDto> images = Collections.emptyList();
        try {
            images = fileService.getFileList(TargetType.INTERIOR_POST, companyPostId);
        } catch (Exception e) {
            log.warn("CompanyPostController - 수정 뷰 이미지 조회 실패 - companyPostId: {}", companyPostId, e);
            model.addAttribute("error", "이미지를 찾을 수 없습니다.");
        }
        model.addAttribute("images", images);

        return "interior/post/edit";
    }

    // 게시글 수정
    @PostMapping("/interior/posts/{companyPostId}/edit")
    public String updatePost(@PathVariable Long companyPostId,
                             @ModelAttribute("form") CompanyPostUpdateDto form,
                             @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        form.setCompanyPostId(companyPostId);

        try {
            companyPostService.updatePost(form, files);
        } catch (Exception e) {
            log.error("CompanyPostService - 게시글 수정 실패 - companyPostId: {}", companyPostId, e);
            return "redirect:/interior/posts/" + companyPostId + "/edit";
        }

        return "redirect:/interior/posts/" + companyPostId;
    }

    // 게시글 삭제
    @PostMapping("/interior/posts/{companyPostId}/delete")
    public String deletePost(@PathVariable Long companyPostId,
                             RedirectAttributes ra) {
        try {
            Long companyId = companyPostService.deletePost(companyPostId);
            return "redirect:/interior/myhome/" + companyId + "?type=posts";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "삭제 중 오류가 발생했습니다.");
            return "redirect:/interior/posts/" + companyPostId;
        }
    }

}
