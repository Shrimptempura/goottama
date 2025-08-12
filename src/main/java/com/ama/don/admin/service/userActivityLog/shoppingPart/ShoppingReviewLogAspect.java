package com.ama.don.admin.service.userActivityLog.shoppingPart;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.admin.service.userActivityLog.memberPart.JoinLogAspect;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import com.ama.don.common.dao.ReviewDao;
import com.ama.don.interior.dev.DevFindTarget;
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
public class ShoppingReviewLogAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ReviewDao reviewDao;

    @Pointcut("execution (* com.ama.don.shop.service.reviewservice.ShopReviewWriteService.execute(..)) && args(model)")
    public void shoppingReviewWriteMethod(Model model) {}

    @AfterReturning("shoppingReviewWriteMethod(model)")
    public void logShoppingReviewWrite(Model model) {
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        if (request == null) {
            log.warn("HttpServletRequest object not found in Model. Cannot log review activity.");
            return;
        }

        String productId = request.getParameter("product_id");
        if (productId == null || productId.isEmpty()) {
            log.warn("Product ID is null or empty. Cannot log review activity.");
            return;
        }

        Long userId = null;
        try {
            userId = DevFindTarget.getUserId();
        } catch (IllegalStateException e) {
            log.error("Failed to find logged-in user. Cannot log review activity. Error: {}", e.getMessage());
            return;
        }

        Long reviewId = null;
        try {
            reviewId = reviewDao.findLatestReviewByUserIdAndTargetId(userId, productId);
            if (reviewId == null) {
                log.warn("Failed to retrieve the latest review ID for user {} and product {}.", userId, productId);
                return;
            }
        } catch (Exception e) {
            log.error("Error retrieving latest review ID. User ID: {}, Product ID: {}. Error: {}", userId, productId, e.getMessage());
            return;
        }

        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setUser_id(userId);
        userActivityDto.setUser_activity_type("SHOP_REVIEW");
        userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
        userActivityDto.setUser_activity_target_type("SHOP_REVIEW");
        userActivityDto.setUser_activity_target_id(reviewId);
        userActivityDto.setUser_activity_details("User "+userId+" make shop review with product ID : "+ productId);

        try {
            userActivityLog.saveUserActivity(userActivityDto);
            log.info("Successfully logged review write activity. Review ID: {}, User ID: {}, Product ID: {}", reviewId, userId, productId);
        } catch (Exception e) {
            log.error("Failed to save review write activity log. Review ID: {}, User ID: {}. Error: {}", reviewId, userId, e.getMessage());
        }
    }

}
