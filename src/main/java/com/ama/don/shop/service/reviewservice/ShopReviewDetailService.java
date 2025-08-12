package com.ama.don.shop.service.reviewservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.ama.don.common.dto.ReviewDto;
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

public class ShopReviewDetailService implements ShopServiceinter{
	

    private ShopIDao iDao;;
    public ShopReviewDetailService(ShopIDao iDao) {
        this.iDao=iDao;
    }
    
    @Override
    public void execute(Model model) {
        
        Map<String, Object> map=model.asMap();
        MultipartHttpServletRequest request=
				(MultipartHttpServletRequest) map.get("request");
		
        
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
			
		Long userid=memberDto.getUser_id();
		


		
		//사용자의 상품 리뷰 리스트			
		ArrayList<ShopReviewFlatDto> shopReviewFlatDtos=iDao.review_by_userid(userid);
		
		model.addAttribute("review",shopReviewFlatDtos);
		
    }
}
