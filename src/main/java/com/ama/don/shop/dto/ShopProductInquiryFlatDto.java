package com.ama.don.shop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductInquiryFlatDto {
	//user_detail
	Long user_id; 
	String user_name; 
	String user_nickname; 
	String user_gender;	// enum('M','F') 
	String user_birth;
	Timestamp user_created_at;
	String user_tel; 
	String user_zipcode;
	String user_addr; 
	String user_email; 
	String user_img;
	
	
	//product
	long product_id; 
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
    
    
    //상품 이미지
    Long product_img_id;
	String product_imgurl;
	String product_img_type;

    //product_inquiry
    long pinquiry_id;
	String pinquiry_content;
	Timestamp pinquiry_date;
	String pinquiry_status;
	
	
	
	//product_reply
	long preply_id;

	String preply_content;
	Timestamp preply_date;
	
}
