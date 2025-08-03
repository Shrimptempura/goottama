package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCommentCreateDto;
import com.ama.don.interior.dto.request.CompanyCommentUpdateDto;
import com.ama.don.interior.dto.response.CompanyCommentDto;
import com.ama.don.interior.dto.response.CompanyCommentTreeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyCommentDao {

    // 댓글 작성
    int insertCompanyComment(CompanyCommentCreateDto dto);

    // 댓글 단건 조회
    CompanyCommentDto findById(@Param("commentId") Long commentId);
    
    // 게시글 내 댓글 전체 조회
    // 일단 모든값을 가져오고 정렬이나 깊이는 프론트에서 처리
    List<CompanyCommentTreeDto> findCommentsByPostId(@Param("companyPostId") Long companyPostId);

    // 댓글 수정
    int updateCompanyComment(CompanyCommentUpdateDto dto);

    // 댓글 삭제(소프트), "삭제된 댓글"으로 칸, 흐름 유지 및 신고 처리시 필요
    int softDeleteCompanyComment(@Param("commentId") Long commentId,
                                 @Param("userId") Long userId);

    // 대댓글
    // insertCompanyComment 사용
    // 대댓글은 inserCopanyComment.setParentCommentId(댓글id)로 처리
    // 테스트 코드 완료, 쿼리는 동일
    
    // 대댓글 조회
    // 댓글 조회에서 정렬 생각
    // findCommentsByPostId로 테스트 코드 완료, 쿼리는 동일
    
    // 댓글 수 조회(이건 company_post로 생각중)
    int countComentsByCompanyPostId(@Param("companyPostId") Long companyPostId);

    // 게시글 삭제시 게시글 내 댓글 삭제
    // 다형성 소프트 삭제 생각
    int softDeleteByCompanyPostId(@Param("companyPostId") Long companyPostId);

    // 회원관리자가 원하면 내가 작성한 댓글 모음(일단은 구현x)
}
