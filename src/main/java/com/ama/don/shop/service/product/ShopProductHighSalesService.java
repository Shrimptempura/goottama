package com.ama.don.shop.service.product;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductHighSalesService implements ShopServiceinter{
	
	ShopIDao iDao;

	public ShopProductHighSalesService(ShopIDao iDao) {
		this.iDao=iDao;
	}	
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");

		model.addAttribute("product_high_sales",iDao.product_high_sales_flat_list());

	}

}
