package com.ama.don.shop.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
	 	private String userId;
	    private String orderId;
	    private int amount;
	    private String productName;
	    private String paymentMethod;
	    private Map<String, Object> additionalData;
}
