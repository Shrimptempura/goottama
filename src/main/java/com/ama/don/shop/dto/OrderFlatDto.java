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
    private Long orderId;
    private Long userId;
    private Timestamp orderDate;
    private String orderStatus;
    private Integer orderTotalprice;
    private String orderName;
    private String orderEmail;
    private String orderPhone;

    // deliver 테이블 필드
    private Long deliverId;
    private String deliverName;
    private String deliverPerson;
    private String deliverRecipientPhone;
    private String deliverLoc;
    private String deliverDetailLoc;
    private String deliverRequest;
    private String deliverStatus;
    private Timestamp deliverDate;

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
    private String product_name;     // 실제 컬럼명

    // 상품 이미지 관련 필드들
    private String product_imgurl;   // 상품 이미지
    private String product_img_type;
}
