package com.ama.don.interior.dao;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import com.ama.don.interior.dto.request.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.request.CompanyScoreAdjustDto;
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
    int insert(CompanyReviewCreateDto dto);

    // 업체 아이디로 company_score_avg 테이블이 존재하는지 확인
    Boolean isExistScoreTable(Long companyId);

    // 리뷰 점수 계산 테이블 생성
    // 서비스단에서 먼저 테이블이 있는지 체크해야함
    int createScoreTable(CompanyReviewCreateDto dto);

    // 리뷰 평균 점수 계산 업데이트
    // 사용자가 리뷰를 작성시 무조건 마지막에 실행되어야 함
    int applyScoreOnCreate(CompanyReviewCreateDto dto);

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
    int softDeleteCompanyReview(Long reviewId);

    // 상위 리뷰 소프트 삭제
    int softDeletePolyReview(Long reviewId, Long userId);

    // 리뷰 수정(review + file + company_review) 3개 트랜잭션
    int updatePolyReview(ReviewDto dto);
    
    // 업체 리뷰 수정
    int updateCompanyReview(CompanyReviewUpdateDto dto);
    
    // 파일 수정은 공통 fileDao에서 처리

    // 리뷰 수정후 점수를 다시 계산
    int adjustEditScoreAvg(CompanyScoreAdjustDto dto);

    // 리뷰를 삭제하기전 0으로 나누는 오류를 방지하기 위해
    // int countByCompanyId(Long companyId); 이 메서드로 개수가 만약 1이면
    // resetScoresIfOne 메서드 실행 아니면 adjustDeleteScoreAvg 실행
    int resetScoresIfOne(Long companyId);

    // 리뷰 삭제후 점수를 다시 계산
    int adjustDeleteScoreAvg(CompanyScoreAdjustDto dto);

    // 리뷰 수정 뷰
    CompanyReviewUpdateDto getEditView(Long reviewId);
}
