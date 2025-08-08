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

import jakarta.servlet.http.HttpSession;

//3. 필수 추가: PaymentController.java

@Controller
@RequestMapping("/shop")  // 🔧 수정: /shop으로 변경
public class KakaoPayController{
 
 @Autowired
 private ShopKakaopayService shopKakaopayService;
 
 @Autowired
 private ShopIDao iDao;
 
 // 결제 성공 처리
 @GetMapping("/kakaopaysuccess") 
public String paymentSuccess(@RequestParam String pg_token,
                            @RequestParam String partner_order_id,
                            @RequestParam String partner_user_id,
                            HttpSession session,
                            Model model) {
     try {
         System.out.println("결제 성공 콜백 호출됨");
         System.out.println("pg_token: " + pg_token);
         System.out.println("partner_order_id: " + partner_order_id);
         System.out.println("partner_user_id: " + partner_user_id);
         
         // 세션에서 저장된 정보 가져오기
         String savedTid = (String) session.getAttribute("kakaotid");
         String savedOrderId = (String) session.getAttribute("kpay_orderid");
         OrderFlatDto orderFlatDto = (OrderFlatDto) session.getAttribute("orderFlatDto");
         
         if (savedTid == null || orderFlatDto == null) {
             throw new RuntimeException("결제 정보가 세션에서 찾을 수 없습니다.");
         }
         
         // 카카오페이 승인 요청
         KakaoPayApprovalResponse approvalResponse = shopKakaopayService.kakaoPayApprove(
             pg_token, partner_order_id, partner_user_id
         );
         
         // 🎯 여기서 실제 주문 및 결제 정보를 DB에 저장해야 합니다!
         saveOrderAndPaymentToDatabase(orderFlatDto, approvalResponse, session);
         
         // 성공 페이지로 데이터 전달
         model.addAttribute("approvalResponse", approvalResponse);
         model.addAttribute("orderInfo", orderFlatDto);
         
         // 세션 정리
         session.removeAttribute("kakaotid");
         session.removeAttribute("kpay_orderid");
         session.removeAttribute("orderFlatDto");
         
         return "/shop/kakaopaysuccess"; // success.jsp 또는 success.html
         
     } catch (Exception e) {
         System.err.println("결제 승인 처리 실패: " + e.getMessage());
         e.printStackTrace();
         model.addAttribute("error", "결제 처리 중 오류가 발생했습니다: " + e.getMessage());
         return "shop/kakao_redirect";
     }
 }
 
// 결제 취소 처리
 @GetMapping("/kakaopaycancel")
public String paymentCancel(@RequestParam String partner_order_id, 
                           Model model, 
                           HttpSession session) {
     System.out.println("결제 취소됨: " + partner_order_id);
     
     // 세션 정리
     session.removeAttribute("kakaotid");
     session.removeAttribute("kpay_orderid");
     session.removeAttribute("orderFlatDto");
     
     model.addAttribute("message", "결제가 취소되었습니다.");
     model.addAttribute("orderId", partner_order_id);
     return "shop/kakaopaycancel";
 }
 
// 결제 실패 처리
 @GetMapping("/kakaopayfail")
public String paymentFail(@RequestParam String partner_order_id, 
                         Model model, 
                         HttpSession session) {
     System.out.println("결제 실패됨: " + partner_order_id);
     
     // 세션 정리
     session.removeAttribute("kakaotid");
     session.removeAttribute("kpay_orderid");
     session.removeAttribute("orderFlatDto");
     
     model.addAttribute("message", "결제에 실패했습니다.");
     model.addAttribute("orderId", partner_order_id);
     return "shop/kakaopayfail";
 }
 
// 🎯 핵심: 실제 주문 및 결제 정보를 DB에 저장하는 메서드
private void saveOrderAndPaymentToDatabase(OrderFlatDto orderFlatDto, 
                                          KakaoPayApprovalResponse approvalResponse,
                                          HttpSession session) { 
     try {
    	 
         LoginMemberService loginMemberService=new LoginMemberService();
 		 MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
 		 Long user_id=memberDto.getUser_id();
 		 

         OrdersDto ordersDto = new OrdersDto();
         ordersDto.setUser_id(user_id);
         ordersDto.setOrder_totalprice(orderFlatDto.getOp_totalprice()); 
         
         // 2. 주문 데이터 저장
         iDao.order_write(ordersDto); 
         
         // 3. 생성된 주문 ID 가져오기
         Long newOrderId = ordersDto.getOrder_id(); 
 		
         
         // 4. 주문 상품 저장
         String orderType = (String) session.getAttribute("orderType");
         if ("direct".equals(orderType)) {
             // 바로 주문인 경우
             Orders_productsDto orderProduct = new Orders_productsDto();
             orderProduct.setOrder_id(newOrderId);
             orderProduct.setProduct_id(orderFlatDto.getProduct_id());
             orderProduct.setOp_quantity(orderFlatDto.getOp_quantity());
             orderProduct.setOp_price(orderFlatDto.getOp_price());
             orderProduct.setOp_totalprice(orderFlatDto.getOp_totalprice());
             
             iDao.orders_products_write(orderProduct);
             
         } else {
             // 장바구니 주문인 경우
             ArrayList<CartFlatDto> cartItems = iDao.cart_list_flat(user_id);
             
             for (CartFlatDto cartItem : cartItems) {
                 Orders_productsDto orderProduct = new Orders_productsDto();
                 orderProduct.setOrder_id(newOrderId);
                 orderProduct.setProduct_id(cartItem.getProduct_id());
                 orderProduct.setOp_quantity(cartItem.getCart_quantity());
                 orderProduct.setOp_price(cartItem.getDiscountedPrice());
                 orderProduct.setOp_totalprice(cartItem.getTotalPrice());
                 
                 iDao.orders_products_write(orderProduct);

                 
             }
             iDao.cart_clear(user_id);
             // 장바구니 비우기
         }
         
         // 3. payment 테이블에 결제 정보 저장
         PaymentDto payment = new PaymentDto();
         payment.setOrder_id(newOrderId);
         payment.setPayment_type("카카오페이");
         payment.setPayment_status("결제완료");
         payment.setPayment_price(approvalResponse.getAmount().getTotal());
         payment.setPayment_tid(Long.parseLong(approvalResponse.getTid()));
         
         iDao.payment_write(payment);
         // 4. deliver 테이블에 배송 정보 저장
         DeliverDto deliver = new DeliverDto();
         deliver.setOrder_id(newOrderId);
         deliver.setDeliver_name(orderFlatDto.getDeliver_name());
         deliver.setDeliver_person(orderFlatDto.getDeliver_person());
         deliver.setDeliver_recipient_phone(orderFlatDto.getDeliver_recipient_phone());
         deliver.setDeliver_loc(orderFlatDto.getDeliver_loc());
         deliver.setDeliver_detail_loc(orderFlatDto.getDeliver_detail_loc());
         deliver.setDeliver_request(orderFlatDto.getDeliver_request());
         deliver.setDeliver_status("배송준비중");
         
         iDao.deliver_write(deliver);
         System.out.println("주문 및 결제 정보 DB 저장 완료. 주문번호: " + newOrderId);
         
         
         try {
        	 //세션 정리
        	 session.removeAttribute("kakaotid");
             session.removeAttribute("kpay_orderid");
             session.removeAttribute("orderFlatDto");
             session.removeAttribute("orderType");
             session.removeAttribute("userId");
         }catch(Exception e) {
        	 e.printStackTrace();
         }
         
     } catch (Exception e) {
         System.err.println("DB 저장 실패: " + e.getMessage());
         e.printStackTrace();
         throw new RuntimeException("주문 정보 저장 중 오류가 발생했습니다.");
     }
 }
}