package com.ama.don.admin.adminConfig;

import com.ama.don.admin.service.userActivityLog.shoppingPart.ShopReviewWriteInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorWebConfig implements WebMvcConfigurer {
    private final ShopReviewWriteInterceptor shopReviewWriteInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(shopReviewWriteInterceptor)
                .addPathPatterns("/shop/review_write");
    }
}