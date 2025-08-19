package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopBestService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopBestService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		try {
		
			//상품 중 리뷰 내역이 많은 상품을 리스트로 조회
			ArrayList<ProductFlatDto> productFlatDtos=iDao.product_best_list();
			
			model.addAttribute("product_list",productFlatDtos);
			
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
		
			
	}

}
