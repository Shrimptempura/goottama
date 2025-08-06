package com.ama.don.shop.service.productinquiry;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductInquiryUpdateService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryUpdateService(ShopIDao iDao) {
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
		String pinquiry_content=request.getParameter("pinquiry_content");
		
		
		if(pinquiry_id==null || pinquiry_id.isEmpty()) {
			
			System.out.println("pinquiry_id가 null 입니다.");
		}
		

		Long pinquiryid=Long.parseLong(pinquiry_id);
		
		
		try {
			
			//
			iDao.product_inquiry_update(pinquiryid,pinquiry_content);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
