package com.ama.don.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product_imgDto {
	long product_img_id;
	long product_id;
	String product_imgurl;
	String product_img_type;

}
