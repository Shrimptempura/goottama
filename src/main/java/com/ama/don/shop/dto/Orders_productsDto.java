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
public class Orders_productsDto {
	long orders_products_id;
	long order_id;
	long product_id;
	int op_quantity;
	int op_price;
	String op_status;
	Timestamp op_date;
	int op_totalprice;
	
	//
}


