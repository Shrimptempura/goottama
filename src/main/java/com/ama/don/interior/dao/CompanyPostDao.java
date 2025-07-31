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
    CompanyPostDetailDto detail(Long companyPostId);

    // 게시글 수정
    void update(CompanyPostUpdateDto dto);

    // 게시글 수정 뷰
    CompanyPostUpdateDto getEditView(Long companyPostId);

    // 게시글 삭제
    void delete(Long companyPostId);

    // 조회수 증가
    void increaseHit(Long companyPostId);

    // 좋아요 수
    int getLikeCount(Long companyPostId);
}
