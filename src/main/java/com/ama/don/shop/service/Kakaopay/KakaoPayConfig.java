package com.ama.don.shop.service.Kakaopay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class KakaoPayConfig {
    
    // ✅ 공식 카카오페이 API (우선 시도)
    public static final String KAKAO_PAY_HOST = "https://open-api.kakaopay.com";
    public static final String KAKAO_PAY_READY_URL = "/online/v1/payment/ready";
    public static final String KAKAO_PAY_APPROVE_URL = "/online/v1/payment/approve";
    
    // ✅ 기존 카카오 API (fallback)
    public static final String KAKAO_PAY_HOST_LEGACY = "https://kapi.kakao.com";
    public static final String KAKAO_PAY_READY_URL_LEGACY = "/v1/payment/ready";
    public static final String KAKAO_PAY_APPROVE_URL_LEGACY = "/v1/payment/approve";
    
    // ✅ SECRET_KEY (ADMIN_KEY에서 변경)		//your-api-key kakaopay 40자리
    public static final String SECRET_KEY = "";
    
    // ✅ 우리 서버의 Callback URL들
    public static final String SUCCESS_URL = "http://localhost:8505/shop/kakaopaysuccess";
    public static final String CANCEL_URL = "http://localhost:8505/shop/kakaopaycancel";
    public static final String FAIL_URL = "http://localhost:8505/shop/kakaopayfail";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}