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
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ShopProductLikeService implements ShopServiceinter{
	

    private ShopIDao iDao;;
    public ShopProductLikeService(ShopIDao iDao) {
        this.iDao=iDao;
    }
    
    @Override
    public void execute(Model model) {
        
        Map<String, Object> map=model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        
        //
        LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		Long userid=memberDto.getUser_id();  
        
        //여기에 pl_ istrue를 가져올 것
		
		String pl_istrue=request.getParameter("request");
		String product_id=request.getParameter("product_id");
		
		Long productid=Long.parseLong(product_id);
        
        
      	//null cheak
      	if(pl_istrue==null || pl_istrue.isEmpty()) {
      		
      		System.out.println("pl_isture가 null 입니다.");
      	}
      	
      	
		/*
		 * //상품 찜
		 * 
		 * //삼품의 찜 여부를 가져와야함 Integer
		 * likeStatus=iDao,product_like_status(userid,productid);
		 * 
		 * if(likeStatus==null) { //상품 _like작성하기
		 * iDao.product_like_write(product_id,pl_istrue,userid);
		 * 
		 * } else { iDao.product_like_update(product_id,pl_iste,userid); }
		 * 
		 * 
		 * 
		 */
      	
      	
    }
}
