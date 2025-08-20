package com.ama.don.admin.controller;

import com.ama.don.admin.dto.postForAdminDTO.PostSearchForAdminDTO;
import com.ama.don.admin.service.postViewerForAdmin.GetPostDetailService;
import com.ama.don.admin.service.postViewerForAdmin.GetPostListService;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostViewController {

    private final GetPostListService getPostListService;
    private final GetPostDetailService getPostDetailService;

    public PostViewController(GetPostDetailService getPostDetailService,
                              GetPostListService getPostListService) {
        this.getPostListService = getPostListService;
        this.getPostDetailService = getPostDetailService;
    }

    @GetMapping("/admin/posts/posts_page")
    public String postPageForAdmin(Model model,
                                   @ModelAttribute SearchVO searchVO,
                                   @ModelAttribute PostSearchForAdminDTO postSearchForAdminDTO) {
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (postSearchForAdminDTO == null) {
            postSearchForAdminDTO = new PostSearchForAdminDTO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("postSearchForAdminDTO", postSearchForAdminDTO);

        getPostListService.execute(model);

        return "admin/posts/posts_page";
    }

    @PostMapping("/admin/posts/post_list")
    public String postListForAdmin(Model model,
                                   @ModelAttribute SearchVO searchVO,
                                   @ModelAttribute PostSearchForAdminDTO postSearchForAdminDTO) {

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("postSearchForAdminDTO", postSearchForAdminDTO);

        getPostListService.execute(model);

        return "admin/posts/post_list";
    }

    @GetMapping("/admin/posts/post_data_modal")
    public String postDataModal(Model model,
                                   @RequestParam("postId") Long postId) {
        model.addAttribute("postId", postId);
        getPostDetailService.execute(model);
        return "admin/posts/post_data_modal";
    }
}
