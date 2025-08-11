package com.ama.don.shop.service.productinquiry;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductInquiryDeleteService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryDeleteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		System.out.println("inquirydelete()");
		
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		//
		
		Long userid=memberDto.getUser_id();
		
		String pinquiry_id=request.getParameter("pinquiry_id");
		
		
		if(pinquiry_id==null || pinquiry_id.isEmpty()) {
			
			System.out.println("user_id가 null 입니다.");
		}
		
		Long pinquiryid=Long.parseLong(pinquiry_id);
		
		
		try {
			if(!iDao.product_reply(pinquiryid).isEmpty()) {
				//1.문의 삭제를 하면 해당 문의의 답글을 지운다.
				iDao.product_reply_delete(pinquiryid);
			}
			
			
			//2.문의 삭제를 하면 삭제한다.
			iDao.product_inquiry_delete(userid,pinquiryid);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
