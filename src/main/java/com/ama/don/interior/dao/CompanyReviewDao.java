package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import com.ama.don.interior.dto.request.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.response.CompanyHomeReviewDto;
import com.ama.don.interior.dto.response.CompanyReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

// 사용자가 업체에 쓰는 리뷰 dao
@Mapper
public interface CompanyReviewDao {

    // 공통 리뷰 작성, (공통리뷰 + 업체리뷰 트랜잭션)
    //int insertCommonReview(CompanyReviewCreateDto dto);

    // 사용자가 작성하는 업체 리뷰
    int insertCompanyReview(CompanyReviewCreateDto dto);

    /**
     * 홈에서 보는 업체에 대한 리뷰 목록 뷰(최신 순)
     */
    List<CompanyHomeReviewDto> findAllReviewsForHome();

    /**
     * 업체 상세페이지에서 보는 리뷰 목록 뷰
     */
    List<CompanyReviewDto> listByCompanyId(Long companyId);

    // 리뷰 상세보기
    CompanyReviewDto detail(Long reviewId);

    // 리뷰 점수 계산 테이블 생성
    int insertScoreTable(CompanyReviewCreateDto dto);

    // 리뷰 평균 점수 계산 업데이트
    int updateScoreAvg(CompanyReviewCreateDto dto);

    // 업체 별점 평균, 단순 값 추출 추후 정렬에 이용 가능성
    Optional<Double> getAvgScore(Long companyId);

    // 리뷰 삭제
    void delete(Long reviewId);

    // 리뷰 수정
    void update(CompanyReviewUpdateDto dto);

    // 리뷰 수정 뷰
    CompanyReviewUpdateDto getEditView(Long reviewId);
}
