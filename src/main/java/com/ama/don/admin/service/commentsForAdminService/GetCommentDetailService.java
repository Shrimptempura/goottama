package com.ama.don.admin.service.commentsForAdminService;

import com.ama.don.admin.dao.SearchCommentIDao;
import com.ama.don.admin.dto.commentsForAdminDTO.CommentsForAdminDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class GetCommentDetailService {

    private final SearchCommentIDao searchCommentIDao;

    public GetCommentDetailService(SearchCommentIDao searchCommentIDao) {
        this.searchCommentIDao = searchCommentIDao;
    }

    public void execute(Model model) {
        Long commentId = (Long) model.getAttribute("commentId");
        CommentsForAdminDTO commentsDto = searchCommentIDao.getCommentByCommentId(commentId);
        if (commentsDto == null) {
            throw new RuntimeException("댓글을 찾을 수 없음. ID " + commentId);
        }
        model.addAttribute("commentsDto", commentsDto);
    }
}
