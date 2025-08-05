package com.ama.don.shop.service;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductReplyViewService implements ShopServiceinter {

	private ShopIDao iDao;
	
	public ShopProductReplyViewService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String,Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		//원글
		String pinquiry_id=request.getParameter("pinquiry_id");
		
		
		if(pinquiry_id==null || pinquiry_id.isEmpty()) {
			System.out.println("pinquiry_id가 null입니다.");
		}

		Long pinquiryid=Long.parseLong(pinquiry_id);
		
		try {
			//문의 답글 쓰기
			
			//상품 문의를 조회해야하나 조회해야됨 하나 상푸 문의를 조회 문의 조회하면 상품 아이디는 상관없음
			
			//단일 상품 특정 문의 조회
			ShopProductInquiryFlatDto inquiryFlatDto=iDao.product_inquiry(pinquiryid);
			
			model.addAttribute("product_inquiry",inquiryFlatDto);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
