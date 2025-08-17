package com.ama.don.interior.controller;

import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.service.CompanyCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyCommentController {

    private final CompanyCommentService companyCommentService;

    // 댓글 작성
    @PostMapping("/interior/posts/{companyPostId}/comments")
    public String addComment(@PathVariable Long companyPostId,
                             @RequestParam(required = false) Long parentCommentId,
                             @RequestParam("content") String content,
                             RedirectAttributes ra) {
        try {
            companyCommentService.addComment(companyPostId, parentCommentId, content);
            ra.addFlashAttribute("msg", "댓글이 등록되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "댓글 작성 실패");
        }

        return "redirect:/interior/posts/" + companyPostId;
    }

    // 댓글 수정
    @PostMapping("/interior/comments/{commentId}/edit")
    public String editComment(@PathVariable Long commentId,
                              @RequestParam("content") String content,
                              @RequestParam("companyPostId") Long companyPostId,
                              RedirectAttributes ra) {
        try {
            companyCommentService.updateMyComment(commentId, content);
            ra.addFlashAttribute("msg", "댓글이 수정되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "댓글 수정 실패");
            return "redirect:/interior/posts/" + companyPostId;
        }

        return "redirect:/interior/posts/" + companyPostId;
    }

    // 댓글 삭제
    @PostMapping("/interior/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId,
                                @RequestParam("companyPostId") Long companyPostId,
                                RedirectAttributes ra) {

        try {
            companyCommentService.deleteMyComment(commentId);
            ra.addFlashAttribute("msg", "댓글이 삭제 되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "댓글 삭제 실패");
            return "redirect:/interior/posts/" + companyPostId;
        }

        return "redirect:/interior/posts/" + companyPostId;
    }

}
