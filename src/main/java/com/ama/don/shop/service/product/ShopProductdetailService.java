package com.ama.don.shop.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.ProductDto;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.dto.ShopProductInquiryFlatDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;
import com.ama.don.shop.service.ShopServiceinter;

import jakarta.servlet.http.HttpServletRequest;

public class ShopProductdetailService implements ShopServiceinter{

	private ShopIDao iDao;
	public ShopProductdetailService(ShopIDao iDao) {
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
		
		//String product_id=request.getParameter("product_id");
		String product_id=request.getParameter("product_id");
		Long userid=memberDto.getUser_id();
		String userLikeStatus = "N"; // 기본값
		System.out.println(product_id);
		System.out.println(userid);
		//iDao.product(product_id);
		
		//null cheak
		if(product_id==null) {
			System.out.println("product_id가 null 입니다.");
		}
		
		Long productid=Long.parseLong(product_id);
		Long targetid=Long.parseLong(product_id);
		
		
		try {
			// 1. 상품 정보 
			ProductFlatDto productFlatDto=iDao.product(productid);
			
			// 2. 상품 이미지
			ArrayList<ProductFlatDto> productimgs=iDao.productimgs(productid);
			
			// 3. 리뷰 정보 (상품아이디가 타겟아이디)
			ArrayList<ShopReviewFlatDto> reviewFlatDtos=iDao.review_list(targetid);
			
			// 4. 문의 정보
			ArrayList<ShopProductInquiryFlatDto> inquiryFlatDtos=iDao.product_inquiry_list(productid);			
			
			// 5. DB에서 현재 좋아요 상태 조회
	        String currentStatus = iDao.product_like_status(userid, productid);
	        
	        System.out.println("currentStatus:"+currentStatus);
			
			
	        // 있으면 Y, 없으면 N
            userLikeStatus = ("Y".equals(currentStatus)) ? "Y" : "N";
			
			// 5. 사용자의 권한 확인 (관리자면 문의에 답글 가능, )
				
			model.addAttribute("product",productFlatDto);
			model.addAttribute("productimgs",productimgs);
			model.addAttribute("review_list",reviewFlatDtos);
			model.addAttribute("product_inquiry_list",inquiryFlatDtos);
			model.addAttribute("userLikeStatus", userLikeStatus);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		//
	}

}
