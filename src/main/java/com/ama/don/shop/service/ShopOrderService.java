package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.Product_imgDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopOrderService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopOrderService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		String user_id=request.getParameter("user_id");
		
		
		// user_id null 체크 및 안전한 변환
        if (user_id == null || user_id.trim().isEmpty()) {
            System.out.println("ERROR: user_id가 null이거나 비어있음");
            model.addAttribute("error", "사용자 ID가 필요합니다.");
            model.addAttribute("cart", new ArrayList<CartFlatDto>());
            return;
        }
		
		Long userid=Long.parseLong(user_id);
		
		System.out.println(user_id);
		ArrayList<CartFlatDto> cartFlatList = iDao.cart_list_flat(userid);
		/* ArrayList<CartDto> cartList=iDao.cart_list(user_id); */
			
		model.addAttribute("cart",cartFlatList);
		//
	}

}
