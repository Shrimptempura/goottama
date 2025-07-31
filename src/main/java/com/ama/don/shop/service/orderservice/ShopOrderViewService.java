package com.ama.don.shop.service.orderservice;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopOrderViewService implements ShopServiceinter{
	
	//주문 뷰

	private ShopIDao iDao;
	public ShopOrderViewService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String user_id=request.getParameter("user_id");
		String form_cart=request.getParameter("form_cart");
		
		System.out.println("user_id"+user_id);
		System.out.println("form_cart:"+form_cart);
			
	
		//null
        if (user_id == null || user_id.trim().isEmpty()) {
            System.out.println("ERROR: user_id가 null이거나 비어있음");
            model.addAttribute("error", "사용자 ID가 필요합니다.");
            model.addAttribute("cart", new ArrayList<CartFlatDto>());
            return;
        }
        
        if (form_cart == null || form_cart.trim().isEmpty()) {
            System.out.println("ERROR: form_cart가 null이거나 비어있음");
            model.addAttribute("error", "form_cart ID가 필요합니다.");
            
            return;
        }
        
        
		Long userid=Long.parseLong(user_id);
     
        
 
 
        if(form_cart.equals("true")) {
        	System.out.println("form_cart=ture");
    		ArrayList<CartFlatDto> cartFlatList = iDao.cart_list_flat(userid);
        
    		model.addAttribute("cart",cartFlatList);
        }
        if(form_cart.equals("false")) {
        	String product_id=request.getParameter("product_id");
        	Long productid=Long.parseLong(product_id);
        	
        	System.out.println("form-cart=false");
        	ProductFlatDto productflatDto=iDao.product(productid);
        	
        	//단일 상품을 가져오기
        
        	model.addAttribute("product",productflatDto);
        }
        
        //장바구니에서 들어가면 장바구니 카트
        //상품이면 상품 곧이곧대로
        
        
		
			
		//
	}

}
