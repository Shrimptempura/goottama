package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.Product_imgDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopOrderUpdateSerivce implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopOrderUpdateSerivce(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		String user_id=request.getParameter("user_id");
		
		//주문수정하기 실제로는 배송지만 수정;
		String order_id=request.getParameter("order_id");
		
		
		// user_id null 체크 및 안전한 변환
        if (user_id == null || user_id.trim().isEmpty()) {
            System.out.println("ERROR: user_id가 null이거나 비어있음");
            model.addAttribute("error", "사용자 ID가 필요합니다.");
            model.addAttribute("cart", new ArrayList<CartFlatDto>());
            return;
        }
        
        // order_id null 체크 및 안전한 변환
        if (order_id == null || order_id.trim().isEmpty()) {
            System.out.println("ERROR: order_id가 null이거나 비어있음");
            model.addAttribute("error", "주문 ID가 필요합니다.");
            return;
        }
		
		Long userid=Long.parseLong(user_id);
		
		Long orderid=Long.parseLong(order_id);
		
		try {
			// 1. 주문 정보 조회 (배송지 정보 포함)
            OrderFlatDto orderDetail = iDao.order_detail_flat(orderid);
            
		} catch (Exception e) {
		   
		}
		
	}

}
