package com.ama.don.shop.service.cart;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopCartWriteService implements ShopServiceinter {

	private ShopIDao iDao;
	
	public ShopCartWriteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		System.out.println("cartwrite");
		
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
	
		String product_id=request.getParameter("product_id");
		String cart_quantity=request.getParameter("cart_quantity");
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		Long userid=memberDto.getUser_id();
	
		
		
		iDao.cart_write(userid,Long.parseLong(product_id),Integer.parseInt(cart_quantity));
		
	}

}
