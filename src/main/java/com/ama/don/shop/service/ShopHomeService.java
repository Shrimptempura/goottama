package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopHomeService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopHomeService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		
		try{
			//주문내역이 많은상품
			ArrayList<ProductFlatDto> product_popular=iDao.product_popular_flat_list();
			//세일비율이 높은상품 
			ArrayList<ProductFlatDto> product_highsales=iDao.product_high_sales_flat_list();
			
			model.addAttribute("popularProducts", product_popular);     // JSP: ${popularProducts}
			model.addAttribute("saleProducts", product_highsales);      // JSP: ${saleProducts}
			
			
			// 디버깅용 로그
            System.out.println("인기 상품 개수: " + product_popular.size());
            System.out.println("세일 상품 개수: " + product_highsales.size());
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
