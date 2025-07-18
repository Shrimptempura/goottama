package com.ama.don.shop.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
	int product_id; 
	int category_id;
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
    
	public ProductDto(int product_id, int category_id,String product_name, int product_price, float product_discountrate,
			String product_img, String product_mall_name, String product_madein, Timestamp product_release,
			String product_as_manager_phone, String product_type, String product_color, String product_istoday,
			Timestamp product_date) {
		this.product_id = product_id;
		this.category_id = category_id;
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
    
	//
	// ✅ 이미지 여러 개 담는 리스트
    //private List<Product_imgDto> product_imgDtoList;
	private Product_imgDto product_imgDto;	//여기와 collection properties가 같은 이름이어야한다. 
	
	// ✅ 이미지 여러 개 담는 리스트
	private List<Product_imgDto> product_imgDtoList; //여기와 collection properties가 같은 이름이어야한다. 
	
}
