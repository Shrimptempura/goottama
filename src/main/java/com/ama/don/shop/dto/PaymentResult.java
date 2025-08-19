package com.ama.don.shop.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PaymentResult {
	 private boolean success;
	    private String message;
	    private String redirectUrl;
	    private Map<String, Object> data;
	    
	    public PaymentResult(boolean success, String message) {
	        this.success = success;
	        this.message = message;
	    }

}
