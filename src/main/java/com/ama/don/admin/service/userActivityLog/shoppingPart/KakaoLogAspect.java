package com.ama.don.admin.service.userActivityLog.shoppingPart;

import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class KakaoLogAspect {

    private static final Logger log = LoggerFactory.getLogger(KakaoLogAspect.class);

    private final SaveUserActivityLog userActivityLog;

    @Pointcut("execution(* com.ama.don.shop.service.Kakaopay.ShopKakaopayService.kakaoPayApproveWithTid(..)) && args(pgToken, orderId, userId, tid)")
    public void payApproveMethod(String pgToken, String orderId, String userId, String tid) {}

     /*
     @Around는 메서드 실행 자체를 감싸서, 성공과 실패 두 가지 경우를 모두 제어할 수 있음
     다른 곳에서는 실패가 크게 중요하지 않았고, 기록해야 할 이유도 없어서 @AfterReturning을 사용 했으나
     결제는 실패와 실패 이유(예, 잔액부족/카드정보오류 등)도 상당히 중요하므로 Around를 사용 함
     */
    @Around("payApproveMethod(pgToken, orderId, userId, tid)")
    public Object logPayApproval(ProceedingJoinPoint joinPoint, String pgToken, String orderId, String userId, String tid) throws Throwable {

        Long parsedUserId = null;
        Long parsedOrderId = null;

        try {
            if (userId != null) {
                parsedUserId = Long.valueOf(userId);
            }
            if (orderId != null) {
                parsedOrderId = Long.valueOf(orderId);
            }
        } catch (NumberFormatException e) {
            log.error("Invalid number format for userId or orderId. Cannot log payment. Error: {}", e.getMessage());
            return joinPoint.proceed();
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
            KakaoPayApprovalResponse approvalResponse = (KakaoPayApprovalResponse) result;

            String details = String.format("User %d successfully approved a KakaoPay payment for order ID %d (TID: %s). Total amount: %d", parsedUserId, parsedOrderId, tid, approvalResponse.getAmount().getTotal());
            UserActivityDto userActivityDto = createUserActivityDto(parsedUserId, "KAKAO_PAY_SUCCESS", "SHOP", parsedOrderId, details);
            saveLog(userActivityDto);

        } catch (Exception e) {
            String details = String.format("User %d failed to approve a KakaoPay payment for order ID %d. Error: %s", parsedUserId, parsedOrderId, e.getMessage());
            UserActivityDto userActivityDto = createUserActivityDto(parsedUserId, "KAKAO_PAY_FAILURE", "SHOP", parsedOrderId, details);
            saveLog(userActivityDto);
            throw e;
        }
        return result;
    }

    private UserActivityDto createUserActivityDto(Long userId, String type, String targetType, Long targetId, String details) {
        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setUser_id(userId);
        userActivityDto.setUser_activity_type(type);
        userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
        userActivityDto.setUser_activity_target_type(targetType);
        userActivityDto.setUser_activity_target_id(targetId);
        userActivityDto.setUser_activity_details(details);
        return userActivityDto;
    }

    private void saveLog(UserActivityDto userActivityDto) {
        try {
            userActivityLog.saveUserActivity(userActivityDto);
            log.info("Successfully logged user activity: {}", userActivityDto.getUser_activity_type());
        } catch (Exception e) {
            log.error("Failed to save user activity log for type {}. Error: {}", userActivityDto.getUser_activity_type(), e.getMessage());
        }
    }
}