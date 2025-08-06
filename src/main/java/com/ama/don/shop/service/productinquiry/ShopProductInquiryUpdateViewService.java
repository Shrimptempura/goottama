package com.ama.don.shop.service.productinquiry;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductInquiryUpdateViewService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryUpdateViewService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		System.out.println("inquiryUpdate()");
		
		String pinquiry_id=request.getParameter("pinquiry_id");
		
		if(pinquiry_id==null || pinquiry_id.isEmpty()) {
			
			System.out.println("user_id가 null 입니다.");
		}
		

		Long pinquiryid=Long.parseLong(pinquiry_id);
		
		
		try {
			
			//
			ShopProductInquiryFlatDto inquiryFlatDto=iDao.product_inquiry(pinquiryid);
			model.addAttribute("product_inquiry",inquiryFlatDto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
