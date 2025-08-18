package com.ama.don.admin.service.userActivityLog.communityPart;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.community.dto.Review.ReviewDetailDto;
import com.ama.don.community.dto.Review.ReviewWriteDto;
import com.ama.don.interior.dev.DevFindTarget;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CommunityPostLogAspect {

    private static final Logger log = LoggerFactory.getLogger(CommunityPostLogAspect.class);
    private final SaveUserActivityLog userActivityLog;

    // 게시글 작성
    @Pointcut("execution(* com.ama.don.community.service.Write_viewService.createReviewWithPost(..)) && args(userId, dto)")
    public void createPostMethod(Long userId, ReviewWriteDto dto) {}

    // 게시글 수정
    @Pointcut("execution(* com.ama.don.community.service.CommunityDetailService.updatePostAndReview(..)) && args(dto)")
    public void updatePostMethod(ReviewDetailDto dto) {}

    // 게시글 삭제
    @Pointcut("execution(* com.ama.don.community.service.CommunityDetailService.deletePost(..)) && args(postId)")
    public void deletePostMethod(Long postId) {}

    @AfterReturning("createPostMethod(userId, dto)")
    public void logPostCreation(Long userId, ReviewWriteDto dto) {
        System.out.println("\n>>> 게시글 작성 AOP 로그 메서드 호출됨.");

        // AOP 메서드에 직접 전달되는 userId를 사용
        userActivityLog.createAndSaveLog(
                userId,
                "COMMUNITY_POST_CREATE",
                "COMMUNITY_REVIEW",
                dto.getPost_id(),
                "User " + userId + " created a post titled: " + dto.getReview_title()
        );

        log.info("Successfully logged post creation. User ID: {}, Post ID: {}, Title: {}", userId, dto.getPost_id(), dto.getReview_title());
    }

    @AfterReturning("updatePostMethod(dto)")
    public void logPostUpdate(ReviewDetailDto dto) {
        System.out.println("\n>>> 게시글 수정 AOP 로그 메서드 호출됨.");

        Long userId;
        try {
            userId = DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.warn("User authentication failed. Cannot log post update. Error: {}", e.getMessage());
            return;
        }

        userActivityLog.createAndSaveLog(
                userId,
                "COMMUNITY_POST_UPDATE",
                "COMMUNITY_REVIEW",
                dto.getPost_id(),
                "User " + userId + " updated post ID: " + dto.getPost_id()
        );

        log.info("Successfully logged post update. User ID: {}, Post ID: {}", userId, dto.getPost_id());
    }

    @AfterReturning("deletePostMethod(postId)")
    public void logPostDeletion(Long postId) {
        System.out.println("\n>>> 게시글 삭제 AOP 로그 메서드 호출됨.");

        Long userId;
        try {
            userId = DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.warn("User authentication failed. Cannot log post deletion. Error: {}", e.getMessage());
            return;
        }

        userActivityLog.createAndSaveLog(
                userId,
                "COMMUNITY_POST_DELETE",
                "COMMUNITY_REVIEW",
                postId,
                "User " + userId + " deleted post ID: " + postId
        );

        log.info("Successfully logged post deletion. User ID: {}, Post ID: {}", userId, postId);
    }
}