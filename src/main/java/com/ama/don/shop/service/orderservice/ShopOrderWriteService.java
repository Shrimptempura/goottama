package com.ama.don.shop.service.orderservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentDto;
import com.ama.don.shop.dto.PaymentResult;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ShopOrderWriteService implements ShopServiceinter{
	

    private ShopIDao iDao;
    private String paymentmethod;
    public ShopOrderWriteService(ShopIDao iDao,String paymentmethod) {
        this.iDao=iDao;
        this.paymentmethod=paymentmethod;
    }
    
    @Override
    public void execute(Model model) {
        
        Map<String, Object> map=model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        
        // 폼에서 내보낸 값들
        String user_id=request.getParameter("user_id");
        String order_name=request.getParameter("order_name");
        String order_email=request.getParameter("order_email");
        String order_phone=request.getParameter("order_phone");
        String order_deliver_name=request.getParameter("order_deliver_name");
        String order_receiver_name=request.getParameter("order_receiver_name");
        String order_receiver_tel=request.getParameter("order_receiver_tel");
        String order_loc=request.getParameter("order_loc");
        String order_detailloc=request.getParameter("order_detailloc");
        String order_request=request.getParameter("order_request");
        
        Long userid=Long.parseLong(user_id);    
        if (user_id != null && !user_id.trim().isEmpty()) {
            System.out.println("유저 아이디:"+user_id);
        } else {
            throw new IllegalArgumentException("유저 ID가 유효하지 않습니다.");
        }
        

    
        
        // 주문에 상품을 검색하는데 장바구니에서 가져와야한다.
        int total=0;
        ArrayList<CartFlatDto> cartFlatDtos=iDao.cart_list_flat(userid);
        for(CartFlatDto cartitem: cartFlatDtos) {
        	//주문 총가격
            total+=cartitem.getTotalPrice();
        }
        
        System.out.println("결재수단:"+paymentmethod);
       
        
        
        // ===== 1. 주문 등록 (한 번만!) =====
        OrdersDto ordersDto = new OrdersDto();
        ordersDto.setUser_id(userid);
        ordersDto.setOrder_totalprice(total);
        iDao.order_write(ordersDto);
        
        long order_id=ordersDto.getOrder_id();
        
        // 디버깅 출력
        System.out.println("=== 주문 등록 결과 ===");
        System.out.println("생성된 order_id: " + order_id);
        System.out.println("user_id: " + userid);
        System.out.println("total: " + total);
        
        try {
            // ===== 2. 배송 정보 등록 (한 번만!) =====
            DeliverDto deliverDto = new DeliverDto();
            deliverDto.setOrder_id(order_id);
            deliverDto.setDeliver_name(order_deliver_name);
            deliverDto.setDeliver_person(order_receiver_name);
            deliverDto.setDeliver_recipient_phone(order_receiver_tel);
            deliverDto.setDeliver_loc(order_loc);
            deliverDto.setDeliver_detail_loc(order_detailloc);
            deliverDto.setDeliver_request(order_request);
            iDao.deliver_write(deliverDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            // ===== 3. 주문 상품 등록 (상품별로 여러 번) =====
            for(CartFlatDto cartitem: cartFlatDtos) {
                Orders_productsDto orders_productsDto = new Orders_productsDto();
                orders_productsDto.setOrder_id(order_id);
                orders_productsDto.setProduct_id(cartitem.getProduct_id());
                orders_productsDto.setOp_quantity(cartitem.getCart_quantity());
                orders_productsDto.setOp_price(cartitem.getDiscountedPrice());
                orders_productsDto.setOp_totalprice(cartitem.getTotalPrice());
                
                iDao.orders_products_write(orders_productsDto);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            // ===== 4. 결제 정보 등록 (한 번만!) =====
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setOrder_id(order_id);
            paymentDto.setPayment_price(total);
            iDao.payment_write(paymentDto);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            // ===== 5. 장바구니 비우기 =====
            iDao.cart_clear(userid);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        try {
            // ===== 6. 주문 완료 후 조회 =====
            
            // 6-1. 주문 기본 정보 조회
            OrderFlatDto orderInfo = iDao.order_detail_flat(order_id);
            
            // 6-2. 주문 상품 목록 조회
            ArrayList<OrderFlatDto> orderProducts = iDao.order_products_flat(order_id);
            
            // 6-3. 상세 정보가 필요하면 배송/결제 정보도 조회
            // OrderFlatDto orderDetail = iDao.order_detail_flat(order_id);
            
            // 디버깅용 출력
            System.out.println("주문 ID: " + order_id);
            System.out.println("주문 정보: " + orderInfo);
            System.out.println("주문 상품 개수: " + orderProducts.size());
            
            // JSP로 전달
            model.addAttribute("orderInfo", orderInfo);
            model.addAttribute("orderProducts", orderProducts);
            model.addAttribute("order_id", order_id);
            model.addAttribute("total_price", total);
            model.addAttribute("message", "주문이 완료되었습니다.");
            
        } catch(Exception e) {
            e.printStackTrace();
            
            // 조회 실패해도 주문은 완료된 상태이므로 기본 정보는 전달
            model.addAttribute("order_id", order_id);
            model.addAttribute("total_price", total);
            model.addAttribute("message", "주문 완료 (상세 정보 조회 실패)");
            model.addAttribute("error", "주문 정보 조회 중 오류가 발생했습니다.");
        }
    }
    

}
