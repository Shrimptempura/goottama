package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.IDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.Product_imgDto;

import jakarta.servlet.http.HttpServletRequest;

public class SCartService implements SServiceinter{

	private IDao iDao;
	public SCartService(IDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		String user_id=request.getParameter("user_id");
		
		
//		iDao.intocart(user_id,product_id);
////		CartDto cartDto=iDao.selcart(user_id);	//이거 ArrayList네 상품이 여러개나오니가
//		ArrayList<CartDto> cartList=iDao.selcart(user_id);
//		
//		model.addAttribute("cart",cartList);
//		
		//insert 이후는 db검새갷서 가져오기 sel이 잇어야하네
		
		//insert 이후 곧바로 db검색해서 sel로 
		
		
		
	}

}
