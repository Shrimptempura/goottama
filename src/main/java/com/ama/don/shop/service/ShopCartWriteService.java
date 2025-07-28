package com.ama.don.shop.service;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;

import jakarta.servlet.http.HttpServletRequest;

public class ShopCartWriteService implements ShopServiceinter {

	private ShopIDao siDao;
	
	public ShopCartWriteService(ShopIDao siDao) {
		this.siDao=siDao;
	}
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		System.out.println("cartwrite");
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String user_id=request.getParameter("user_id");
		String product_id=request.getParameter("product_id");
		String cart_quantity=request.getParameter("cart_quantity");
		
		
		siDao.cart_write(Long.parseLong(user_id),Long.parseLong(product_id),Long.parseLong(cart_quantity));
		
	}

}
