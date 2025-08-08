package com.ama.don.shop.service.Kakaopay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//2. 별도 파일: KakaoPayConfig.java
@Configuration
public class KakaoPayConfig {
 public static final String KAKAO_PAY_HOST = "https://kapi.kakao.com";
 public static final String KAKAO_PAY_READY_URL = "/v1/payment/ready";
 public static final String KAKAO_PAY_APPROVE_URL = "/v1/payment/approve";
 public static final String KAKAO_PAY_CANCEL_URL = "/v1/payment/cancel";
 
 // 실제 키로 교체하세요!
 public static final String ADMIN_KEY = "YOUR_REAL_KAKAO_PAY_ADMIN_KEY";
 
 @Bean
 public RestTemplate restTemplate() {
     return new RestTemplate();
 }
}