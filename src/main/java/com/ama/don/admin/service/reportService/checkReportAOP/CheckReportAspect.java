package com.ama.don.admin.service.reportService.checkReportAOP;

import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.interior.dev.DevFindTarget;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 사용자가 신고되어있는 텍스트 컨텐츠 조회 시 안내페이지로 보내는 메서드
 */
@Aspect
@Component
public class CheckReportAspect {

    private final CheckReportService checkReportService;

    public CheckReportAspect(CheckReportService checkReportService) {
        this.checkReportService = checkReportService;
    }

    /**
     * 조회 관련 메서드를 포함하는 포인트컷 정의.
     * `_detail`, `_view`와 `detail`, `edit`를 포함하는 메서드를 대상으로.
     * `updatePost`의 경우도 조회 후 수정을 하는 과정이므로 포함.
     */
    @Pointcut("execution(* com.ama.don.shop.controller.*._detail(..)) || " +
            "execution(* com.ama.don.shop.controller.*._view(..)) || " +
            "execution(* com.ama.don.interior.controller.*.get*Detail(..)) || " +
            "execution(* com.ama.don.interior.controller.*.show*Editform(..)) || " +
            "execution(* com.ama.don.interior.controller.*.updatePost(..))")
    public void contentViewMethods() {}

    @Around("contentViewMethods()")
    public Object checkReportStatus(ProceedingJoinPoint joinPoint) throws Throwable {
        long userRoleId = getUserRoleId();

        // 관리자급 계정(300, 400)은 신고된 콘텐츠를 볼 수 있도록 예외 처리
        if (userRoleId >= 300) {
            return joinPoint.proceed();
        }

        String targetType = getTargetTypeFromController(joinPoint.getTarget().getClass());

        if (targetType == null) {
            return joinPoint.proceed();
        }

        Optional<Long> targetIdOpt = findIdFromArgs(joinPoint);

        if (targetIdOpt.isPresent()) {
            Long targetId = targetIdOpt.get();
            if (checkReportService.isReported(targetType, targetId)) {
                return "redirect:/admin/accessDeniedPage";
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 현재 실행 중인 컨트롤러 클래스 이름으로 신고 대상 타입을 결정.
     */
    private String getTargetTypeFromController(Class<?> controllerClass) {
        String name = controllerClass.getSimpleName();
        if (name.contains("ShopController")) {
            // TODO: ShopController의 메서드명을 기반으로 더 세분화 가능 해 보임
            return "SHOP_PRODUCT"; // 상품에 대한 신고
        } else if (name.contains("CompanyReviewController")) {
            return "COMPANY_REVIEW"; // 회사 리뷰에 대한 신고
        } else if (name.contains("CompanyPostController")) {
            return "COMPANY_POST"; // 회사 게시글에 대한 신고
        }
        return null; // AOP를 적용하지 않을 경우
    }

    /**
     * 메서드의 모든 인자를 탐색하여 ID에 해당하는 Long 값을 찾음. <br/>
     * 기존에는 `Method method = getTargetMethod(joinPoint);` 이 방식을 사용 했으나
     * `joinPoint.getArgs()[i].getClass()`가 정확한 매개변수 타입을 반환하지 못하는 경우가 발생,
     * 따라서 `MethodSignature`를 이용해 리플렉션으로 직접 메서드를 찾는 과정에서 발생할 수 있는 오류를 방지하는 방향으로 바꿈
     * @param joinPoint
     * @return
     */
    private Optional<Long> findIdFromArgs(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();

        if (args == null || args.length == 0) {
            return Optional.empty();
        }

        for (int i = 0; i < args.length; i++) {
            String paramName = parameterNames[i];
            if (paramName.equals("companyPostId")) { // companyPostId라는 이름의 인자를 명시적으로 찾음
                if (args[i] instanceof Long) {
                    return Optional.of((Long) args[i]);
                } else if (args[i] instanceof String) {
                    try {
                        return Optional.of(Long.valueOf((String) args[i]));
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        for (Object arg : args) {
            if (arg instanceof Long) {
                return Optional.of((Long) arg);
            }
        }
        return Optional.empty();
    }

    private Method getTargetMethod(ProceedingJoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Class<?>[] parameterTypes = new Class<?>[joinPoint.getArgs().length];
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                parameterTypes[i] = joinPoint.getArgs()[i].getClass();
            }
            return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private long getUserRoleId() {
        try {
            UserTotalDataDTO userTotalDataDTO = DevFindTarget.findTarget().getUserTotalDataDTO();
            return userTotalDataDTO.getRoles_id();
        } catch (Exception e) {
            return 100;
        }
    }
}