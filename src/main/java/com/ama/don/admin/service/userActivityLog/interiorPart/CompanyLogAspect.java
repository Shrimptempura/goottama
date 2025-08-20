package com.ama.don.admin.service.userActivityLog.interiorPart;

import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.interior.service.CompanyAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CompanyLogAspect {

    private final SaveUserActivityLog userActivityLog;
    private final CompanyAuthService companyAuthService;

    // 업체 등록
    @Pointcut("execution(* com.ama.don.interior.service.CompanyServiceImpl.createCompany(..))")
    public void createCompanyMethod() {}

    // 업체 수정
    @Pointcut("execution(* com.ama.don.interior.service.CompanyServiceImpl.updateCompany(..)) && args(updateDto, ..)")
    public void updateCompanyMethod(Object updateDto) {}

    // 업체 삭제
    @Pointcut("execution(* com.ama.don.interior.service.CompanyServiceImpl.deleteCompany(..))")
    public void deleteCompanyMethod() {}

    @AfterReturning(pointcut = "createCompanyMethod()", returning = "companyId")
    public void logCompanyCreation(Long companyId) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && companyId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "COMPANY_CREATE",
                    "COMPANY",
                    companyId,
                    "User " + userId + " created a new company with ID: " + companyId
            );
            log.info("Successfully logged company creation. User ID: {}, Company ID: {}", userId, companyId);
        } else {
            log.warn("Failed to log company creation. User ID: {}, Company ID: {}", userId, companyId);
        }
    }

    @AfterReturning(pointcut = "updateCompanyMethod(updateDto)", returning = "companyId")
    public void logCompanyUpdate(JoinPoint joinPoint, Long companyId) {
        Long userId = companyAuthService.getLoginUserId();

        if (userId != null && companyId != null) {
            userActivityLog.createAndSaveLog(
                    userId,
                    "COMPANY_UPDATE",
                    "COMPANY",
                    companyId,
                    "User " + userId + " updated company with ID: " + companyId
            );
            log.info("Successfully logged company update. User ID: {}, Company ID: {}", userId, companyId);
        } else {
            log.warn("Failed to log company update. User ID: {}, Company ID: {}", userId, companyId);
        }
    }

    @Around("deleteCompanyMethod()")
    public Object logCompanyDeletion(ProceedingJoinPoint joinPoint) throws Throwable{
        Long companyId = companyAuthService.requireMyCompanyId();
        Long userId = companyAuthService.getLoginUserId();

        try {
            Object result = joinPoint.proceed();

            if (userId != null && companyId != null) {
                userActivityLog.createAndSaveLog(
                        userId,
                        "COMPANY_DELETE",
                        "COMPANY",
                        companyId,
                        "User " + userId + " deleted company with ID: " + companyId
                );
                log.info("Successfully logged company deletion. User ID: {}, Company ID: {}", userId, companyId);
            } else {
                log.warn("Failed to log company deletion. User ID: {}, Company ID: {}", userId, companyId);
            }

            return result;
        } catch (Throwable e) {
            log.error("Company deletion failed before logging. User ID: {}, Company ID: {}", userId, companyId, e);
            throw e;
        }
    }
}