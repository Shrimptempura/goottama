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
import com.ama.don.shop.dto.Product_imgDto;
import com.ama.don.shop.dto.ShopReviewFlatDto;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.paymentService.KakaoPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ShopProductLikeService implements ShopServiceinter {

	private ShopIDao iDao;

	public ShopProductLikeService(ShopIDao iDao) {
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

		// 여기에 pl_ istrue를 가져올 것

		String product_id = request.getParameter("product_id");
		String plike_islike = request.getParameter("plike_islike");

		Long productid = Long.parseLong(product_id);

		// null cheak
		if (plike_islike == null || plike_islike.isEmpty()) {

			System.out.println("pl_isture가 null 입니다.");
		}

		// 🔍 1단계: 현재 좋아요 상태 확인
		String currentStatus = iDao.product_like_status(userid, productid);
		
		String likestatus=iDao.product_like_status(userid, productid);
		System.out.println("현재 DB 상태: " + currentStatus);
		

        String newStatus;

		// 🔄 2단계: 상태에 따라 토글 처리
     
        //좋아요 상태->싫어요
        if("Y".equals(currentStatus)) {
        	newStatus = "N";
        	iDao.product_like_update(userid, productid, newStatus);
        	System.out.println("상품을 찜취소했습니다.");
        }
        //싫어요 상태->좋아요
        else if("N".equals(currentStatus)) {
        	newStatus = "Y";
        	iDao.product_like_update(userid, productid, newStatus);
        	System.out.println("상품을 찜등록 했습니다.");
        }
        //둘 다 아님 , 기본 싫어요 상태이므로 좋아요상태컬럼을 최초 생성
        else {
        	newStatus = "Y";
        	iDao.product_like_write(userid, productid, newStatus);
        	System.out.println("상품을 찜등록 했습니다.");
        }
        
        model.addAttribute("userLikeStatus", newStatus);
        
		/*
		 * //만약 좋아요한 상품이 존재한다면 if ("Y".equals(currentStatus)) { // 존재함 → N으로 업데이트
		 * 
		 * iDao.product_like_update(); iDao.product_like_update(userId, productId,
		 * newStatus);
		 * 
		 * newStatus = "N"; System.out.println("🔴 좋아요 삭제 처리");
		 * iDao.product_like_delete(userid, productid);
		 * System.out.println("상품을 찜 목록에서 제거했습니다."); newStatus = "N";
		 * model.addAttribute("userLikeStatus", "N");
		 * 
		 * 
		 * //만약 좋아요한 상품이 존재하지 않는다면 //생성한다. } else { // 존재하지 않음 → 생성
		 * System.out.println("🔵 좋아요 생성 처리"); iDao.product_like_write(userid,
		 * productid, "Y"); System.out.println("상품을 찜 목록에 추가했습니다.");
		 * model.addAttribute("userLikeStatus", "Y"); }
		 */

	}
}
