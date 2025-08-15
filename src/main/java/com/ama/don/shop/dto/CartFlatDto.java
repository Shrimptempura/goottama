package com.ama.don.shop.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//카트 읽기
public class CartFlatDto {
    
    // ===== 중요: 필드 순서를 SQL SELECT 순서와 맞추거나 aias 정확히 =====
	
    // 장바구니 정보
    private Long cart_id;
    private Integer cart_quantity;
    private Timestamp cart_date;
    // 사용자 정보  
    private Long user_id;
    private String user_name;        // 반드시 String 타입!
    
    // 상품 정보
    private Long product_id;
    private String product_name;     // 반드시 String 타입!
    private String product_mall_name; // 반드시 String 타입!
    private Integer product_price;   // 반드시 Integer 타입!
    private BigDecimal product_discountrate; // 반드시 BigDecimal 타입!
    private String product_istoday;
    
    private Timestamp product_date;
    
    // 이미지 정보
    private String product_imgurl;   // 반드시 String 타입!
    private String product_img_type; // 반드시 String 타입!
    
    
    // 뷰에서 사용할 계산 메서드들
    public Integer getDiscountedPrice() {
        if (product_discountrate != null && product_discountrate.compareTo(BigDecimal.ZERO) > 0) {
            double discountPercent = product_discountrate.doubleValue() * 100;
            return product_price - (int)(product_price * discountPercent / 100);
        }
        return product_price;
    }
    
    /**
     * 할인 금액 (원래 가격에서 할인된 금액 × 수량)
     */
    public Integer getDiscountAmount() {
        if (product_discountrate != null && product_discountrate.compareTo(BigDecimal.ZERO) > 0) {
            double discountPercent = product_discountrate.doubleValue() * 100;
            int discountPerItem = (int)(product_price * discountPercent / 100);
            return discountPerItem * cart_quantity;
        }
        return 0;
    }
    
    public Integer getTotalPrice() {
        return getDiscountedPrice() * cart_quantity;
    }
    
    public String getDiscountText() {
        if (product_discountrate != null && product_discountrate.compareTo(BigDecimal.ZERO) > 0) {
            int discountPercent = (int)(product_discountrate.doubleValue() * 100);
            return discountPercent + "% 할인";
        }
        return "";
    }
    
    /**
     * 할인율이 있는지 확인
     */
    public boolean hasDiscount() {
        return product_discountrate != null && product_discountrate.compareTo(BigDecimal.ZERO) > 0;
    }
}
