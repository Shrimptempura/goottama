package com.ama.don.admin.service.userActivityLog.interiorPart;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.interior.dev.DevFindTarget;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CompanyCommentLogAspect {

    private static final Logger log = LoggerFactory.getLogger(CompanyCommentLogAspect.class);
    private final SaveUserActivityLog userActivityLog;

    // 댓글 작성
    @Pointcut("execution(* com.ama.don.interior.service.CompanyCommentServiceImpl.addComment(..))")
    public void addCommentMethod() {}

    // 댓글 수정
    @Pointcut("execution(* com.ama.don.interior.service.CompanyCommentServiceImpl.updateMyComment(..))")
    public void updateCommentMethod() {}

    // 댓글 삭제
    @Pointcut("execution(* com.ama.don.interior.service.CompanyCommentServiceImpl.deleteMyComment(..))")
    public void deleteCommentMethod() {}

    @AfterReturning(pointcut = "addCommentMethod()", returning = "commentId")
    public void logAddComment(JoinPoint joinPoint, Long commentId) {
        Object[] args = joinPoint.getArgs();
        Long companyPostId = (Long) args[0];
        Long userId = DevFindTarget.getUserId();

        if (userId != null && commentId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "COMMENT_CREATE",
                    "INTERIOR_COMMENT",
                    commentId,
                    "User " + userId + " added a new comment to post ID: " + companyPostId
            );
            log.info("Successfully logged comment creation. User ID: {}, Comment ID: {}, Post ID: {}", userId, commentId, companyPostId);
        }
    }

    @AfterReturning(pointcut = "updateCommentMethod()")
    public void logUpdateComment(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long commentId = (Long) args[0];
        Long userId = DevFindTarget.getUserId();

        if (userId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "COMMENT_UPDATE",
                    "INTERIOR_COMMENT",
                    commentId,
                    "User " + userId + " updated comment ID: " + commentId
            );
            log.info("Successfully logged comment update. User ID: {}, Comment ID: {}", userId, commentId);
        }
    }

    @AfterReturning(pointcut = "deleteCommentMethod()")
    public void logDeleteComment(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long commentId = (Long) args[0];
        Long userId = DevFindTarget.getUserId();

        if (userId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "COMMENT_DELETE",
                    "INTERIOR_COMMENT",
                    commentId,
                    "User " + userId + " deleted comment ID: " + commentId
            );
            log.info("Successfully logged comment deletion. User ID: {}, Comment ID: {}", userId, commentId);
        }
    }
}