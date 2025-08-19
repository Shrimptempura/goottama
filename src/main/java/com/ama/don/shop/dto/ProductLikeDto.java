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
public class ProductLikeDto {
	Long plike_id;
	Long user_id;
	Long product_id;
	String plike_islike;
	Timestamp plike_date;
}
