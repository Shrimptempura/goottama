package com.ama.don.shop.service.paymentService;

import java.util.HashMap;
import java.util.Map;

import com.ama.don.shop.dto.PaymentResult;

import jakarta.servlet.http.HttpServletRequest;

public class BankPayService {
    
    public PaymentResult processPayment(HttpServletRequest request, int totalAmount) {
        try {
            System.out.println("무통장 입금 처리 시작");
            System.out.println("입금 금액: " + totalAmount);
            
            PaymentResult result = new PaymentResult(true, "무통장 입금 주문이 접수되었습니다.");
            
            // 계좌 정보 추가
            Map<String, Object> data = new HashMap<>();
            data.put("bankName", "농협은행");
            data.put("accountNumber", "301-1234-5678");
            data.put("accountHolder", "예금주: 구트아카데미");
            data.put("amount", totalAmount);
            result.setData(data);
            
            return result;
            
        } catch (Exception e) {
            return new PaymentResult(false, "무통장 입금 처리 실패: " + e.getMessage());
        }
    }
}