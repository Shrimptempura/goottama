package com.ama.don.admin.service.userActivityLog.interiorPart;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.interior.dto.post.CompanyPostCreateDto;
import com.ama.don.interior.dto.post.CompanyPostUpdateDto;
import com.ama.don.interior.service.CompanyAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CompanyPostLogAspect {

    private final SaveUserActivityLog userActivityLog;
    private final CompanyAuthService companyAuthService;

    // 게시글 작성
    @Pointcut("execution(* com.ama.don.interior.service.CompanyPostServiceImpl.createCompanyPost(..)) && args(createDto, ..)")
    public void createPostMethod(CompanyPostCreateDto createDto) {}

    // 게시글 수정
    @Pointcut("execution(* com.ama.don.interior.service.CompanyPostServiceImpl.updatePost(..)) && args(dto, ..)")
    public void updatePostMethod(CompanyPostUpdateDto dto) {}

    // 게시글 삭제
    @Pointcut("execution(* com.ama.don.interior.service.CompanyPostServiceImpl.deletePost(..)) && args(companyPostId)")
    public void deletePostMethod(Long companyPostId) {}

    @AfterReturning(pointcut = "createPostMethod(createDto)", returning = "companyPostId")
    public void logPostCreation(CompanyPostCreateDto createDto, Long companyPostId) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && companyPostId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "POST_CREATE",
                    "INTERIOR_POST",
                    companyPostId,
                    "User " + userId + " created a new company post ID: " + companyPostId
            );
            log.info("Successfully logged company post creation. User ID: {}, Post ID: {}", userId, companyPostId);
        } else {
            log.warn("Failed to log company post creation. User ID: {}, Post ID: {}", userId, companyPostId);
        }
    }

    @AfterReturning("updatePostMethod(dto)")
    public void logPostUpdate(CompanyPostUpdateDto dto) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && dto.getCompanyPostId() != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "POST_UPDATE",
                    "INTERIOR_POST",
                    dto.getCompanyPostId(),
                    "User " + userId + " updated company post ID: " + dto.getCompanyPostId()
            );
            log.info("Successfully logged company post update. User ID: {}, Post ID: {}", userId, dto.getCompanyPostId());
        } else {
            log.warn("Failed to log company post update. User ID: {}, Post ID: {}", userId, dto.getCompanyPostId());
        }
    }

    @AfterReturning("deletePostMethod(companyPostId)")
    public void logPostDeletion(Long companyPostId) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && companyPostId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "POST_DELETE",
                    "INTERIOR_POST",
                    companyPostId,
                    "User " + userId + " deleted company post ID: " + companyPostId
            );
            log.info("Successfully logged company post deletion. User ID: {}, Post ID: {}", userId, companyPostId);
        } else {
            log.warn("Failed to log company post deletion. User ID: {}, Post ID: {}", userId, companyPostId);
        }
    }
}