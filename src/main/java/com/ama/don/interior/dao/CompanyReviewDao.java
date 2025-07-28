package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import com.ama.don.interior.dto.request.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.response.CompanyHomeReviewDto;
import com.ama.don.interior.dto.response.CompanyReviewDto;
import com.ama.don.interior.dto.response.CompanyScoreAvgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 사용자가 업체에 쓰는 리뷰 dao
@Mapper
public interface CompanyReviewDao {

    // 공통 리뷰 작성, (공통리뷰 + 업체리뷰 트랜잭션)
    //int insertCommonReview(CompanyReviewCreateDto dto);

    // 사용자가 작성하는 업체 리뷰
    int insertCompanyReview(CompanyReviewCreateDto dto);

    // 업체 아이디로 company_score_avg 테이블이 존재하는지 확인
    Boolean isExistScoreTable(Long companyId);

    // 리뷰 점수 계산 테이블 생성
    // 서비스단에서 먼저 테이블이 있는지 체크해야함
    int insertScoreTable(CompanyReviewCreateDto dto);

    // 리뷰 평균 점수 계산 업데이트
    // 사용자가 리뷰를 작성시 무조건 마지막에 실행되어야 함
    int updateScoreAvg(CompanyReviewCreateDto dto);

    // 업체 모든 별점 평균
    CompanyScoreAvgDto getAvgScoreByCompanyId (Long companyId);

    // 리뷰 상세보기
    CompanyReviewDto getDetail(Long reviewId);

    /**
     * 홈에서 보는 업체에 대한 리뷰 목록 뷰(최신 순)
     */
    List<CompanyHomeReviewDto> getHomeCompanyReviews();

    /**
     * 업체 상세페이지에서 보는 리뷰 목록 뷰
     */
    List<CompanyReviewDto> listByCompanyId(Long companyId);

    // 해당 업체에 대한 리뷰 수
    int getReviewCountByCompanyId(Long companyId);

    // 리뷰 삭제
    void delete(Long reviewId);

    // 리뷰 수정
    void update(CompanyReviewUpdateDto dto);

    // 리뷰 수정 뷰
    CompanyReviewUpdateDto getEditView(Long reviewId);
}
