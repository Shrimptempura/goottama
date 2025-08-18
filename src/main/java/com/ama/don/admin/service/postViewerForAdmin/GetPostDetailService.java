package com.ama.don.admin.service.postViewerForAdmin;

import com.ama.don.admin.dao.PostForAdminIDao;
import com.ama.don.admin.dto.postForAdminDTO.PostForAdminDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class GetPostDetailService {

    private final PostForAdminIDao postForAdminIDao;

    public GetPostDetailService(PostForAdminIDao postForAdminIDao) {
        this.postForAdminIDao = postForAdminIDao;
    }

    public void execute(Model model) {
        Long postId = (Long) model.getAttribute("postId");
        PostForAdminDTO postForAdminDTO = postForAdminIDao.getPostByPostId(postId);
        if (postForAdminDTO == null) {
            throw new RuntimeException("글을 찾을 수 없음. ID " + postId);
        }
        model.addAttribute("postForAdminDTO", postForAdminDTO);
    }
}
