package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class product_inquiryDto {
	int pinquiry_id;
	long product_id;
	String pinquiry_content;
	Timestamp pinquiry_date;
	int pinquiry_group;
	int pinquiry_step;
	int pinquiry_indent;
	
	public product_inquiryDto(int pinquiry_id, long product_id, String pinquiry_content, Timestamp pinquiry_date,
			int pinquiry_group, int pinquiry_step, int pinquiry_indent) {
		super();
		this.pinquiry_id = pinquiry_id;
		this.product_id = product_id;
		this.pinquiry_content = pinquiry_content;
		this.pinquiry_date = pinquiry_date;
		this.pinquiry_group = pinquiry_group;
		this.pinquiry_step = pinquiry_step;
		this.pinquiry_indent = pinquiry_indent;
	}
	
	
}
