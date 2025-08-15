package com.ama.don.shop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	long product_id; 
	long user_id;
	long category_id;
    String product_name;
	int product_price;
    BigDecimal product_discountrate;
    String product_mall_name;
    String product_madein;
    Timestamp product_release;
    String product_as_manager_phone;
    String product_type;
    String product_color;
    String product_istoday;
    Timestamp product_date;
	

	private Product_imgDto product_imgDto;	//여기와 collection properties가 같은 이름이어야한다. 
	
	// ✅ 이미지 여러 개 담는 리스트
	private List<Product_imgDto> product_imgDtoList; //여기와 collection properties가 같은 이름이어야한다. 
	
	
	
}
