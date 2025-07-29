package com.ama.don.interior.dao;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import com.ama.don.interior.dto.request.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.response.CompanyHomeReviewDto;
import com.ama.don.interior.dto.response.CompanyReviewDto;
import com.ama.don.interior.dto.response.CompanyScoreAvgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

// 사용자가 업체에 쓰는 리뷰 dao
@Mapper
public interface CompanyReviewDao {

    // 공통 리뷰 작성, (공통리뷰 + 업체리뷰 트랜잭션)
    //int insertCommonReview(CompanyReviewCreateDto dto);

    // 사용자가 작성하는 업체 리뷰
    int insert(CompanyReviewCreateDto dto);

    // 업체 아이디로 company_score_avg 테이블이 존재하는지 확인
    Boolean isExistScoreTable(Long companyId);

    // 리뷰 점수 계산 테이블 생성
    // 서비스단에서 먼저 테이블이 있는지 체크해야함
    int createScoreTable(CompanyReviewCreateDto dto);

    // 리뷰 평균 점수 계산 업데이트
    // 사용자가 리뷰를 작성시 무조건 마지막에 실행되어야 함
    int updateScoreAvg(CompanyReviewCreateDto dto);

    // 업체 모든 별점 평균
    CompanyScoreAvgDto getAvgScoreByCompanyId(Long companyId);

    // 리뷰 상세보기
    CompanyReviewDto getReviewDetail(Long reviewId);

    /**
     * 홈에서 보는 업체에 대한 리뷰 목록 뷰(최신 순)
     */
    List<CompanyHomeReviewDto> findRecentForHome();

    /**
     * 업체 상세페이지에서 보는 리뷰 목록 뷰
     */
    List<CompanyReviewDto> listByCompanyId(Long companyId);

    // 해당 업체에 대한 리뷰 수
    int countByCompanyId(Long companyId);

    // 상위 다형성 리뷰에서 리뷰아이디 찾기(1 + 2 + 3 세트)
    // 검증용 값 1개면 충분
    // 뷰단에서(listByCompanyId) review_id를 뿌려줌
    Boolean existByReviewIdAndUserId(Long reviewId, Long userId);

    // 하위 리뷰 소프트 삭제(트랜잭션 하위 -> 상위 순)
    int updateCompanyReview(Long reviewId);

    // 상위 리뷰 소프트 삭제
    int updateCommonReview(Long reviewId, Long userId);

    // 리뷰 수정
    int updateReview(CompanyReviewUpdateDto dto);

    // 리뷰 수정 뷰
    CompanyReviewUpdateDto getEditView(Long reviewId);

    // 테스트용 where is_deleted 제외
    CompanyReviewDto getReviewDetailTest(Long reviewId);

}
