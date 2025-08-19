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
	private long order_id;				//주문 아이디
	private long user_id;				//사용자 아이디
	private Timestamp order_date;		//주문 날짜
	private String order_status;		//주문 상태 (주문 완료)
	private int order_totalprice;		//주문 전체 가격
    //사용자가 입력
    private String orderName;			//주문하는 사람 이름
    private String orderEmail;			//주문하는 사람 이메맇
    private String orderPhone;			//주문하는사람 전화번호
    
    //deliver 테이블 필드
    private long deliver_id;			//배송 아이디
	private String deliver_name;		//배송지 이름
	private String deliver_person;		//배송 받는사람 
	private String deliver_recipient_phone;	//배송 받는사람의 전화번호
	private String deliver_loc;			//배송 위치
	private String deliver_detail_loc;	//배송 세부 위치
	private String deliver_request;		//배송 시 요청 내역
	private String deliver_status;		//배송 상태 (배송대기,배송완료)
	private Timestamp deliver_date;		//배송일


    // payment 테이블 필드
    private Long payment_id;			//결재 아이디
    private String payment_type;		//결재 타입	(카카오결재로 고정하고 있다.)
    private Timestamp payment_date;		//결재 시간
    private String payment_status;		//결재 상태								
    private Integer payment_price;		//사용자가 지불한 금액 	(사용자가 실제로 지불한 것이 아니라 주문한 총 금액과 같다.) 

    // 주문 상품 관련 필드들
    private Long orderProductId;		//주문 상품 아이디
    private Long productId;				//상품 아이디
    private Integer op_quantity; 		//주문 상품 수량 (사용자가 주문한 상품 수량)
    private Integer op_price ;    		//주문 상품 가격 (각 상품가격)	 // 단가
    private Integer op_totalprice ; 	//주문 상품 전체 합	// 합계 

    // 상품 관련 필드들
    private Long product_id;			//상품 아이디
    private String product_name;     	//상품 이름	// 실제 컬럼명

    // 상품 이미지 관련 필드들
    private String product_imgurl;   // 상품 이미지
    private String product_img_type;	//상품 이미지 타입 (메인, 서브로 되어있다.)
}


