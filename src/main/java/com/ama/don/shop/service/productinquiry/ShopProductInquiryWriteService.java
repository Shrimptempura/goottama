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

public class ShopProductInquiryWriteService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductInquiryWriteService(ShopIDao iDao) {
		this.iDao=iDao;
	}
	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map=model.asMap();
		HttpServletRequest request=
				(HttpServletRequest) map.get("request");
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		
		Long userid=memberDto.getUser_id();
		
		String product_id=request.getParameter("product_id");
		
		String pinquiry_content=request.getParameter("pinquiry_content");
		
		if(product_id==null || product_id.isEmpty()) {
			System.out.println("product_id가 null 값입니다.");
		}
		
		if(pinquiry_content==null || pinquiry_content.isEmpty()) {
			System.out.println("pinquiry_content가 null 값입니다.");
		}
		
		Long productid=Long.parseLong(product_id);
		
		try{
			
			//상품 문의를 등록
			iDao.product_inquiry_write(userid,productid,pinquiry_content);
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
