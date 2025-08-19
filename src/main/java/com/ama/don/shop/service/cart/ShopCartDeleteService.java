package com.ama.don.shop.service.cart;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopCartDeleteService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopCartDeleteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		
		
		//
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		
		
		Long userid=memberDto.getUser_id();
		
		Long productid=Long.parseLong(product_id);
		//cartdeleteiDao;
		
		try {
			
			iDao.cart_delete_item(userid, productid);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			

		System.out.println("장바구니 상품 삭제 완료: user_id=" + userid + ", product_id=" + productid);
        model.addAttribute("message", "상품이 삭제되었습니다.");
		
	
		
	}

}
