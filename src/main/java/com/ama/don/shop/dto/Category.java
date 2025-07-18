package com.ama.don.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category {
	int category_id;
	String category_main;
	String category_sub;
	
	public Category(int category_id, String category_main, String category_sub) {
		this.category_id = category_id;
		this.category_main = category_main;
		this.category_sub = category_sub;
	}
	
	
}
