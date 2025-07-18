package com.ama.don.shop.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliverDto {
	long deliver_id;
	long order_id;
	String deliver_name;
	String deliver_person;
	String deliver_recipient_phone;
	String deliver_loc;
	String deliver_detail_loc;
	String deliver_request;
	String deliver_status;
	Timestamp deliver_date;
	
	public DeliverDto(long deliver_id, long order_id, String deliver_name, String deliver_person,
			String deliver_recipient_phone, String deliver_loc, String deliver_detail_loc, String deliver_request,
			String deliver_status, Timestamp deliver_date) {
		
		this.deliver_id = deliver_id;
		this.order_id = order_id;
		this.deliver_name = deliver_name;
		this.deliver_person = deliver_person;
		this.deliver_recipient_phone = deliver_recipient_phone;
		this.deliver_loc = deliver_loc;
		this.deliver_detail_loc = deliver_detail_loc;
		this.deliver_request = deliver_request;
		this.deliver_status = deliver_status;
		this.deliver_date = deliver_date;
	}
	
	
}
