package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDto {

	long order_id;
	long user_id;
	Timestamp order_date;
	String order_status;
	int order_totalprice;
	
	public OrdersDto(long order_id, long user_id, Timestamp order_date, String order_status, int order_totalprice) {
		this.order_id = order_id;
		this.user_id = user_id;
		this.order_date = order_date;
		this.order_status = order_status;
		this.order_totalprice = order_totalprice;
	}
	
	
}
