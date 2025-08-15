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
public class product_inquiryDto {
	long pinquiry_id;
	long user_id;
	long product_id;
	String pinquiry_content;
	Timestamp pinquiry_date;
	String pinquiry_status;
	
}
