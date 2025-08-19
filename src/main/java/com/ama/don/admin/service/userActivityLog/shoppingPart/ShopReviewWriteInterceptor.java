package com.ama.don.admin.service.userActivityLog.shoppingPart;

import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.common.dao.ReviewDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ShopReviewWriteInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ShopReviewWriteInterceptor.class);

    private final SaveUserActivityLog userActivityLog;
    private final ReviewDao reviewDao;
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // 리다이렉트가 발생했는지 확인 (응답 상태 코드 302: Found)
        if (response.getStatus() == HttpServletResponse.SC_FOUND) {
            String redirectUrl = response.getHeader("Location");

            // 리다이렉트 URL이 리뷰 등록 성공 URL인지 확인
            if (redirectUrl != null && redirectUrl.startsWith("/shop/product_detail")) {
                System.out.println("\n>>> afterCompletion을 통한 리뷰 등록 로그 시작: " + redirectUrl);

                String decodedUrl = URLDecoder.decode(redirectUrl, StandardCharsets.UTF_8.name());

                Pattern productPattern = Pattern.compile("product_id=(\\d+)");
                Pattern userPattern = Pattern.compile("userid=(\\d+)");

                Matcher productMatcher = productPattern.matcher(decodedUrl);
                Matcher userMatcher = userPattern.matcher(decodedUrl);

                String productId = null;
                String userId = null;

                if (productMatcher.find()) {
                    productId = productMatcher.group(1);
                }
                if (userMatcher.find()) {
                    userId = userMatcher.group(1);
                }

                if (productId == null || userId == null) {
                    log.warn("Failed to extract product ID or user ID from redirect URL. Cannot log review activity.");
                    return;
                }

                Long parsedUserId = Long.valueOf(userId);
                Long reviewId = null;
                try {
                    reviewId = reviewDao.findLatestReviewByUserIdAndTargetId(parsedUserId, productId);
                    if (reviewId == null) {
                        log.warn("Failed to retrieve the latest review ID for user {} and product {}.", parsedUserId, productId);
                        return;
                    }
                } catch (Exception e) {
                    log.error("Error retrieving latest review ID from Interceptor. User ID: {}, Product ID: {}. Error: {}", parsedUserId, productId, e.getMessage());
                    return;
                }

                UserActivityDto userActivityDto = new UserActivityDto();
                userActivityDto.setUser_id(parsedUserId);
                userActivityDto.setUser_activity_type("SHOP_REVIEW_WRITE");
                userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
                userActivityDto.setUser_activity_target_type("SHOP_REVIEW");
                userActivityDto.setUser_activity_target_id(reviewId);
                userActivityDto.setUser_activity_details("User " + parsedUserId + " wrote a review for product ID: " + productId);

                try {
                    userActivityLog.saveUserActivity(userActivityDto);
                    log.info("Successfully logged review write activity from Interceptor. Review ID: {}, User ID: {}, Product ID: {}", reviewId, parsedUserId, productId);
                } catch (Exception e) {
                    log.error("Failed to save review write activity log from Interceptor. Review ID: {}, User ID: {}. Error: {}", reviewId, parsedUserId, e.getMessage());
                }
            }
        }
    }
}