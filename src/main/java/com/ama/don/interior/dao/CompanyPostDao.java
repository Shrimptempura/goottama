package com.ama.don.interior.dao;

import com.ama.don.common.enums.CompanySortType;
import com.ama.don.interior.dto.request.CompanyPostCreateDto;
import com.ama.don.interior.dto.request.CompanyPostUpdateDto;
import com.ama.don.interior.dto.response.CompanyHomePostDto;
import com.ama.don.interior.dto.response.CompanyPostDetailDto;
import com.ama.don.interior.dto.response.CompanyPostPreviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 업체가 작성한 게시글
@Mapper
public interface CompanyPostDao {

    /**
     * 홈에서 보는 업체 게시글 목록 뷰, enum(LASTEST, RANDOM, POPULAR)
     */
    List<CompanyHomePostDto> findAllCompanyPosts(CompanySortType sortType);

    /**
     * 업체 상세페이지에서 보는 업체게시글 목록 뷰
     */
    List<CompanyPostPreviewDto> caseList(Long companyId);

    // 게시글 생성 순서
    // 1. PostDao의 insertPolyPostForCompany 다형성 생성
    // 2. PostDao의 findById로 전체 조회
    // 3. file 관련 다형성 dto 사용하여 생성
    // 4. 업체 게시글 생성
    int insertCompanyPost(CompanyPostCreateDto dto);

    // 게시글 상세보기
    // 연관된 테이블과 기능
    // post, company_post, company, company_detail, file | count(view, like, scrap)
    // (post + company_post), (company + company_detail) 쿼리 조인처리
    // file은 맨 마지막, 나머지는 기타 쿼리 및 테이블 확인
    // getPostAndCompanyPostById, getCompanyBasicInfoById
    CompanyPostDetailDto getPostAndCompanyPostById(Long companyPostId);

    // 상세보기 부분 조회 (company + company_detail)
    CompanyPostDetailDto getCompanyBasicInfoById(Long companyId);

    // 게시글 수정
    int updatePost(CompanyPostUpdateDto dto);

    // 게시글 수정 뷰
    CompanyPostUpdateDto getEditView(Long companyPostId);

    // 게시글 삭제
    // 다형성 게시글 먼저 삭제
    int deletePolyPostById(Long postId);

    // 업체 게시글 삭제
    int deleteCompanyPostById(Long companyPostId);

    // 조회수 증가
    void increaseHit(Long companyPostId);

    // 좋아요 수
    int getLikeCount(Long companyPostId);
}
