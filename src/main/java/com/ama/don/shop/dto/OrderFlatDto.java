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
public class OrderFlatDto {
    // orders 테이블 필드
	private long order_id;
	private long user_id;
	private Timestamp order_date;
	private String order_status;
	private int order_totalprice;
    //사용자가 입력
    private String orderName;
    private String orderEmail;
    private String orderPhone;
    
    //deliver 테이블 필드
    private long deliver_id;
	private String deliver_name;
	private String deliver_person;
	private String deliver_recipient_phone;
	private String deliver_loc;
	private String deliver_detail_loc;
	private String deliver_request;
	private String deliver_status;
	private Timestamp deliver_date;


    // payment 테이블 필드
    private Long payment_id;
    private String payment_type;
    private Timestamp payment_date;
    private String payment_status;
    private Integer payment_price;

    // 주문 상품 관련 필드들
    private Long orderProductId;
    private Long productId;
    private Integer op_quantity; // 수량
    private Integer op_price ;    // 단가
    private Integer op_totalprice ; // 합계 (이 필드 추가!)

    // 상품 관련 필드들
    private String product_id;
    private String product_name;     // 실제 컬럼명

    // 상품 이미지 관련 필드들
    private String product_imgurl;   // 상품 이미지
    private String product_img_type;
}


