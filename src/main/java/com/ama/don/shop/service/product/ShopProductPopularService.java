package com.ama.don.shop.service.product;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductPopularService implements ShopServiceinter{
	
	ShopIDao iDao;

	public ShopProductPopularService(ShopIDao iDao) {
		this.iDao=iDao;
	}	
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");

		//PorductFlatDto 리스트형식으로 사용한다.
		model.addAttribute("product_popular",iDao.product_popular_all_list());	

	}

}
