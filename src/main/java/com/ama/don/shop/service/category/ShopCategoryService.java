package com.ama.don.shop.service.category;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopCategoryService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopCategoryService(ShopIDao iDao) {
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
		//
		
		// 카테고리 ID 파라미터 받기
        String category_id = request.getParameter("category_id");
        
        // null 체크 및 기본값 설정
        if (category_id == null || category_id.trim().isEmpty()) {
            category_id = "1"; // 기본 카테고리 (가구)
            System.out.println("카테고리 ID가 없어서 기본값 1로 설정");
        } 
		
        Long categoryid=Long.parseLong(category_id);
        
        
		try{
			
			//카테고리별 상품 리스트 카테고리 번호로 데이터 셀렉트
			//받은 결과를 다시 jsp로
			
			//카테고리별 상품 리스트
			ArrayList<ProductFlatDto> categoryproducts=iDao.product_category(categoryid);
			
			model.addAttribute("list",categoryproducts);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		//iDao.intocart(product_id);
//		model.addAttribute("list",iDao.product_list());
		

	}

}
