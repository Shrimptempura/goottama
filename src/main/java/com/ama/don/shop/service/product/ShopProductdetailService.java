package com.ama.don.shop.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductdetailService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductdetailService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		//String product_id=request.getParameter("product_id");
		String product_id=request.getParameter("product_id");
		String user_id=request.getParameter("user_id");
		System.out.println(product_id);
		System.out.println(user_id);
		//iDao.product(product_id);
		
		
		
		//null cheak
		if(product_id==null) {
			System.out.println("product_id가 null 입니다.");
		}
		
		//null cheak
		if(user_id==null) {
			System.out.println("user_id가 null 입니다.");
		}
		
		
		Long productid=Long.parseLong(product_id);
		Long targetid=Long.parseLong(product_id);
		
		
		try {
			// 1. 상품 정보 
			ProductFlatDto productFlatDto=iDao.product(productid);
			
			// 2. 상품 이미지
			ArrayList<ProductFlatDto> productimgs=iDao.productimgs(productid);
			
			// 3. 리뷰 정보 (상품아이디가 타겟아이디)
			ArrayList<ShopReviewFlatDto> reviewFlatDtos=iDao.review_list(targetid);
			
			// 4. 문의 정보
			
			
			
			model.addAttribute("product",productFlatDto);
			model.addAttribute("productimgs",productimgs);
			model.addAttribute("review_list",reviewFlatDtos);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
