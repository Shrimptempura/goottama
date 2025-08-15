package com.ama.don.shop.service.productlike;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.CartDto;
import com.ama.don.shop.dto.CartFlatDto;
import com.ama.don.shop.dto.DeliverDto;
import com.ama.don.shop.dto.OrderFlatDto;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.Orders_productsDto;
import com.ama.don.shop.dto.PaymentDto;
import com.ama.don.shop.dto.PaymentResult;
import com.ama.don.shop.dto.ProductLikeDto;
import com.ama.don.shop.dto.ProductLikeFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ShopProductLikeDetailService implements ShopServiceinter {

	private ShopIDao iDao;

	public ShopProductLikeDetailService(ShopIDao iDao) {
		this.iDao = iDao;
	}

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		//
		LoginMemberService loginMemberService = new LoginMemberService();
		MemberDto memberDto = loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember", memberDto);

		Long userid = memberDto.getUser_id();


		//유저 아이디로 좋아요 조회
		ArrayList<ProductLikeFlatDto> productLikeFaltDtos=iDao.product_like_list(userid);
		
        model.addAttribute("product_like", productLikeFaltDtos);
        
		
	}
}
