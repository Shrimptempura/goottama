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
public class OrdersDto {

	private long order_id;
	private long user_id;
	private Timestamp order_date;
	private String order_status;
	private int order_totalprice;
	
	
}
