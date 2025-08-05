package com.ama.don.shop.dto;

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
}
