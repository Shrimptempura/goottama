package com.ama.don.shop.service.orderservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;

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
		
		
		// 폼에서 전송된 데이터 받기
        String order_id = request.getParameter("order_id");
        String deliver_person = request.getParameter("deliver_person");
        String deliver_recipient_phone = request.getParameter("deliver_recipient_phone");
        String deliver_loc = request.getParameter("deliver_loc");
        String deliver_detail_loc = request.getParameter("deliver_detail_loc");
        

		
		String product_id=request.getParameter("product_id");
		String user_id=request.getParameter("user_id");
		
		
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
            // 1. 주문 배송지 업데이트
            iDao.deliver_update(orderid, deliver_person, deliver_recipient_phone, 
                               deliver_loc, deliver_detail_loc);
            System.out.println("success: 배송지 수정 완료");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		
		
	}

}
