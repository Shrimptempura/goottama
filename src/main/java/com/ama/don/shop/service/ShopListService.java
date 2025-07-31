package com.ama.don.shop.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductDto;
import com.ama.don.shop.dto.Product_imgDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopListService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopListService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	
	@Override
	@Transactional
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		
		//iDao.intocart(product_id);
		model.addAttribute("list",iDao.product_list());
		
		//
		
//		ArrayList<Product_imgDto> imgList=
//				iDao.selectImg(bid);
//		model.addAttribute("imgList",imgList);
	}

}
