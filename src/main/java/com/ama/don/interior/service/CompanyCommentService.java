package com.ama.don.interior.service;

import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;

import java.util.List;

public interface CompanyCommentService {

    // 댓글 작성 (대댓글은 parentCommentId 사용)
    Long addComment(Long companyPostId, Long parentCommentId, String commentContent);

    // 댓글 단건 조회
    CompanyCommentDto getCommentDetail(Long commentId);

    // 게시글의 댓글 전체 조회
    List<CompanyCommentTreeDto> listComments(Long companyPostId);

    // 댓글 수정
    void updateMyComment(Long commentId, String commentContent);

    // 댓글 삭제(소프트)
    void deleteMyComment(Long commentId);

    // 게시글 삭제시 댓글 전체 삭제(소프트)
    void deleteAllByPost(Long companyPostId);
}
