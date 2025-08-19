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
	
}
