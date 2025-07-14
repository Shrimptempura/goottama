package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
	int product_id; 
    String product_name;
	int product_price;
    float product_discountrate;
    String product_mall_name;
    String product_madein;
    Timestamp product_release;
    String product_as_manager_phone;
    String product_type;
    String product_color;
    String product_istoday;
    Timestamp product_date;
    
	public ProductDto(int product_id, String product_name, int product_price, float product_discountrate,
			String product_img, String product_mall_name, String product_madein, Timestamp product_release,
			String product_as_manager_phone, String product_type, String product_color, String product_istoday,
			Timestamp product_date) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_discountrate = product_discountrate;
		this.product_mall_name = product_mall_name;
		this.product_madein = product_madein;
		this.product_release = product_release;
		this.product_as_manager_phone = product_as_manager_phone;
		this.product_type = product_type;
		this.product_color = product_color;
		this.product_istoday = product_istoday;
		this.product_date = product_date;
	}
    
   
}
