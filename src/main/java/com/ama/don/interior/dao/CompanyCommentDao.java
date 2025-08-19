package com.ama.don.interior.dao;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.comment.CompanyCommentCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;
import com.ama.don.interior.dto.comment.CompanyCommentUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyCommentDao {

    // === create =========================================================
    // 댓글 작성
    int insertCompanyComment(CompanyCommentCreateDto dto);

    // 대댓글 -> insertCompanyComment 사용
    // 대댓글은 inserCopanyComment.setParentCommentId(댓글id)로 처리
    // 테스트 코드 완료, insertCompanyComment와 쿼리는 동일

    // === read =========================================================
    // 댓글 단건 조회
    CompanyCommentDto findById(@Param("commentId") Long commentId);

    // 게시글 내 댓글 전체 조회
    // 일단 모든값을 가져오고 정렬이나 깊이는 프론트에서 처리
    List<CompanyCommentTreeDto> findCommentsByPostId(@Param("companyPostId") Long companyPostId,
                                                     @Param("targetType") TargetType targetType);

    // 대댓글 조회
    // 댓글 조회에서 정렬 생각
    // findCommentsByPostId로 테스트 코드 완료, 쿼리는 동일

    // 댓글 수 조회
    int countCommentsByTarget(@Param("targetId") Long targetId,
                              @Param("targetType") TargetType targetType);

    // === update =========================================================
    // 댓글 수정
    int updateCompanyComment(CompanyCommentUpdateDto dto);


    // === delete =========================================================
    // 댓글 삭제(소프트)
    int softDeleteCompanyComment(@Param("commentId") Long commentId,
                                 @Param("userId") Long userId);

    // 게시글이 삭제될 때 해당 게시글 댓글 전부 소프트 삭제
    int softDeleteCommentsByPostId(@Param("companyPostId") Long companyPostId,
                                   @Param("targetType") TargetType targetType);

    // 회원관리자가 원하면 내가 작성한 댓글 모음(일단은 구현x)
}
