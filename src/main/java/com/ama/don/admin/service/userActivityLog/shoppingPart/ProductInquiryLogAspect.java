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
public class ProductInquiryLogAspect {

    private static final Logger log = LoggerFactory.getLogger(ProductInquiryLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ShopIDao shopIDao;

    @Pointcut("execution(* com.ama.don.shop.service.productinquiry.ShopProductInquiryWriteService.execute(..)) && args(model)")
    public void writeInquiry() {}

    @Pointcut("execution(* com.ama.don.shop.service.productinquiry.ShopProductInquiryUpdateService.execute(..)) && args(model)")
    public void updateInquiry() {}

    @Pointcut("execution(* com.ama.don.shop.service.productinquiry.ShopProductInquiryDeleteService.execute(..)) && args(model)")
    public void deleteInquiry() {}

    @AfterReturning("writeInquiry()")
    public void logWriteInquiry(Model model) {
        HttpServletRequest request = getRequestFromModel(model);
        if (request == null) return;

        Long userId = getUserId();
        if (userId == null) return;

        String productId = request.getParameter("product_id");

        Long inquiryId = shopIDao.findLatestProductInquiryByUserIdAndTargetId(userId, productId);
        if (inquiryId == null) {
            log.warn("Failed to retrieve the latest inquiry ID for user {} and product {}.", userId, productId);
            return;
        }
        String details = "User " + userId + " made a new inquiry with ID " + inquiryId + " for product ID " + productId;
        UserActivityDto userActivityDto = createUserActivityDto(userId, "SHOP_INQUIRY_WRITE", "SHOP", String.valueOf(inquiryId),
                details);
        saveLog(userActivityDto);
    }

    @AfterReturning("updateInquiry()")
    public void logUpdateInquiry(Model model) {
        HttpServletRequest request = getRequestFromModel(model);
        if (request == null) return;

        Long userId = getUserId();
        if (userId == null) return;

        String inquiryId = request.getParameter("pinquiry_id");
        String details = "User " + userId + " updated inquiry ID " + inquiryId;
        UserActivityDto userActivityDto = createUserActivityDto(userId, "SHOP_INQUIRY_UPDATE", "SHOP", String.valueOf(inquiryId),
                details);
        saveLog(userActivityDto);
    }

    @AfterReturning("deleteInquiry()")
    public void logDeleteInquiry(Model model) {
        HttpServletRequest request = getRequestFromModel(model);
        if (request == null) return;

        Long userId = getUserId();
        if (userId == null) return;

        String inquiryId = request.getParameter("pinquiry_id");
        String details = "User " + userId + " deleted inquiry ID " + inquiryId;
        UserActivityDto userActivityDto = createUserActivityDto(userId, "SHOP_INQUIRY_DELETE", "SHOP", String.valueOf(inquiryId),
                details);
        saveLog(userActivityDto);
    }

    private HttpServletRequest getRequestFromModel(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        if (request == null) {
            log.warn("HttpServletRequest object not found in Model.");
        }
        return request;
    }

    private Long getUserId() {
        try {
            return DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.error("Failed to find logged-in user. Error: {}", e.getMessage());
            return null;
        }
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