package com.ama.don.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.community.dao.CommunityCommentDao;
import com.ama.don.community.dto.Comment.CommentCreateDto;

public interface CommentService {
    void createComment(CommentCreateDto dto);
    List<CommentCreateDto> getCommentsByTarget(Long targetId, TargetType targetType);
    
    @Service
    public class CommunityCommentServiceImpl implements CommentService {

        @Autowired
        private CommunityCommentDao commentDao;

        @Override
        public void createComment(CommentCreateDto dto) {
            commentDao.insert(dto);
        }

        @Override
        public List<CommentCreateDto> getCommentsByTarget(Long targetId, TargetType targetType) {
            return commentDao.findByTargetId(targetId, targetType);
        }
    }

}
