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
public class ShopreviewDto {
	Long review_id; 
	Long user_id; 
	String review_title; 
	String review_content; 
	int review_count; 
	Timestamp review_date; 
	Timestamp review_modify; 
	Long target_id; 
	String target_type; 
	int is_deleted;
}
