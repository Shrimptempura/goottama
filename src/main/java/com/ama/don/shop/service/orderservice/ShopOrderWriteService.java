package com.ama.don.shop.service.orderservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopOrderWriteService implements ShopServiceinter {
    
    private ShopIDao iDao;
    private String paymentmethod;
    
    public ShopOrderWriteService(ShopIDao iDao, String paymentmethod) {
        this.iDao = iDao;
        this.paymentmethod = paymentmethod;
    }
    
    @Override
    public void execute(Model model) {
        
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        
        try {
            // 기본 정보 추출
            String orderType = request.getParameter("orderType");
            
            LoginMemberService loginMemberService=new LoginMemberService();
    		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
    		model.addAttribute("loginMember",memberDto);
    		
    		Long userid=memberDto.getUser_id();
    		
            System.out.println("=== 주문 처리 시작 ===");
            System.out.println("유저 아이디: " + userid);
            System.out.println("주문 타입: " + orderType);
            System.out.println("결제수단: " + paymentmethod);
            
            // 주문 처리 결과 변수들
            int total = 0;
            List<Orders_productsDto> orderProducts = new ArrayList<>();
            boolean shouldClearCart = false;
            
            
            // 주문에 상품을 검색하는데 (장바구니,상품 상세)에서 가져와야한다.
            if ("direct".equals(orderType)) {
                // === 바로 주문하기 ===
                System.out.println("=== 바로 주문하기 처리 ===");
                
                String product_id = request.getParameter("product_id");
                String quantity = request.getParameter("quantity");
                String totalAmount = request.getParameter("totalAmount");
                
                if (product_id == null || quantity == null || totalAmount == null) {
                    throw new IllegalArgumentException("바로 주문에 필요한 파라미터가 누락되었습니다.");
                }
                
                Long productId = Long.parseLong(product_id);
                int qty = Integer.parseInt(quantity);
                total = Integer.parseInt(totalAmount);
                
                // 상품 정보 조회 (검증용)
                ProductFlatDto product = iDao.product(productId);
                if (product == null) {
                    throw new IllegalArgumentException("존재하지 않는 상품입니다.");
                }
                
                // 주문 상품 정보 생성
                Orders_productsDto orderProduct = new Orders_productsDto();
                orderProduct.setProduct_id(productId);
                orderProduct.setOp_quantity(qty);                           // ✅ 사용자 선택 수량
                orderProduct.setOp_price(product.getDiscountedPrice());     // ✅ 할인된 단가
                orderProduct.setOp_totalprice(total);                       // ✅ 실제 주문 총액
                
                orderProducts.add(orderProduct);
                shouldClearCart = false; // 바로 주문은 장바구니 클리어 안함
                
                System.out.println("바로 주문 - 상품ID: " + productId + ", 수량: " + qty + ", 총액: " + total);
                
            } else {
                // === 장바구니 주문 ===
                System.out.println("=== 장바구니 주문 처리 ===");
                
                ArrayList<CartFlatDto> cartFlatDtos = iDao.cart_list_flat(userid);
                
                if (cartFlatDtos == null || cartFlatDtos.isEmpty()) {
                    throw new IllegalArgumentException("장바구니가 비어있습니다.");
                }
                
                total = 0; // 초기화
                for (CartFlatDto cartitem : cartFlatDtos) {
                    total += cartitem.getTotalPrice();
                    
                    Orders_productsDto orderProduct = new Orders_productsDto();
                    orderProduct.setProduct_id(cartitem.getProduct_id());
                    orderProduct.setOp_quantity(cartitem.getCart_quantity());     // ✅ 장바구니 수량
                    orderProduct.setOp_price(cartitem.getDiscountedPrice());      // ✅ 할인된 단가
                    orderProduct.setOp_totalprice(cartitem.getTotalPrice());      // ✅ 아이템별 총액
                    
                    orderProducts.add(orderProduct);
                }
                
                shouldClearCart = true; // 장바구니 주문은 클리어함
                
                System.out.println("장바구니 주문 - 상품 개수: " + orderProducts.size() + ", 총액: " + total);
            }
            
            // === 여기서부터는 공통 처리 (중복 로직 제거!) ===
            
            // 1. 주문 등록
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setUser_id(userid);
            ordersDto.setOrder_totalprice(total);
            iDao.order_write(ordersDto);
            
            long order_id = ordersDto.getOrder_id();
            
            System.out.println("=== 주문 등록 완료 ===");
            System.out.println("생성된 order_id: " + order_id);
            System.out.println("user_id: " + userid);
            System.out.println("total: " + total);
            
            // 2. 배송 정보 등록
            try {
                DeliverDto deliverDto = new DeliverDto();
                deliverDto.setOrder_id(order_id);
                deliverDto.setDeliver_name(request.getParameter("order_deliver_name"));
                deliverDto.setDeliver_person(request.getParameter("order_receiver_name"));
                deliverDto.setDeliver_recipient_phone(request.getParameter("order_receiver_tel"));
                deliverDto.setDeliver_loc(request.getParameter("order_loc"));
                deliverDto.setDeliver_detail_loc(request.getParameter("order_detailloc"));
                deliverDto.setDeliver_request(request.getParameter("order_request"));
                
                iDao.deliver_write(deliverDto);
                System.out.println("배송 정보 등록 완료");
                
            } catch (Exception e) {
                System.err.println("배송 정보 등록 실패: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("배송 정보 등록에 실패했습니다.", e);
            }
            
            // 3. 주문 상품들 등록
            try {
                for (Orders_productsDto product : orderProducts) {
                    Orders_productsDto orders_productsDto = new Orders_productsDto();
                    orders_productsDto.setOrder_id(order_id);
                    orders_productsDto.setProduct_id(product.getProduct_id());
                    orders_productsDto.setOp_quantity(product.getOp_quantity());
                    orders_productsDto.setOp_price(product.getOp_price());
                    orders_productsDto.setOp_totalprice(product.getOp_totalprice());
                    
                    iDao.orders_products_write(orders_productsDto);
                    
                    System.out.println("주문 상품 등록: 상품ID=" + product.getProduct_id() + 
                                     ", 수량=" + product.getOp_quantity() + 
                                     ", 단가=" + product.getOp_price() + 
                                     ", 총액=" + product.getOp_totalprice());
                }
                
                System.out.println("모든 주문 상품 등록 완료");
                
            } catch (Exception e) {
                System.err.println("주문 상품 등록 실패: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("주문 상품 등록에 실패했습니다.", e);
            }
            
            // 4. 결제 정보 등록
            try {
                PaymentDto paymentDto = new PaymentDto();
                paymentDto.setOrder_id(order_id);
                paymentDto.setPayment_price(total);
                // paymentDto.setPayment_method(paymentmethod); // PaymentDto에 method 필드가 있다면
                
                iDao.payment_write(paymentDto);
                System.out.println("결제 정보 등록 완료");
                
            } catch (Exception e) {
                System.err.println("결제 정보 등록 실패: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("결제 정보 등록에 실패했습니다.", e);
            }
            
            // 5. 조건부 장바구니 클리어
            if (shouldClearCart) {
                try {
                    iDao.cart_clear(userid);
                    System.out.println("장바구니 클리어 완료");
                } catch (Exception e) {
                    System.err.println("장바구니 클리어 실패: " + e.getMessage());
                    e.printStackTrace();
                    // 장바구니 클리어 실패는 주문 전체를 실패시키지 않음
                }
            } else {
                System.out.println("바로 주문이므로 장바구니 클리어 스킵");
            }
            
            // 6. 주문 완료 후 조회
            try {
                OrderFlatDto orderInfo = iDao.order_detail_flat(order_id);
                ArrayList<OrderFlatDto> orderProductsList = iDao.order_products_flat(order_id);
                
                System.out.println("주문 완료 정보 조회 성공");
                System.out.println("주문 ID: " + order_id);
                System.out.println("주문 정보: " + (orderInfo != null ? "조회됨" : "null"));
                System.out.println("주문 상품 개수: " + (orderProductsList != null ? orderProductsList.size() : 0));
                
                // JSP로 전달
                model.addAttribute("orderInfo", orderInfo);
                model.addAttribute("orderProducts", orderProductsList);
                model.addAttribute("order_id", order_id);
                model.addAttribute("total_price", total);
                model.addAttribute("message", "주문이 성공적으로 완료되었습니다!");
                
            } catch (Exception e) {
                System.err.println("주문 완료 정보 조회 실패: " + e.getMessage());
                e.printStackTrace();
                
                // 조회 실패해도 주문은 완료된 상태이므로 기본 정보는 전달
                model.addAttribute("order_id", order_id);
                model.addAttribute("total_price", total);
                model.addAttribute("message", "주문 완료 (상세 정보 조회 실패)");
                model.addAttribute("error", "주문 정보 조회 중 오류가 발생했습니다.");
            }
            
        } catch (Exception e) {
            System.err.println("주문 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "주문 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}