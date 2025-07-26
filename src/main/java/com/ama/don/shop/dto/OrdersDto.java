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

	long order_id;
	long user_id;
	Timestamp order_date;
	String order_status;
	int order_totalprice;
	
	
}
