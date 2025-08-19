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
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopOrderModifyViewService implements ShopServiceinter{
	
	//주문 수정 뷰
	

    private ShopIDao iDao;
    public ShopOrderModifyViewService(ShopIDao iDao) {
        this.iDao=iDao;
    }
    
    @Override
    public void execute(Model model) {
        
        Map<String, Object> map=model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        
        
        
        String order_id=request.getParameter("order_id");
        Long orderid=Long.parseLong(order_id);
        
        
       //order_id를 바탕으로 주문을 검색해서 주문을 수정하기
       try {
    	   
    	   //주문 기본정보, 주문 상세정보
    	   OrderFlatDto orderDetail=iDao.order_detail_flat(orderid);
    	   
    	   //주문 상품 목록
    	   ArrayList<OrderFlatDto> orderProducts= iDao.order_products_flat(orderid);
    	  
    	   
    	   model.addAttribute("orderDetail",orderDetail);
    	   model.addAttribute("orderProducts",orderProducts);
    	   model.addAttribute("orderId", orderid);
    	   
    	   
    	 
    	   if (orderDetail != null) {
               System.out.println("✅ 주문 정보 조회 성공!");
               System.out.println("   - 주문 ID: " + orderDetail.getOrder_id());
               System.out.println("   - 사용자 ID: " + orderDetail.getUser_id());
               System.out.println("   - 총 금액: " + orderDetail.getOrder_totalprice());
           } else {
               System.out.println("❌ 주문 정보가 null! DB에 데이터가 없을 수 있음");
           }

    	    if (order_id == null || order_id.trim().isEmpty()) {
    	        System.out.println("❌ order_id가 null이거나 비어있음!");
    	        model.addAttribute("error", "주문 ID가 필요합니다.");
    	        return;
    	    }

    	   
       }catch (Exception e) {
    	   e.printStackTrace();
       }  
        
    }
}
