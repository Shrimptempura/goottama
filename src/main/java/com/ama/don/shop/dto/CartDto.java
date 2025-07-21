package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
	int cart_id;
	long user_id;
	long product_id;
	int cart_quantity;
	Timestamp cart_date;
	
	public CartDto(int cart_id,long user_id,long product_id,int cart_quantity,Timestamp cart_date) {
		
		this.cart_id=cart_id;
		this.user_id=user_id;
		this.product_id=product_id;
		this.cart_quantity=cart_quantity;
		this.cart_date=cart_date;
		
	}	
	

}
