package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Orders_productsDto {
	long po_id;
	long order_id;
	long product_id;
	int op_quantity;
	int op_price;
	Timestamp op_date;
	String op_status;
	int op_totalprice;
	
	public Orders_productsDto(long po_id, long order_id, long product_id, int op_quantity, int op_price,
			Timestamp op_date, String op_status, int op_totalprice) {
		this.po_id = po_id;
		this.order_id = order_id;
		this.product_id = product_id;
		this.op_quantity = op_quantity;
		this.op_price = op_price;
		this.op_date = op_date;
		this.op_status = op_status;
		this.op_totalprice = op_totalprice;
	}
	
	
}
