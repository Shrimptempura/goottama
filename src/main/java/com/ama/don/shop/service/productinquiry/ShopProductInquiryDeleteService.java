package com.ama.don.shop.service.productinquiry;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductInquiryDeleteService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryDeleteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");

		
		//
		String user_id=request.getParameter("user_id");
		String pinquiry_id=request.getParameter("pinquiry_id");
		
		
		
		Long userid=Long.parseLong(user_id);
		Long pinquiryid=Long.parseLong(pinquiry_id);
		
		try {
			iDao.product_inquiry_delete(userid, pinquiryid);
			iDao.product_reply_delete(pinquiryid);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
