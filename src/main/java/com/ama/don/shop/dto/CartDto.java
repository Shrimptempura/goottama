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
public class CartDto {
	long cart_id;
	long user_id;
	long product_id;
	int cart_quantity;
	Timestamp cart_date;
	
	
	private ProductDto productDto;

	private Product_imgDto product_imgDto;
}
