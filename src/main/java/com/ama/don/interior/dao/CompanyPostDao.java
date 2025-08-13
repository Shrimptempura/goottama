package com.ama.don.interior.dao;

import com.ama.don.interior.dto.post.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 업체가 작성한 게시글
@Mapper
public interface CompanyPostDao {

    // === create =============================================
    /**
     * [게시글 작성]
     * 1. 다형성 게시글 작성 (post)         -> insertPolyPostForCompany()
     * 2. 다형성 게시글 조회 (post)         -> findById()
     * 3. 업체 게시글 생성 (company_post)   -> insertCompanyPost()
     */
    int insertCompanyPost(CompanyPostCreateDto dto);


    // === read =============================================
    // 게시글 단건 조회
    CompanyPostDto findById(@Param("companyPostId") Long companyPostId);

    // 홈에서 보는 업체 게시글 리스트 (정렬: 최신순, 랜덤, 인기순(좋아요), 지역순)
    List<CompanyHomePostDto> findCompanyPostByLatest();     // 최신
    List<CompanyHomePostDto> findCompanyPostByRandom();     // 랜덤
    List<CompanyHomePostDto> findCompanyPostByPopular();    // 인기
    List<CompanyHomePostDto> findCompanyPostByRegion(@Param("region") String region);   // 지역(필터링)

    // 업체 상세페이지에서 보는 업체게시글 리스트
    List<CompanyPostPreviewDto> getCompanyPostPreview(@Param("companyId") Long companyId);

    // 게시글 상세보기
    // 연관된 테이블과 기능
    // post, company_post, company, company_detail, file | count(view, like, scrap)
    // (post + company_post), (company + company_detail) 쿼리 조인처리
    // getPostAndCompanyPostById, getCompanyBasicInfoById
    CompanyPostDetailDto getPostAndCompanyPostById(@Param("companyPostId") Long companyPostId);
    CompanyPostDetailDto getCompanyBasicInfoById(@Param("companyId") Long companyId);

    // 조회수 증가
    int increaseHit(@Param("companyPostId") Long companyPostId);


    // === update =============================================
    // 게시글 수정 뷰
    CompanyPostUpdateDto getEditView(@Param("companyPostId") Long companyPostId);

    // 게시글 수정
    int updatePost(CompanyPostUpdateDto dto);


    // === delete =============================================
    // 게시글 삭제
    // 댓글, 좋아요, 스크립 존재 시 먼저 지워야함(하위-> 상위)
    // 업체 게시글 삭제 (하위)
    int deleteCompanyPostById(@Param("companyPostId") Long companyPostId);

    // 다형성 게시글 삭제 (상위)
    int deletePolyPostById(@Param("postId") Long postId);
}
