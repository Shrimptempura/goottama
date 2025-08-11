package com.ama.don.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoPayReadyRequest {
	 private String cid = "TC0ONETIME";
	 private String partner_order_id;
	 private Long partner_user_id;
	 private String item_name;
	 private int quantity;
	 private int total_amount;
	 private int vat_amount;
	 private int tax_free_amount = 0;
	 private String approval_url;
	 private String fail_url;
	 private String cancel_url;
}
