package com.ama.don.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayReadyResponse {
    private String tid;                // 결제 고유번호
    private String next_redirect_pc_url;  // 결제 페이지 URL
    private String created_at;         // 결제 준비 요청 시간
}