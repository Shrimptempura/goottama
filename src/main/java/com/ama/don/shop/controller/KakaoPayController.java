package com.ama.don.shop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.service.Kakaopay.ShopKakaopayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//3. 필수 추가: PaymentController.java

@Controller
@RequestMapping("/shop")  // 🔧 수정: /shop으로 변경
public class KakaoPayController{
 
 @Autowired
 private ShopKakaopayService shopKakaopayService;
 
 @Autowired
 private ShopIDao iDao;
 
/// ✅ 즉시 적용 - 카카오페이 콜백용 Controller 수정

//주문 승인
@GetMapping("/kakaopaysuccess")
public String kakaoPaySuccess(@RequestParam(value = "pg_token", required = false) String pgToken,HttpServletRequest request,Model model) {
    
	try {
        System.out.println("=== 카카오페이 성공 콜백 ===");
        System.out.println("PG Token: " + pgToken);
        // 카카오페이는 pg_token만 전달하므로 세션에서 정보 가져오기
        
        // URL의 모든 파라미터 출력 (디버깅용)
        System.out.println("=== 전달받은 모든 파라미터 ===");
        request.getParameterMap().forEach((key, values) -> {
            System.out.println(key + ": " + String.join(", ", values));
        });
        
        HttpSession session = request.getSession();
        
        // 세션에서 주문 정보 가져오기 (카카오페이는 pg_token만 전달)
        String kpayOrderId = (String) session.getAttribute("kpay_orderid");
        Long userId = (Long) session.getAttribute("userId");
        String kakaotid = (String) session.getAttribute("kakaotid");
        
        System.out.println("=== 세션 정보 ===");
        System.out.println("세션 Order ID: " + kpayOrderId);
        System.out.println("세션 User ID: " + userId);
        System.out.println("세션 TID: " + kakaotid);
        
        // 필수 정보 검증
        if (pgToken == null || pgToken.trim().isEmpty()) {
            throw new RuntimeException("PG Token이 없습니다.");
        }
        if (kpayOrderId == null || kpayOrderId.trim().isEmpty()) {
            throw new RuntimeException("세션에서 주문 ID를 찾을 수 없습니다.");
        }
        if (userId == null) {
            throw new RuntimeException("세션에서 사용자 ID를 찾을 수 없습니다.");
        }
        
        // 카카오페이 승인 API 호출
        KakaoPayApprovalResponse approval = shopKakaopayService.kakaoPayApproveWithTid(
            pgToken, 
            kpayOrderId, 
            String.valueOf(userId),
            kakaotid  // ← TID를 직접 전달
        );
        
        if (approval != null) {
            System.out.println("✅ 결제 승인 성공!");
            
            // 주문 정보를 DB에 저장하는 로직 추가
            Long order_id=saveOrderToDatabase(session, approval);
            
            // 세션 정리
            session.removeAttribute("kakaotid");
            session.removeAttribute("kpay_orderid");
            session.removeAttribute("orderFlatDto");
            session.removeAttribute("orderType");
            session.removeAttribute("originalTotal");
            
            model.addAttribute("approval", approval);
            model.addAttribute("success", true);
            
            // 6. 주문 완료 후 조회
            OrderFlatDto orderInfo = iDao.order_detail_flat(order_id);
            ArrayList<OrderFlatDto> orderProductsList = iDao.order_products_flat(order_id);
                
            // JSP로 전달
            model.addAttribute("orderInfo", orderInfo);
            model.addAttribute("orderProducts", orderProductsList);
            model.addAttribute("order_id", order_id);
            model.addAttribute("total_price",approval.getAmount().getTotal());
            model.addAttribute("message", "주문이 성공적으로 완료되었습니다!");
            
            // 팝업 창을 닫고 부모 창에 성공 알림
            return "shop/order_complete";
            
        } else {
            throw new RuntimeException("결제 승인 응답이 null입니다.");
        }
        
        
        
    } catch (Exception e) {
        System.out.println("❌ 결제 승인 실패: " + e.getMessage());
        e.printStackTrace();
        
        model.addAttribute("error", e.getMessage());
        model.addAttribute("success", false);
        
        // 팝업 창을 닫고 부모 창에 실패 알림
        return "shop/fail_popup";
    }
}

//✅ saveOrderToDatabase 메서드 - 카카오페이 승인 후 주문 저장
private Long saveOrderToDatabase(HttpSession session, KakaoPayApprovalResponse approval) {
 try {
     System.out.println("=== 주문 정보 DB 저장 시작 ===");
     
     // 세션에서 주문 관련 정보 가져오기
     OrderFlatDto orderFlatDto = (OrderFlatDto) session.getAttribute("orderFlatDto");
     String orderType = (String) session.getAttribute("orderType");
     Long userId = (Long) session.getAttribute("userId");
     Integer originalTotal = (Integer) session.getAttribute("originalTotal");
     
     System.out.println("주문 타입: " + orderType);
     System.out.println("사용자 ID: " + userId);
     System.out.println("원래 총액: " + originalTotal);
     System.out.println("승인 금액: " + approval.getAmount().getTotal());
     
     // 금액 검증
     if (originalTotal == null || !originalTotal.equals(approval.getAmount().getTotal())) {
         throw new RuntimeException("주문 금액과 결제 승인 금액이 일치하지 않습니다.");
     }
     
     if (orderFlatDto == null) {
         throw new RuntimeException("세션에서 주문 정보를 찾을 수 없습니다.");
     }
     
     // === 1. 주문 기본 정보 등록 ===
     OrdersDto ordersDto = new OrdersDto();
     ordersDto.setUser_id(userId);
     ordersDto.setOrder_totalprice(approval.getAmount().getTotal());
     
     iDao.order_write(ordersDto);
     long order_id = ordersDto.getOrder_id();
     
     System.out.println("✅ 주문 등록 완료 - Order ID: " + order_id);
     
     // === 2. 배송 정보 등록 ===
     try {
         DeliverDto deliverDto = new DeliverDto();
         deliverDto.setOrder_id(order_id);
         deliverDto.setDeliver_name(orderFlatDto.getDeliver_name());
         deliverDto.setDeliver_person(orderFlatDto.getDeliver_person());
         deliverDto.setDeliver_recipient_phone(orderFlatDto.getDeliver_recipient_phone());
         deliverDto.setDeliver_loc(orderFlatDto.getDeliver_loc());
         deliverDto.setDeliver_detail_loc(orderFlatDto.getDeliver_detail_loc());
         deliverDto.setDeliver_request(orderFlatDto.getDeliver_request());
         
         iDao.deliver_write(deliverDto);
         System.out.println("✅ 배송 정보 등록 완료");
         
     } catch (Exception e) {
         System.err.println("❌ 배송 정보 등록 실패: " + e.getMessage());
         throw new RuntimeException("배송 정보 등록에 실패했습니다.", e);
     }
     
     // === 3. 주문 상품 정보 등록 ===
     try {
         if ("direct".equals(orderType)) {
             // 바로 주문하기인 경우
             System.out.println("=== 바로 주문 상품 등록 ===");
             
             Orders_productsDto orderProduct = new Orders_productsDto();
             orderProduct.setOrder_id(order_id);
             orderProduct.setProduct_id(orderFlatDto.getProduct_id());
             orderProduct.setOp_quantity(orderFlatDto.getOp_quantity());
             orderProduct.setOp_price(orderFlatDto.getOp_price());
             orderProduct.setOp_totalprice(orderFlatDto.getOp_totalprice());
             
             iDao.orders_products_write(orderProduct);
             
             System.out.println("바로 주문 상품 등록 완료 - 상품ID: " + orderFlatDto.getProduct_id());
             
         } else {
             // 장바구니 주문인 경우
             System.out.println("=== 장바구니 주문 상품들 등록 ===");
             
             ArrayList<CartFlatDto> cartItems = iDao.cart_list_flat(userId);
             
             if (cartItems == null || cartItems.isEmpty()) {
                 throw new RuntimeException("장바구니가 비어있습니다.");
             }
             
             for (CartFlatDto cartItem : cartItems) {
                 Orders_productsDto orderProduct = new Orders_productsDto();
                 orderProduct.setOrder_id(order_id);
                 orderProduct.setProduct_id(cartItem.getProduct_id());
                 orderProduct.setOp_quantity(cartItem.getCart_quantity());
                 orderProduct.setOp_price(cartItem.getDiscountedPrice());
                 orderProduct.setOp_totalprice(cartItem.getTotalPrice());
                 
                 iDao.orders_products_write(orderProduct);
                 
                 System.out.println("장바구니 상품 등록: 상품ID=" + cartItem.getProduct_id() + 
                                  ", 수량=" + cartItem.getCart_quantity());
             }
             
             System.out.println("✅ 모든 장바구니 상품 등록 완료");
         }
         
     } catch (Exception e) {
         System.err.println("❌ 주문 상품 등록 실패: " + e.getMessage());
         throw new RuntimeException("주문 상품 등록에 실패했습니다.", e);
     }
     
     // === 4. 결제 정보 등록 ===
     try {
         PaymentDto paymentDto = new PaymentDto();
         paymentDto.setOrder_id(order_id);
         paymentDto.setPayment_price(approval.getAmount().getTotal());
         paymentDto.setPayment_tid(approval.getTid());
         // 카카오페이 결제 정보 추가 (PaymentDto에 해당 필드들이 있다면)
         // paymentDto.setPayment_method("KAKAO_PAY");
         // paymentDto.setPayment_tid(approval.getTid());
         // paymentDto.setPayment_aid(approval.getAid());
         // paymentDto.setPayment_approved_at(approval.getApproved_at());
         
         iDao.payment_write(paymentDto);
         System.out.println("✅ 결제 정보 등록 완료");
         
     } catch (Exception e) {
         System.err.println("❌ 결제 정보 등록 실패: " + e.getMessage());
         throw new RuntimeException("결제 정보 등록에 실패했습니다.", e);
     }
     
     // === 5. 장바구니 클리어 (장바구니 주문인 경우) ===
     if (!"direct".equals(orderType)) {
         try {
             iDao.cart_clear(userId);
             System.out.println("✅ 장바구니 클리어 완료");
         } catch (Exception e) {
             System.err.println("❌ 장바구니 클리어 실패: " + e.getMessage());
             // 장바구니 클리어 실패는 주문 전체를 실패시키지 않음
             System.out.println("⚠️  장바구니 클리어 실패했지만 주문은 완료됨");
         }
     }
     
	// 6. 주문 완료 후 조회
     try {
         OrderFlatDto orderInfo = iDao.order_detail_flat(order_id);
         ArrayList<OrderFlatDto> orderProductsList = iDao.order_products_flat(order_id);
         
         System.out.println("주문 완료 정보 조회 성공");
         System.out.println("주문 ID: " + order_id);
         System.out.println("주문 정보: " + (orderInfo != null ? "조회됨" : "null"));
         System.out.println("주문 상품 개수: " + (orderProductsList != null ? orderProductsList.size() : 0));
         
        
         
     } catch (Exception e) {
         System.err.println("주문 완료 정보 조회 실패: " + e.getMessage());
         e.printStackTrace();
     }

     return order_id;
     
     
 } catch (Exception e) {
     System.err.println("💥 주문 저장 중 치명적 오류: " + e.getMessage());
     e.printStackTrace();
     throw new RuntimeException("주문 저장에 실패했습니다: " + e.getMessage(), e);
 }
 

}

@GetMapping("/kakaopaycancel")
public String kakaoPayCancel(HttpServletRequest request, Model model) {
    System.out.println("=== 카카오페이 결제 취소 ===");
    
    // 모든 파라미터 출력
    request.getParameterMap().forEach((key, values) -> {
        System.out.println(key + ": " + String.join(", ", values));
    });
    
    HttpSession session = request.getSession();
    
    // 세션 정리
    session.removeAttribute("kakaotid");
    session.removeAttribute("kpay_orderid");
    session.removeAttribute("orderFlatDto");
    session.removeAttribute("orderType");
    session.removeAttribute("originalTotal");
    
    model.addAttribute("cancelled", true);
    
    // 팝업 창을 닫고 부모 창에 취소 알림
    return "shop/cancel_popup";
}

@GetMapping("/kakaopayfail")
public String kakaoPayFail(
    @RequestParam(value = "error_code", required = false) String errorCode,
    @RequestParam(value = "error_msg", required = false) String errorMsg,
    HttpServletRequest request, 
    Model model
) {
    System.out.println("=== 카카오페이 결제 실패 ===");
    System.out.println("Error Code: " + errorCode);
    System.out.println("Error Message: " + errorMsg);
    
    // 모든 파라미터 출력
    request.getParameterMap().forEach((key, values) -> {
        System.out.println(key + ": " + String.join(", ", values));
    });
    
    HttpSession session = request.getSession();
    
    // 세션 정리
    session.removeAttribute("kakaotid");
    session.removeAttribute("kpay_orderid");
    session.removeAttribute("orderFlatDto");
    session.removeAttribute("orderType");
    session.removeAttribute("originalTotal");
    
    model.addAttribute("failed", true);
    model.addAttribute("errorCode", errorCode);
    model.addAttribute("errorMessage", errorMsg);
    
    // 팝업 창을 닫고 부모 창에 실패 알림
    return "shop/fail_popup";
}
 
 // 디버깅을 위한 모든 파라미터 출력 메서드 (임시)
 @GetMapping("/kakaopay-debug")
 public String kakaoPayDebug(HttpServletRequest request, Model model) {
     System.out.println("=== 카카오페이 디버그 - 모든 파라미터 ===");
     
     request.getParameterMap().forEach((key, values) -> {
         System.out.println(key + ": " + String.join(", ", values));
     });
     
     model.addAttribute("debug", true);
     return "payment/debug";
 }
 
 
 
 
}