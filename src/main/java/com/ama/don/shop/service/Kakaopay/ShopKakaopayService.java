package com.ama.don.shop.service.Kakaopay;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.KakaoPayReadyResponse;

@Service
public class ShopKakaopayService {
	
	private String savedTid; // 클래스 필드로 추가
	
	public KakaoPayReadyResponse kakaoPayReady(int amount) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "YOUR_ADMIN_KEY");
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "user123");
        params.add("item_name", "상품명");
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(amount));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/kakaopay/success");
        params.add("cancel_url", "http://localhost:8080/kakaopay/cancel");
        params.add("fail_url", "http://localhost:8080/kakaopay/fail");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<KakaoPayReadyResponse> response = restTemplate.postForEntity(
                "https://kapi.kakao.com/v1/payment/ready", request, KakaoPayReadyResponse.class
        );

        return response.getBody();
    }
	
	
	//response
	
	public KakaoPayApprovalResponse kakaoPayApprove(String pgToken) {
	    RestTemplate restTemplate = new RestTemplate();

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "KakaoAK " + "YOUR_ADMIN_KEY");
	    headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("cid", "TC0ONETIME");
	    params.add("tid", savedTid); // 1단계에서 저장한 tid
	    params.add("partner_order_id", "1001");
	    params.add("partner_user_id", "user123");
	    params.add("pg_token", pgToken);

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
	    ResponseEntity<KakaoPayApprovalResponse> response = restTemplate.postForEntity(
	            "https://kapi.kakao.com/v1/payment/approve", request, KakaoPayApprovalResponse.class
	    );

	    return response.getBody();
	}
}
