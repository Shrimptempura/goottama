package com.ama.don.interior.service;

import com.ama.don.common.dao.ReviewDao;
import com.ama.don.common.dto.ReviewDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.common.service.ReviewService;
import com.ama.don.interior.dao.CompanyReviewDao;
import com.ama.don.interior.dto.review.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyReviewServiceImpl implements CompanyReviewService {

    private final CompanyReviewDao companyReviewDao;
    private final ReviewService reviewService;
    private final CompanyAuthService companyAuthService;

    // 리뷰생성(회원만 작성가능)
    @Transactional
    @Override
    public Long createReview(CompanyReviewCreateDto createReviewDto) {
        Long userId = companyAuthService.getLoginUserId();
        Long companyId = createReviewDto.getCompanyId();    // hidden form jsp

        if (companyId == null) {
            log.warn("CRService - 리뷰 생성 중 companyId를 찾을수 없습니다. - userId: {}, dto: {}", userId, createReviewDto);
            throw new IllegalArgumentException("companyId가 없습니다.");
        }

        log.info("CRService - 리뷰 생성 시작 - userId: {}, companyId: {}", userId, companyId);

        try {
            ReviewDto poly = makePolyReview(createReviewDto);
            Long reviewId = reviewService.insertPolyReview(poly);
            if (reviewId == null) {
                log.error("CRService - 상위 리뷰 생성 실패 - userId: {}", userId);
                throw new IllegalStateException("상위 리뷰 생성 실패");
            }
            createReviewDto.setReviewId(reviewId);

            log.info("CRService - 하위 리뷰 생성 시작 - userId: {}, reviewId: {}", userId, reviewId);
            int inserted = companyReviewDao.insertCompanyReview(createReviewDto);

            if (inserted == 0) {
                log.error("CRService - 하위 리뷰 생성 실패 - userId: {}, reviewId: {}", userId, reviewId);
                throw new IllegalStateException("하위 리뷰 생성 실패");
            }

            log.info("CRService - 점수 테이블 생성 시작 - userId: {}, reviewId: {}", userId, reviewId);
            if (!companyReviewDao.isExistScoreTable(companyId)) {
                // 첫 리뷰인 경우
                int firstReview = companyReviewDao.createScoreTable(createReviewDto);
                if (firstReview == 0) {
                    log.error("CRService - 점수 테이블 생성 실패 - companyId: {}", companyId);
                    throw new IllegalStateException("점수 테이블 생성 실패");
                }
            } else {
                // 점수 테이블이 존재하면
                companyReviewDao.addScoreOnCreate(createReviewDto);
                companyReviewDao.averageOnCreate(createReviewDto);
            }

            log.info("CRService - 리뷰 작성 성공 - reviewId: {}, companyId: {}, userId: {}", reviewId, companyId, userId);
            return reviewId;
        } catch (DataAccessException e) {
            log.error("CRService - DB 오류, 리뷰 생성 실패 - userId: {}", userId, e);
            throw new IllegalStateException("업체 리뷰 생성 실패", e);
        }
    }

    // 리뷰 상세보기
    @Transactional(readOnly = true)
    @Override
    public CompanyReviewDto getReviewDetail(Long reviewId) {
        return companyReviewDao.getReviewDetail(reviewId);
    }

    // 홈에서 보는 리뷰 최신순 리스트
    @Transactional(readOnly = true)
    @Override
    public List<CompanyHomeReviewDto> findRecentForHome() {
        return companyReviewDao.findRecentForHome();
    }

    // 업체 상세페이지에서 보는 리뷰 리스트
    @Transactional(readOnly = true)
    @Override
    public List<CompanyReviewDto> listByCompanyId(Long companyId) {
        return companyReviewDao.listByCompanyId(companyId);
    }

    // 업체 리뷰 평점 조회
    @Transactional(readOnly = true)
    @Override
    public CompanyScoreAvgDto findScoreAvgByCompanyId(Long companyId) {
        return companyReviewDao.findScoreAvgByCompanyId(companyId);
    }


    @Transactional
    @Override
    public void updateReview(CompanyReviewUpdateDto updateReviewDto) {
        Long userId = companyAuthService.getLoginUserId();
        Long reviewId = updateReviewDto.getReviewId();

        log.info("CRService - 업체 리뷰 수정 시작 - userId: {}, reviewId: {}", userId, reviewId);

        try {
            if (!companyReviewDao.existByReviewIdAndUserId(reviewId, userId)) {
                log.error("CRService - 상위 리뷰가 존재 하지 않음 - userId: {}, reviewId: {}", userId, reviewId);
                throw new IllegalStateException("상위 리뷰가 없음");
            }

            // 기존 점수 저장
            CompanyReviewUpdateDto origin = companyReviewDao.getEditView(reviewId);
            if (origin == null) {
                log.error("CRService - 수정 전 기존 정보 조회 실패 - reviewId: {}", reviewId);
                throw new IllegalStateException("기존 정보 조회 실패");
            }
            Long companyId = origin.getCompanyId();


            // 상위 리뷰 수정
            ReviewDto dto = new ReviewDto();
            dto.setReviewId(reviewId);
            dto.setReviewContent(updateReviewDto.getReviewContent());
            int polyUpdated = companyReviewDao.updatePolyReview(dto);

            // 상위 수정 또는 하위 수정이 없을수도 있다 예외 x
            if (polyUpdated == 0) {
                log.error("CRService - 상위 리뷰 수정 없음 - reviewId: {}", reviewId);
            }

            // 하위 리뷰 수정
            int companyUpdated = companyReviewDao.updateCompanyReview(updateReviewDto);
            if (companyUpdated == 0) {
                log.error("CRService - 하위 리뷰 수정 없음 - reviewId: {}", reviewId);
            }

            // 점수 조정
            CompanyScoreAdjustDto adjust = recycleScoreOnEdit(companyId, updateReviewDto, origin);
            companyReviewDao.adjustScoreOnEdit(adjust);
            companyReviewDao.updateAverageScores(adjust);

            log.info("CRService - 리뷰 수정 성공 - reviewId: {}", reviewId);
        } catch (DataAccessException e) {
            log.error("CRService - DB 오류, 리뷰 수정 실패 - reviewId: {}", reviewId, e);
            throw new IllegalStateException("DB 오류, 리뷰 수정 실패", e);
        }
    }

    @Transactional
    @Override
    public void deleteReview(Long reviewId) {
        Long userId = companyAuthService.getLoginUserId();
        log.info("CRService - 리뷰 삭제 시작 - userId: {}, reviewId: {}", userId, reviewId);

        try {
            if (!companyReviewDao.existByReviewIdAndUserId(reviewId, userId)) {
                log.error("CRService - 삭제할 상위 리뷰가 없습니다. - userId: {}, reviewId: {}", userId, reviewId);
                throw new IllegalStateException("삭제할 상위 리뷰가 없습니다.");
            }

            // 기존 정보 저장
            CompanyReviewUpdateDto origin = companyReviewDao.getEditView(reviewId);
            if (origin == null) {
                log.error("CRService - 삭제 전 기존 정보 조회 실패 - reviewId: {}", reviewId);
                throw new IllegalStateException("리뷰 원본 조회 실패");
            }
            Long companyId = origin.getCompanyId();
            
            // 하위 -> 상위 삭제(소프트)
            companyReviewDao.softDeleteCompanyReview(reviewId);
            companyReviewDao.softDeletePolyReview(reviewId, userId);

            // 현재 companyId에 대한 리뷰 숫자 구하기
            int leftCount = companyReviewDao.countByCompanyId(companyId);
            if (leftCount == 0) {
                companyReviewDao.resetScoresIfOne(companyId);
            } else {
                CompanyScoreAdjustDto adjust = recycleScoreOnDelete(companyId, origin);
                companyReviewDao.adjustSumOnDelete(adjust);
                companyReviewDao.updateAverageScores(adjust);
            }

            log.info("CRService - 삭제 성공 - reviewId: {}, userId: {}", reviewId, userId);

        } catch (DataAccessException e) {
            log.error("CRService - DB 오류, 리뷰 삭제 실패 - reviewId: {}", reviewId, e);
            throw new IllegalStateException("DB 오류, 리뷰 삭제 실패", e);
        }
    }

    // 다형성 리뷰 작성(content)
    private ReviewDto makePolyReview(CompanyReviewCreateDto createReviewDto) {

        Long userId = companyAuthService.getLoginUserId();
        Long companyId = createReviewDto.getCompanyId();

        ReviewDto poly = new ReviewDto();
        poly.setUserId(userId);
        poly.setTargetType(TargetType.INTERIOR);
        poly.setTargetId(companyId);
        poly.setReviewContent(createReviewDto.getReviewContent());

        return poly;
    }

    // 리뷰 수정시 점수 조정
    private CompanyScoreAdjustDto recycleScoreOnEdit(Long companyId, CompanyReviewUpdateDto newRate,
                                                     CompanyReviewUpdateDto origin) {

        CompanyScoreAdjustDto adjust = new CompanyScoreAdjustDto();
        adjust.setCompanyId(companyId);
        adjust.setOldCommunicationRate(origin.getCommunicationRate());
        adjust.setOldPriceRate(origin.getPriceRate());
        adjust.setOldResultRate(origin.getResultRate());
        adjust.setOldScheduleRate(origin.getScheduleRate());

        adjust.setNewCommunicationRate(newRate.getCommunicationRate());
        adjust.setNewPriceRate(newRate.getPriceRate());
        adjust.setNewResultRate(newRate.getResultRate());
        adjust.setNewScheduleRate(newRate.getScheduleRate());

        return adjust;
    }

    private CompanyScoreAdjustDto recycleScoreOnDelete(Long companyId, CompanyReviewUpdateDto origin) {

        CompanyScoreAdjustDto adjust = new CompanyScoreAdjustDto();
        adjust.setCompanyId(companyId);
        adjust.setOldCommunicationRate(origin.getCommunicationRate());
        adjust.setOldPriceRate(origin.getPriceRate());
        adjust.setOldResultRate(origin.getResultRate());
        adjust.setOldScheduleRate(origin.getScheduleRate());

        return adjust;
    }
}
