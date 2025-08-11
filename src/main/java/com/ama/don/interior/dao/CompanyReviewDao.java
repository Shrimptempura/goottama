package com.ama.don.interior.dao;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.interior.dto.review.CompanyReviewCreateDto;
import com.ama.don.interior.dto.review.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.review.CompanyScoreAdjustDto;
import com.ama.don.interior.dto.review.CompanyHomeReviewDto;
import com.ama.don.interior.dto.review.CompanyReviewDto;
import com.ama.don.interior.dto.review.CompanyScoreAvgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 사용자가 업체에 쓰는 리뷰 dao
@Mapper
public interface CompanyReviewDao {

    // === create ===================================================
    /**
     * [리뷰 작성]
     * 1. 다형성 리뷰 작성 (review)           -> insertPolyReview()
     * 2. 업체 리뷰 작성 (company_review)     -> insert()
     */
    // 다형성 리뷰 작성 (review)
    // insertPolyReview

    // 업체 리뷰 생성 (하위 리뷰 company_review)
    int insertCompanyReview(CompanyReviewCreateDto dto);

    // === read ===================================================
    // 업체 모든 별점 평균
    CompanyScoreAvgDto findScoreAvgByCompanyId(Long companyId);

    // 리뷰 상세보기
    CompanyReviewDto getReviewDetail(Long reviewId);

    // 홈에서 보는 업체에 대한 리뷰 목록 뷰(최신 순)
    List<CompanyHomeReviewDto> findRecentForHome();

    // 업체 상세페이지에서 보는 리뷰 목록 뷰
    List<CompanyReviewDto> listByCompanyId(Long companyId);

    // 해당 업체에 대한 리뷰 수
    int countByCompanyId(Long companyId);

    // 상위 다형성 리뷰에서 리뷰아이디 찾기
    // 리뷰 삭제나 수정 전에 실제 상위 리뷰가 존재하는지 확인 해야 함
    boolean existByReviewIdAndUserId(Long reviewId, Long userId);

    // === update ===================================================
    /**
     * [리뷰 수정]
     * 1. 리뷰 수정 전, 상위 리뷰가 존재하는지 확인 (review)     -> existByReviewIdAndUserId
     * 2. 상위 리뷰 수정 (review)     -> updatePolyReview
     * 3. 하위 업체 리뷰 수정 (company_review)      -> updateCompanyReview
     * 4. 리뷰가 수정이 되면 점수도 다시 재계산 해야함
     *      - adjustScoreOnEdit, updateAverageScores
     */

    // 리뷰 수정 뷰
    CompanyReviewUpdateDto getEditView(Long reviewId);

    // 상위 리뷰 수정(상위 -> 하위)
    int updatePolyReview(ReviewDto dto);

    // 하위 업체 리뷰 수정
    int updateCompanyReview(CompanyReviewUpdateDto dto);

    // 리뷰 수정후 점수를 계산할때 사용하는 합연산
    int adjustScoreOnEdit(CompanyScoreAdjustDto dto);

    // 리뷰 수정후 평균 점수 계산
    int updateAverageScores(CompanyScoreAdjustDto dto);

    // === delete ===================================================
    /**
     * [리뷰 삭제]
     * 1. 리뷰 삭제 전, 상위 리뷰가 존재하는지 확인 (review)     -> existByReviewIdAndUserId
     * 2. 하위 리뷰 삭제 (company_review)         -> softDeleteCompanyReview()
     * 3. 상위 리뷰 삭제 (review)         -> softDeletePolyReview()
     * 4. 리뷰가 삭제가 되면 점수도 다시 재계산 해야함
     *      - countByCompanyId() 으로 현재 리뷰 수 구하기
     *      - 현재 리뷰가 1개 입니까?
     *      5-1. 리뷰가 1개이면 0으로 나누는 오류 방지
     *          - resetScoresIfOne (company_score_avg)
     *          - 모든 점수 0으로 초기화
     *      5-2. 리뷰가 2개 이상이면
     *          - adjustSumOnDelete(), updateAverageScores()
     */
    // 하위 리뷰 소프트 삭제(트랜잭션 하위 -> 상위 순)
    int softDeleteCompanyReview(Long reviewId);

    // 상위 리뷰 소프트 삭제
    int softDeletePolyReview(Long reviewId, Long userId);
    
    // 리뷰 수가 1개이면 점수를 0으로 초기화
    int resetScoresIfOne(Long companyId);       // 0으로 초기화

    // 리뷰 삭제후 점수계산을 위한 합산
    int adjustSumOnDelete(CompanyScoreAdjustDto dto);

    // adjustSumOnDelete() 실행 후 updateAverageScores()으로 평균값 재배정

    
    // === score ===================================================
    /**
     * [점수 설정]
     * 1. companyId로 작성한 company_score_avg가 있는가?, 실제 첫 리뷰인가
     *      - company_score_avg 테이블 확인 (isExistScoreTable)
     *      - 처음이다: createScoreTable (company_score_avg)
     *      - 처음이 아니다: addScoreOnCreate, addScoreOnCreate (company_score_avg)
     */

    // companyId로 company_score_avg 테이블 존재 유무 확인
    // 존재하면 -> addScoreOnCreate, averageOnCreate, 존재하지 않으면 -> createScoreTable
    boolean isExistScoreTable(Long companyId);

    // 리뷰 점수 계산 테이블 생성
    // 업체의 점수 테이블 최초 생성(1회) (isExistScoreTable 확인)
    int createScoreTable(CompanyReviewCreateDto dto);

    // company_score_avg 테이블은 합산 계산후 평균을 구한다.
    // 사용자가 리뷰 작성시 마지막에 실행
    int addScoreOnCreate(CompanyReviewCreateDto dto);

    // addScoreOnCreate의 합산으로 평균 구하기
    int averageOnCreate(CompanyReviewCreateDto dto);






}
