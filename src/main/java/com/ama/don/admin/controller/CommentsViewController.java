package com.ama.don.admin.controller;

import com.ama.don.admin.dto.commentsForAdminDTO.CommentsSearchForAdminDTO;
import com.ama.don.admin.service.commentsForAdminService.GetCommentDetailService;
import com.ama.don.admin.service.commentsForAdminService.GetCommentsListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentsViewController {

    private final GetCommentsListService getCommentsListService;
    private final GetCommentDetailService getCommentDetailService;

    public CommentsViewController(GetCommentsListService getCommentsListService,
                                  GetCommentDetailService getCommentDetailService) {
        this.getCommentsListService = getCommentsListService;
        this.getCommentDetailService = getCommentDetailService;
    }

    @GetMapping("/admin/comments/comments_page")
    public String commentPageForAdmin(Model model,
                                      @ModelAttribute SearchVO searchVO,
                                      @ModelAttribute CommentsSearchForAdminDTO commentsSearchForAdminDTO) {
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (commentsSearchForAdminDTO == null) {
            commentsSearchForAdminDTO = new CommentsSearchForAdminDTO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commentsSearchForAdminDTO", commentsSearchForAdminDTO);

        getCommentsListService.execute(model);

        return "admin/comments/comments_page";
    }

    @PostMapping("/admin/comments/comment_list")
    public String commentListForAdmin(Model model,
                                      @ModelAttribute SearchVO searchVO,
                                      @ModelAttribute CommentsSearchForAdminDTO commentsSearchForAdminDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("commentsSearchForAdminDTO", commentsSearchForAdminDTO);

        getCommentsListService.execute(model);

        return "admin/comments/comment_list";
    }

    @GetMapping("/admin/comments/comment_data_modal")
    public String commentDataModal(Model model,
                                   @RequestParam("commentId") Long commentId) {
        model.addAttribute("commentId", commentId);
        getCommentDetailService.execute(model);
        return "admin/comments/comment_data_modal";
    }
}
