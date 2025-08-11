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
    private final ReviewDao reviewDao;

    // 리뷰생성(회원만 작성가능)
    @Transactional
    @Override
    public Long createReview(CompanyReviewCreateDto createReviewDto) {
        Long userId = companyAuthService.getLoginUserId();
        log.info("CRService - 리뷰 생성 시작 - userId: {}", userId);

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
            Long companyId = createReviewDto.getCompanyId();

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
            log.error("SRService - DB 오류, 리뷰 생성 실패 - userId: {}", userId, e);
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
        Long companyId = companyAuthService.requireMyCompanyId();
        Long reviewId = updateReviewDto.getReviewId();

        log.info("CRService - 업체 리뷰 수정 시작 - userId: {}, companyId: {}, reviewId: {}", userId, companyId, reviewId);

        try {
            if (!companyReviewDao.existByReviewIdAndUserId(reviewId, userId)) {
                log.error("CRService - 상위 리뷰가 존재 하지 않음 - userId: {}, companyId: {}, reviewId: {}", userId, companyId, reviewId);
                throw new IllegalStateException("상위 리뷰가 없음");
            }

            // 상위 리뷰 수정
            ReviewDto dto = new ReviewDto();
            dto.setReviewContent(updateReviewDto.getReviewContent());
            int polyUpdated = companyReviewDao.updatePolyReview(dto);

            if (polyUpdated == 0) {
                log.error("CRService - 상위 리뷰 수정 없음 - reviewId: {}", reviewId);
                throw new IllegalStateException("상위 리뷰 수정 사항 업음");
            }

            // 하위 리뷰 수정
            int companyUpdated = companyReviewDao.updateCompanyReview(updateReviewDto);
            if (companyUpdated == 0) {
                log.error("CRService - 하위 리뷰 수정 없음 - reviewId: {}", reviewId);
                throw new IllegalStateException("하위 리뷰 수정 사항 없음");
            }

            // 기존 점수
            CompanyReviewUpdateDto origin = companyReviewDao.getEditView(reviewId);
            if (origin == null) {
                log.error("CRService - 기존 정보 조회 실패 - reviewId: {}", reviewId);
                throw new IllegalStateException("기존 정보 조회 실패");
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

    @Override
    public void deleteReview(Long reviewId) {

    }

    // 다형성 리뷰 작성(content)
    private ReviewDto makePolyReview(CompanyReviewCreateDto createReviewDto) {
        Long userId = companyAuthService.getLoginUserId();
        Long companyId = companyAuthService.requireMyCompanyId();

        ReviewDto poly = new ReviewDto();
        poly.setUserId(userId);
        poly.setTargetType(TargetType.INTERIOR);
        poly.setTargetId(companyId);
        poly.setReviewContent(poly.getReviewContent());

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
}
