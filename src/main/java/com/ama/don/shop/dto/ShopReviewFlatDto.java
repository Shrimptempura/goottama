package com.ama.don.shop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopReviewFlatDto {
	//review
	Long review_id; 
	String review_title; 
	String review_content; 
	int review_count; 
	Timestamp review_date; 
	Timestamp review_modify;
	Long target_id;
	String target_type;	// enum('SHOP','COMMUNITY','INTERIOR')
	int is_deleted;
	
	
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
	
	//상품 정보
	Long product_id; 
	Long category_id;
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
}
