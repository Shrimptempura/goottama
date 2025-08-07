package com.ama.don.shop.service.productinquiry;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductReplyWriteService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductReplyWriteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		//
		String pinquiry_id=request.getParameter("pinquiry_id");
		
		String preply_content=request.getParameter("preply_content");
		
		if(pinquiry_id==null || pinquiry_id.trim().isEmpty()) {
			
			System.out.println("pinquiry_id가 null입니다.");
		}
		
		
		
		Long pinquiryid=Long.parseLong(pinquiry_id);
		
		
		//답글 존재 여부 확인
	 	ArrayList<ShopProductInquiryFlatDto> inquiryFlatDtos=iDao.product_reply(pinquiryid);
		
		
		if(inquiryFlatDtos==null || inquiryFlatDtos.isEmpty()) {
			try {
				iDao.product_reply_write(pinquiryid,preply_content);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!inquiryFlatDtos.isEmpty() ) {
			
			System.out.println("답글 작성 안됨");
		}
		
			
	}

}
