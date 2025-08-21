package com.ama.don.admin.service.userActivityLog.interiorPart;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.interior.dto.review.CompanyReviewCreateDto;
import com.ama.don.interior.dto.review.CompanyReviewUpdateDto;
import com.ama.don.interior.service.CompanyAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CompanyReviewLogAspect {

    private final SaveUserActivityLog userActivityLog;
    private final CompanyAuthService companyAuthService;

    // 리뷰 작성
    @Pointcut("execution(* com.ama.don.interior.service.CompanyReviewServiceImpl.createReview(..)) && args(createReviewDto, ..)")
    public void createReviewMethod(CompanyReviewCreateDto createReviewDto) {}

    // 리뷰 수정
    @Pointcut("execution(* com.ama.don.interior.service.CompanyReviewServiceImpl.updateReview(..)) && args(updateReviewDto, ..)")
    public void updateReviewMethod(CompanyReviewUpdateDto updateReviewDto) {}

    // 리뷰 삭제
    @Pointcut("execution(* com.ama.don.interior.service.CompanyReviewServiceImpl.deleteReview(..)) && args(reviewId)")
    public void deleteReviewMethod(Long reviewId) {}

    @AfterReturning(pointcut = "createReviewMethod(createReviewDto)", returning = "reviewId")
    public void logReviewCreation(CompanyReviewCreateDto createReviewDto, Long reviewId) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && reviewId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "REVIEW_CREATE",
                    "INTERIOR_REVIEW",
                    reviewId,
                    "User " + userId + " created a new interior review ID: " + reviewId + " for company ID: " + createReviewDto.getCompanyId()
            );
            log.info("Successfully logged interior review creation. User ID: {}, Review ID: {}", userId, reviewId);
        } else {
            log.warn("Failed to log interior review creation. User ID: {}, Review ID: {}", userId, reviewId);
        }
    }

    @AfterReturning("updateReviewMethod(updateReviewDto)")
    public void logReviewUpdate(CompanyReviewUpdateDto updateReviewDto) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && updateReviewDto.getReviewId() != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "REVIEW_UPDATE",
                    "INTERIOR_REVIEW",
                    updateReviewDto.getReviewId(),
                    "User " + userId + " updated interior review ID: " + updateReviewDto.getReviewId()
            );
            log.info("Successfully logged interior review update. User ID: {}, Review ID: {}", userId, updateReviewDto.getReviewId());
        } else {
            log.warn("Failed to log interior review update. User ID: {}, Review ID: {}", userId, updateReviewDto.getReviewId());
        }
    }

    @AfterReturning("deleteReviewMethod(reviewId)")
    public void logReviewDeletion(Long reviewId) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && reviewId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "REVIEW_DELETE",
                    "INTERIOR_REVIEW",
                    reviewId,
                    "User " + userId + " deleted interior review ID: " + reviewId
            );
            log.info("Successfully logged interior review deletion. User ID: {}, Review ID: {}", userId, reviewId);
        } else {
            log.warn("Failed to log interior review deletion. User ID: {}, Review ID: {}", userId, reviewId);
        }
    }
}