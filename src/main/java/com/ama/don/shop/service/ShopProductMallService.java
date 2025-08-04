package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductMallService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductMallService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		
		
		ArrayList<ProductFlatDto> productFlatDtos=iDao.product_list();
		//상품을 가져오고 상품 쇼핑몰별로 검색한다.
		
		model.addAttribute("product_list",productFlatDtos);
		
			
	}

}
