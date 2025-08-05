package com.ama.don.shop.service.productinquiry;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductInquiryWriteViewService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryWriteViewService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		
		String user_id=request.getParameter("user_id");

		String product_id=request.getParameter("product_id");
		
		
		if(user_id==null || user_id.isEmpty()) {
			System.out.println("user_id가 null 값입니다.");
		}
		
		if(product_id==null || product_id.isEmpty()) {
			System.out.println("product_id가 null 값입니다.");
		}
		
		
		Long userid=Long.parseLong(user_id);
		
		Long productid=Long.parseLong(product_id);
		
		try{
			
			//상품 아이디로 단일 상품을 조회(상품과 유저정보를 )
			ProductFlatDto productFlatDto=iDao.product(productid);
			
			model.addAttribute("product",productFlatDto);
			model.addAttribute("userinfo",iDao.user_info(userid));
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
