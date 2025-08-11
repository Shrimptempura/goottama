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

public class ShopCartUpdateService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopCartUpdateService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		String product_id=request.getParameter("product_id");
		
		String cart_id=request.getParameter("cart_id");
		String cart_quantity=request.getParameter("cart_quantity");
		
		
		
		
		//
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		Long userid=memberDto.getUser_id();
		
		
		  // cart_id null 체크 및 안전한 변환
        if (cart_id == null || cart_id.trim().isEmpty()) {
            System.out.println("ERROR: cart_id가 null이거나 비어있음");
            model.addAttribute("error", "장바구니 ID가 필요합니다.");
            model.addAttribute("cart", new ArrayList<CartFlatDto>());
            return;
        }

        // cart_quantity null 체크 및 안전한 변환
        if (cart_quantity == null || cart_quantity.trim().isEmpty()) {
            System.out.println("ERROR: cart_quantity가 null이거나 비어있음");
            model.addAttribute("error", "수량이 필요합니다.");
            model.addAttribute("cart", new ArrayList<CartFlatDto>());
            return;
        }
		
        Long cartid = Long.parseLong(cart_id);
        
        // cart_quantity는 int로 변환
        int cartquantity = Integer.parseInt(cart_quantity);
        
        // 수량 유효성 검사
        if (cartquantity < 1) {
            System.out.println("ERROR: 수량은 1개 이상이어야 함");
            model.addAttribute("error", "수량은 1개 이상이어야 합니다.");
            return;
        }
        

		
		
		//카트아이디를 long으로바꾸고 
		//cart_qunatity는 int로 바꾸고
		//cartdeleteiDao;
		
		try {
			
			iDao.cart_update(cartid,cartquantity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			

		System.out.println("장바구니 수량 업데이트 완료: cart_id=" + cartid + ", quantity=" + cartquantity);
        model.addAttribute("message", "상품이 삭제되었습니다.");
		
	
		
	}

}
