package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyPostCreateDto;
import com.ama.don.interior.dto.request.CompanyPostUpdateDto;
import com.ama.don.interior.dto.response.CompanyHomePostDto;
import com.ama.don.interior.dto.response.CompanyPostDetailDto;
import com.ama.don.interior.dto.response.CompanyPostPreviewDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 업체가 작성한 게시글
@Mapper
public interface CompanyPostDao {


    // 홈에서 보는 업체 게시글 목록 뷰, enum(LASTEST, RANDOM, POPULAR)
    // 쿼리를 3가지 조건으로 짜야함: 최신순, 랜덤, 인기순(좋아요 순)
    // 3가지 방법으로 짜면 enum은 안씀(이건 choose 방법)
    // 위치정보를 상위가 아닌 정렬로 이동( + 내 지역구)
    // 홈에서 최신순
    List<CompanyHomePostDto> findCompanyPostByLatest();

    // 홈에서 랜덤
    List<CompanyHomePostDto> findCompanyPostByRandom();

    // 홈에서 인기순
    List<CompanyHomePostDto> findCompanyPostByPopular();

    // 홈에서 지역구만
    List<CompanyHomePostDto> findCompanyPostByRegion(@Param("region") String region);

    // 업체 상세페이지에서 보는 업체게시글 목록 뷰
    // 여기서는 그냥 보여줌
    List<CompanyPostPreviewDto> getCompanyPostPreview(@Param("companyId") Long companyId);

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
    CompanyPostDetailDto getPostAndCompanyPostById(@Param("companyPostId") Long companyPostId);

    // 상세보기 부분 조회 (company + company_detail)
    CompanyPostDetailDto getCompanyBasicInfoById(@Param("companyId") Long companyId);

    // 게시글 수정
    int updatePost(CompanyPostUpdateDto dto);

    // 게시글 수정 뷰
    CompanyPostUpdateDto getEditView(@Param("companyPostId") Long companyPostId);

    // 게시글 삭제
    // 댓글, 좋아요, 스크립 존재 시 먼저 지워야함(하위-> 상위)
    // 다형성 게시글 먼저 삭제
    int deletePolyPostById(@Param("postId") Long postId);

    // 업체 게시글 삭제
    int deleteCompanyPostById(@Param("companyPostId") Long companyPostId);

    // 조회수 증가
    int increaseHit(@Param("companyPostId") Long companyPostId);

    // 좋아요 수 조회
    int countLikeCompanyPost(@Param("companyPostId") Long companyPostId);

    // 게시글 좋아요 클릭시 좋아요수 증가
    int incrementLikeCount(@Param("companyPostId") Long companyPostId);
    
    // 게시글 좋아요 취소시 좋아요수 감소
    int decrementLikeCount(@Param("companyPostId") Long companyPostId);

    // 스크랩, 댓글은 다른 dao에서 책임 나누자
}
