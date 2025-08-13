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

public class ShopProductInquiryDetailService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryDetailService(ShopIDao iDao) {
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
		
		//상품 문의 조회 리스트
		ArrayList<ShopProductInquiryFlatDto> shopProductInquiryFlatDtos=iDao.product_inquiry_by_userid(userid);
		
		for (ShopProductInquiryFlatDto inquiry : shopProductInquiryFlatDtos) {
			System.out.println("pinquiry content: "+inquiry.getPinquiry_content());
		}
		
		model.addAttribute("inquiry",shopProductInquiryFlatDtos);
		
		
	}

}
