package com.ama.don.common.service;

import com.ama.don.common.dao.ReviewDao;
import com.ama.don.common.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    // 다형성 리뷰 생성
    @Transactional
    @Override
    public Long insertPolyReview(ReviewDto dto) {
        if (dto == null) {
            log.error("ReviewService : 공통 review dto 누락 - dto: {}", dto);
            throw new IllegalArgumentException("리뷰 dto가 없습니다.");
        }
        log.info("ReviewService - 상위 리뷰 생성 시작 - dto: {}", dto);

        try {
            int started = reviewDao.insertPolyReview(dto);
            if (started == 0 || dto.getReviewId() == null) {
                log.error("ReviewService - 상위 리뷰 생성 실패 - dto: {}", dto);
                throw new IllegalStateException("상위 리뷰 생성 실패");
            }
            log.info("ReviewService - 상위 리뷰 생성 성공 - dto: {}", dto);
            return dto.getReviewId();
        } catch (DataAccessException e) {
            log.error("ReviewService - DB 오류, 상위 리뷰 저장 실패 - dto: {}", dto, e);
            throw new IllegalStateException("DB 오류 상위 리뷰 저장 실패", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewDto selectDefaultFieldById(Long reviewId) {
        if (reviewId == null) {
            log.warn("ReviewService - 상위 리뷰 ID가 없습니다. - reviewId: {}", reviewId);
            throw new IllegalArgumentException("상위 리뷰 ID가 없습니다.");
        }
        log.info("ReviewService - 상위 리뷰 default 값만 조회 - reviewId: {}", reviewId);

        try {
            ReviewDto selected = reviewDao.selectDefaultFieldById(reviewId);
            if (selected == null) {
                log.error("ReviewService - 상위 리뷰 default 조회 실패(없음) - reviewId: {}", reviewId);
                throw new IllegalStateException("default 값 조회 실패");
            }
            return selected;
        } catch (DataAccessException e) {
            log.error("ReviewService - DB 오류, default 값 조회 실패 - reviewId: {}", reviewId, e);
            throw new IllegalStateException("DB 오류, default 값 조회 실패", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ReviewDto selectAllFieldById(Long reviewId) {
        if (reviewId == null) {
            log.warn("ReviewService - 상위 리뷰 아이디가 없습니다. - reviewId: {}", reviewId);
            throw new IllegalArgumentException("상위 리뷰 아이디가 없습니다.");
        }

        try {
            return reviewDao.selectById(reviewId);
        } catch (DataAccessException e) {
            log.error("ReviewService - DB 오류, 상위 리뷰 전체 조회 실패 - reviewId: {}", reviewId, e);
            throw  new IllegalStateException("DB 오류, 상위 리뷰 전체 조회 실패", e);
        }
    }
}
