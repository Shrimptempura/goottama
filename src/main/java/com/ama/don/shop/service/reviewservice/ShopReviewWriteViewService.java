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

public class ShopReviewWriteViewService implements ShopServiceinter{
	

    private ShopIDao iDao;;
    public ShopReviewWriteViewService(ShopIDao iDao) {
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
		
        String product_id=request.getParameter("product_id");
        
        
      
        //null cheak
      	if(product_id==null) {
      		System.out.println("product_id가 null 입니다.");
      	}
      		
      	
    	Long productid=Long.parseLong(product_id);

		
		// 사용자 아이디를 가지고 사용자 닉네임을 가져와서 사용한다.
		ShopReviewFlatDto reviewFlatDto=iDao.user_info(userid);
  
        model.addAttribute("user_info",reviewFlatDto);
        model.addAttribute("product",iDao.product(productid));
        
    }
}
