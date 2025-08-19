package com.ama.don.admin.service.commentsForAdminService;

import com.ama.don.admin.dao.SearchCommentIDao;
import com.ama.don.admin.dto.commentsForAdminDTO.CommentsForAdminDTO;
import com.ama.don.admin.dto.commentsForAdminDTO.CommentsSearchForAdminDTO;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.common.enums.TargetType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class GetCommentsListService {

    private final SearchCommentIDao searchCommentIDao;

    public GetCommentsListService(SearchCommentIDao searchCommentIDao) {
        this.searchCommentIDao = searchCommentIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        CommentsSearchForAdminDTO commentsSearchForAdminDTO = (CommentsSearchForAdminDTO) map.get("commentsSearchForAdminDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<CommentsForAdminDTO> commentsDtos;
        int total;

        // 검색 조건이 없거나 비어있는 경우
        if (commentsSearchForAdminDTO == null ||
                commentsSearchForAdminDTO.getCommentId() == null &&
                        commentsSearchForAdminDTO.getUserId() == null &&
                        (commentsSearchForAdminDTO.getCommentContent() == null || commentsSearchForAdminDTO.getCommentContent().isEmpty()) &&
                        commentsSearchForAdminDTO.getTargetId() == null &&
                        (commentsSearchForAdminDTO.getTargetType() == null || commentsSearchForAdminDTO.getTargetType().isEmpty()) &&
                        commentsSearchForAdminDTO.getIsDeleted() == null &&
                        (commentsSearchForAdminDTO.getCreatedAtStart() == null) &&
                        (commentsSearchForAdminDTO.getCreatedAtEnd() == null) &&
                        (commentsSearchForAdminDTO.getModifiedAtStart() == null) &&
                        (commentsSearchForAdminDTO.getModifiedAtEnd() == null)) {
            total = searchCommentIDao.countAllComment();
            commentsDtos = searchCommentIDao.getAllComment(searchVO);
        } else {
            // 검색 조건이 있는 경우
            total = searchCommentIDao.countSearchComment(commentsSearchForAdminDTO);
            commentsDtos = searchCommentIDao.searchComments(searchVO, commentsSearchForAdminDTO);
        }

        searchVO.pageCalculate(total);

        for (CommentsForAdminDTO dto : commentsDtos) {
            Map<String, Object> row = new HashMap<>();
            row.put("commentId", dto.getCommentId());
            row.put("userId", dto.getUserId());
            row.put("commentContent", dto.getCommentContent());
            row.put("createdAt", dto.getCreatedAt());
            row.put("modifiedAt", dto.getModifiedAt());
            row.put("isDeleted", dto.getIsDeleted());
            row.put("targetId", dto.getTargetId());
            row.put("targetType", dto.getTargetType());
            mapList.add(row);
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("mapList", mapList);
    }
}