package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
	long payment_id;
	long order_id;
	String payment_type;
	Timestamp payment_date;
	String payment_status;
	int payment_price;	
	
	
}
