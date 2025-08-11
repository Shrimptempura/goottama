package com.ama.don.admin.service.reportService.checkReportAOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 사용자가 신고되어있는 텍스트 컨텐츠 조회 시 안내페이지로 보내는 메서드
 * TODO: 일단 공지에서 확인 한 결과 작동 함. 이제 다른 컨텐츠가 완성되면 적용 할 것.
 */
@Aspect
@Component
public class CheckReportAspect {

    private final CheckReportService checkReportService;

    public CheckReportAspect(CheckReportService checkReportService) {
        this.checkReportService = checkReportService;
    }

    @Pointcut("execution(* com.ama.don.admin.controller.AdminNoticeController.notice*(..))")
    public void contentViewControllers() {}

    @Around("contentViewControllers()")
    public Object checkReportStatus(ProceedingJoinPoint joinPoint) throws Throwable{
        Long targetId = null;
        Object[] args = joinPoint.getArgs();
        System.out.println("\nargs[0] : " + args[0]);
        System.out.println("args[1] : " + args[1] + "\n");
        if (args != null && args.length > 0 && args[1] != null) {
            Object arg = args[1];

            if (arg instanceof Long) {
                targetId = (Long) arg;
            } else if (arg instanceof Integer) {
                targetId = ((Integer) arg).longValue();
            } else if (arg instanceof String) {
                try {
                    targetId = Long.valueOf((String) arg);
                } catch (NumberFormatException e) {
                    targetId = null;
                }
            } else {
                targetId = null;
            }
        }

        if (targetId != null && checkReportService.isReported("NOTICE", targetId)) {
            return "redirect:/admin/accessDeniedPage";
        }
        return joinPoint.proceed();
    }
}
