package com.ama.don.shop.service.orderservice;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
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
		
		
		//장바구니 주문인지 , 바로구매 주문인지 true,false
		String form_cart=request.getParameter("form_cart");
		
		System.out.println("form_cart:"+form_cart);
			
	
        if (form_cart == null || form_cart.trim().isEmpty()) {
            System.out.println("ERROR: form_cart가 null이거나 비어있음");
            model.addAttribute("error", "form_cart ID가 필요합니다.");
            
            return;
        }
        
        LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		Long userid=memberDto.getUser_id();
     
        
 
 
        if(form_cart.equals("true")) {
        	System.out.println("form_cart=ture");
    		ArrayList<CartFlatDto> cartFlatList = iDao.cart_list_flat(userid);
        
    		model.addAttribute("cart",cartFlatList);
        }
        if(form_cart.equals("false")) {
        	String product_id=request.getParameter("product_id");
        	String quantitystr = request.getParameter("quantity");
        	 
        	 
        	 
        	Long productid=Long.parseLong(product_id);
        	
      
        	
        	//
        	int quantity = Integer.parseInt(quantitystr);
        	
        	ProductFlatDto productflatDto=iDao.product(productid);
        	
        	
        	productflatDto.setQuantity(quantity);
        	
        	//단일 상품을 가져오기
        
        	model.addAttribute("product",productflatDto);
        }
        

	}

}
