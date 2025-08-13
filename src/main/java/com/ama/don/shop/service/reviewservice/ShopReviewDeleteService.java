package com.ama.don.shop.service.reviewservice;

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

public class ShopReviewDeleteService implements ShopServiceinter{
	

    private ShopIDao iDao;;
    public ShopReviewDeleteService(ShopIDao iDao) {
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
        
        String review_id=request.getParameter("review_id");

        
        
      	//null cheak
      	if(review_id==null) {
      		System.out.println("review_id가 null 입니다.");
      	}
    	
    	Long reviewid=Long.parseLong(review_id);
    	
    	System.out.println("리뷰 삭제");
    	
    	
    	//리뷰 삭제하기
    	iDao.review_delete(reviewid);
        
    }
}
