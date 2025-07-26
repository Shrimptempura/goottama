package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.Product_imgDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopCartService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopCartService(ShopIDao iDao) {
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
	
		
		
//		iDao.selectcart(user_id);
//		Array
//		
//		CartDto cartDto=
//		CartDto cartDto=iDao.select();
		
		
		
//		
		//ArrayList<CartDto> cartList=iDao.cart_list(user_id);
		
		// 새로운 FlatDto 방식
		ArrayList<CartFlatDto> cartFlatList = iDao.cart_list_flat(userid);
		
		model.addAttribute("cart",cartFlatList);
		//USER ID를기반으로 검색하고 나온 상품들을 상품아이디로 상품과 조인해서 상품을 가져오기
		//USER ID 를 기반으로 검색한다. 13개
		//13개 상품을 상품아이디로 검색한다. (가격,상품이름,상품_쇼핑_몰,갯수);
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
