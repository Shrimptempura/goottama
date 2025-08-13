package com.ama.don.admin.service.userActivityLog.shoppingPart;

import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.interior.dev.DevFindTarget;
import com.ama.don.shop.dao.ShopIDao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderLogAspect {

    private static final Logger log = LoggerFactory.getLogger(ProductInquiryLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ShopIDao shopIDao;

    @Pointcut("execution(* com.ama.don.shop.service.orderservice.ShopOrderWriteService.execute(..)) && args(model)")
    public void writeOrder() {}

    @Pointcut("execution(* com.ama.don.shop.service.orderservice.ShopOrderUpdateService.execute(..)) && args(model)")
    public void updateOrder() {}

    @AfterReturning("writeOrder()")
    public void logOrderWrite(Model model) {
        HttpServletRequest request = getRequestFromModel(model);
        if (request == null) return;

        Long userId = getUserId();
        if (userId == null) return;

        Long orderId = shopIDao.findLatestOrderIdByUserId(userId);
        if (orderId == null) {
            log.warn("Failed to retrieve the latest order ID for user {}.", userId);
            return;
        }
        String details = String.format("User %d created a new order with ID %d.", userId, orderId);
        UserActivityDto userActivityDto = createUserActivityDto(userId, "ORDER", "SHOP", String.valueOf(orderId), details);
        saveLog(userActivityDto);
    }

    @AfterReturning("updateOrder()")
    public void logOrderUpdate(Model model) {
        HttpServletRequest request = getRequestFromModel(model);
        if (request == null) return;

        Long userId = getUserId();
        if (userId == null) return;

        String orderId = request.getParameter("order_id");
        String details = String.format("User %d update a order with ID %s.", userId, orderId);
        UserActivityDto userActivityDto = createUserActivityDto(userId, "ORDER", "SHOP", orderId, details);
        saveLog(userActivityDto);
    }

    private Long getUserId() {
        try {
            return DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.error("Failed to find logged-in user. Error: {}", e.getMessage());
            return null;
        }
    }

    private HttpServletRequest getRequestFromModel(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        if (request == null) {
            log.warn("HttpServletRequest object not found in Model.");
        }
        return request;
    }

    private UserActivityDto createUserActivityDto(Long userId, String type, String targetType, String targetId, String details) {
        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setUser_id(userId);
        userActivityDto.setUser_activity_type(type);
        userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
        userActivityDto.setUser_activity_target_type(targetType);
        userActivityDto.setUser_activity_target_id(Long.valueOf(targetId));
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
