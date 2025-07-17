package com.ama.don.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product_imgDto {
	long product_img_id;
	long product_id;
	String product_imgurl;
	String product_img_type;
	
	
	public Product_imgDto(long product_img_id, long product_id, String product_imgurl, String product_img_type) {
		this.product_img_id = product_img_id;
		this.product_id = product_id;
		this.product_imgurl = product_imgurl;
		this.product_img_type = product_img_type;
	}

}
