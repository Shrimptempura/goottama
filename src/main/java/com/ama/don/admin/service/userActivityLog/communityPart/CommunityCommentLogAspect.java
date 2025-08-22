package com.ama.don.admin.service.userActivityLog.communityPart;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.community.dao.CommunityCommentDao;
import com.ama.don.community.dao.CommunityPostDao;
import com.ama.don.community.dto.Comment.CommentCreateDto;
import com.ama.don.community.dto.Review.ReviewPostDto;
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
public class CommunityCommentLogAspect {

    private static final Logger log = LoggerFactory.getLogger(CommunityCommentLogAspect.class);
    private final SaveUserActivityLog userActivityLog;
    private final CommunityCommentDao commentDao;
    private final CommunityPostDao communityPostDao;

    @Pointcut("execution(* com.ama.don.community.service.CommentService.createComment(..)) && args(dto)")
    public void createCommentMethod(CommentCreateDto dto) {}

    @Pointcut("execution(* com.ama.don.community.service.CommentService.updateComment(..)) && args(dto)")
    public void updateCommentMethod(CommentCreateDto dto) {}

    @Pointcut("execution(* com.ama.don.community.service.CommentService.deleteComment(..)) && args(commentId)")
    public void deleteCommentMethod(Long commentId) {}

    /**
     * 댓글 작성 로그 작성 메서드
     * @param joinPoint
     * @param dto
     */
    @AfterReturning("createCommentMethod(dto)")
    public void logCommentCreation(JoinPoint joinPoint, CommentCreateDto dto) {
        System.out.println("\n>>> 댓글 작성 AOP 로그 메서드 호출됨.");

        Long userId;
        try {
            userId = DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.warn("User authentication failed. Cannot log comment creation. Error: {}", e.getMessage());
            return;
        }

        userActivityLog.createAndSaveLog(
                userId,
                "COMMUNITY_COMMENT_CREATE",
                "COMMUNITY_COMMENT",
                dto.getComment_id(),
                "User " + userId + " created a comment for post ID: " + dto.getTargetId()
        );

        log.info("Successfully logged comment creation. User ID: {}, Comment ID: {}, Post ID: {}", userId, dto.getComment_id(), dto.getTargetId());
    }

    /**
     * 댓글 수정 로그 작성 메서드
     * @param joinPoint
     * @param dto
     */
    @AfterReturning("updateCommentMethod(dto)")
    public void logCommentUpdate(JoinPoint joinPoint, CommentCreateDto dto) {
        System.out.println("\n>>> 댓글 수정 AOP 로그 메서드 호출됨.");

        Long userId;
        try {
            userId = DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.warn("User authentication failed. Cannot log comment update. Error: {}", e.getMessage());
            return;
        }

        userActivityLog.createAndSaveLog(
                userId,
                "COMMUNITY_COMMENT_UPDATE",
                "COMMUNITY_COMMENT",
                dto.getComment_id(),
                "User " + userId + " updated comment ID: " + dto.getComment_id() + " for post ID: " + dto.getTargetId()
        );

        log.info("Successfully logged comment update. User ID: {}, Comment ID: {}, Post ID: {}", userId, dto.getComment_id(), dto.getTargetId());
    }

    /**
     * 댓글 삭제 로그 작성 메서드
     * @param joinPoint
     * @param commentId
     */
    @AfterReturning("deleteCommentMethod(commentId)")
    public void logCommentDeletion(JoinPoint joinPoint, Long commentId) {
        System.out.println("\n>>> 댓글 삭제 AOP 로그 메서드 호출됨.");

        Long userId;
        Long postId = null;
        try {
            userId = DevFindTarget.getUserId();
            ReviewPostDto postData = communityPostDao.findById(commentId);
            postId = postData.getPost_id();
            if(postId == null) {
                log.warn("Could not find PostId for commentId: {}. Log will be incomplete.", commentId);
            }
        } catch (IllegalStateException e) {
            log.warn("User authentication failed. Cannot log comment deletion. Error: {}", e.getMessage());
            return;
        }

        userActivityLog.createAndSaveLog(
                userId,
                "COMMUNITY_COMMENT_DELETE",
                "COMMUNITY_COMMENT",
                commentId,
                "User " + userId + " deleted comment ID: " + commentId + " from post ID: " + (postId != null ? postId : "unknown")
        );

        log.info("Successfully logged comment deletion. User ID: {}, Comment ID: {}, Post ID: {}", userId, commentId, postId);
    }
}