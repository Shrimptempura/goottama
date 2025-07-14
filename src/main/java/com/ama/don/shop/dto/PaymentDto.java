package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDto {
	long payment_id;
	long order_id;
	String payment_type;
	Timestamp payment_date;
	String payment_status;
	int payment_price;
	
	public PaymentDto(long payment_id, long order_id, String payment_type, Timestamp payment_date,
			String payment_status, int payment_price) {
		this.payment_id = payment_id;
		this.order_id = order_id;
		this.payment_type = payment_type;
		this.payment_date = payment_date;
		this.payment_status = payment_status;
		this.payment_price = payment_price;
	}
	
	
}
