package com.ama.don.shop.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ama.don.interior.dev.DevFindTarget;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.KakaoPayReadyResponse;
import com.ama.don.shop.dto.OrdersDto;
import com.ama.don.shop.dto.PaymentRequest;
import com.ama.don.shop.dto.ProductFlatDto;
import com.ama.don.shop.service.ShopBestService;
import com.ama.don.shop.service.ShopHomeService;
import com.ama.don.shop.service.ShopProductMallService;
import com.ama.don.shop.service.ShopListService;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.ShopWriteService;
import com.ama.don.shop.service.Kakaopay.ShopKakaopayService;
import com.ama.don.shop.service.cart.ShopCartDeleteService;
import com.ama.don.shop.service.cart.ShopCartService;
import com.ama.don.shop.service.cart.ShopCartUpdateService;
import com.ama.don.shop.service.cart.ShopCartWriteService;
import com.ama.don.shop.service.category.ShopCategoryService;
import com.ama.don.shop.service.orderservice.ShopOrderDetailService;
import com.ama.don.shop.service.orderservice.ShopOrderModifyViewService;
import com.ama.don.shop.service.orderservice.ShopOrderUpdateSerivce;
import com.ama.don.shop.service.orderservice.ShopOrderViewService;
import com.ama.don.shop.service.orderservice.ShopOrderWriteService;
import com.ama.don.shop.service.product.ShopProductHighSalesService;
import com.ama.don.shop.service.product.ShopProductPopularService;
import com.ama.don.shop.service.product.ShopProductdetailService;
import com.ama.don.shop.service.productinquiry.ShopProductInquiryWriteService;
import com.ama.don.shop.service.productinquiry.ShopProductInquiryWriteViewService;
import com.ama.don.shop.service.productinquiry.ShopProductInquiryDeleteService;
import com.ama.don.shop.service.productinquiry.ShopProductInquiryUpdateService;
import com.ama.don.shop.service.productinquiry.ShopProductInquiryUpdateViewService;
import com.ama.don.shop.service.productinquiry.ShopProductReplyViewService;
import com.ama.don.shop.service.productinquiry.ShopProductReplyWriteService;
import com.ama.don.shop.service.reviewservice.ShopReviewDeleteService;
import com.ama.don.shop.service.reviewservice.ShopReviewUpdateService;
import com.ama.don.shop.service.reviewservice.ShopReviewUpdateViewService;
import com.ama.don.shop.service.reviewservice.ShopReviewWriteService;
import com.ama.don.shop.service.reviewservice.ShopReviewWriteViewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShopController {

	ShopServiceinter shopServiceinter;

	@Autowired
	private ShopIDao iDao;
	
	@Autowired
	private ShopKakaopayService shopKakaopayService;

	@RequestMapping("/shop/subheader")
	public String subheader(Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		
		return "shop/subheader";
	}

	//상품 홈
	@RequestMapping("/shop/home")
	public String home(HttpServletRequest request, Model model) {
		
		//지금 사용자가 필요한 곳은 user_id를교체
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request", request);
		shopServiceinter = new ShopHomeService(iDao);
		shopServiceinter.execute(model);
		
		
		return "shop/home";
	}
	
	@RequestMapping("/shop/product_popular")
	public String product_popular(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto); 
		
		model.addAttribute("request", request);
		shopServiceinter=new ShopProductPopularService(iDao);
		shopServiceinter.execute(model);
		
		
		return "shop/product_popular";
	}
	
	@RequestMapping("/shop/product_high_sales")
	public String product_high_sales(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request", request);
		shopServiceinter=new ShopProductHighSalesService(iDao);
		shopServiceinter.execute(model);
		
		
		return "shop/product_high_sales";
	}

	/*
	 * @RequestMapping("/shop/write_view") public String write_view() { return
	 * "shop/write_view"; }
	 * 
	 * @RequestMapping("/shop/write") public String write(HttpServletRequest
	 * request, Model model) {
	 * 
	 * model.addAttribute("request", request); shopServiceinter = new
	 * ShopWriteService(iDao); shopServiceinter.execute(model);
	 * 
	 * return "shop/home"; }
	 */
	
	//카테고리
	@RequestMapping("/shop/category")
	public String category(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request", request); 
		shopServiceinter = new ShopCategoryService(iDao); 
		shopServiceinter.execute(model);
	

		return "shop/category";
	}

	@RequestMapping("/shop/product_detail")
	public String product_detail(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		model.addAttribute("request", request);
		shopServiceinter = new ShopProductdetailService(iDao);
		shopServiceinter.execute(model);

		return "shop/product_detail";
	}
	
	@RequestMapping("/shop/review_write_view")
	public String review_write_view(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter= new ShopReviewWriteViewService(iDao);
		shopServiceinter.execute(model);
		
		return "shop/review_write_view";
	}
	
	@RequestMapping("/shop/review_write")
	public String review_write(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopReviewWriteService(iDao);
		shopServiceinter.execute(model);
		
		 //
		Long userid=memberDto.getUser_id();
        String product_id=request.getParameter("product_id");
		Long productid=Long.parseLong(product_id);
		System.out.println("userid:"+userid);	
		return "redirect:/shop/product_detail?product_id="+productid+"&userid="+userid;
		
	}

	@RequestMapping("/shop/review_delete")
	public String review_delete(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopReviewDeleteService(iDao);
		shopServiceinter.execute(model);
		
		//
		Long userid=memberDto.getUser_id();
        String target_id=request.getParameter("target_id");
		Long productid=Long.parseLong(target_id);
		System.out.println("userid:"+userid);
			
		return "redirect:/shop/product_detail?product_id="+productid;
		
	}
	
	@RequestMapping("/shop/review_update_view")
	public String review_update_view(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopReviewUpdateViewService(iDao);
		shopServiceinter.execute(model);
		
		return "shop/review_update_view";
	}
	
	@RequestMapping("/shop/review_update")
	public String review_update(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopReviewUpdateService(iDao);
		shopServiceinter.execute(model);
		
		//
		Long userid=memberDto.getUser_id();
        String target_id=request.getParameter("target_id");
		Long productid=Long.parseLong(target_id);
		System.out.println("userid:"+userid);
		
		return "redirect:/shop/product_detail?product_id="+productid;
	}
	
	
	
	//상품 문의 작성
	@RequestMapping("/shop/product_inquiry_write_view")
	public String product_inquiry_view(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductInquiryWriteViewService(iDao);
		shopServiceinter.execute(model);
		
		return "shop/product_inquiry_write_view";
	}
	
	
	@RequestMapping("/shop/product_inquiry_write")
	public String product_inquiry_write(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductInquiryWriteService(iDao);
		shopServiceinter.execute(model);
		
		//
        String user_id=request.getParameter("user_id");
        String product_id=request.getParameter("product_id");
		
		Long productid=Long.parseLong(product_id);
		Long userid=Long.parseLong(user_id);
		
		System.out.println("userid:"+userid);
		
		return "redirect:/shop/product_detail?product_id="+productid;
	}
	
	@RequestMapping("/shop/product_inquiry_update_view")
	public String product_inquiry_update_view(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductInquiryUpdateViewService(iDao);
		shopServiceinter.execute(model);
		
		
		return "shop/product_inquiry_update_view";
	}
	
	@RequestMapping("/shop/product_inquiry_update")
	public String product_inquiry_update(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductInquiryUpdateService(iDao);
		shopServiceinter.execute(model);
		
		//

		String product_id=request.getParameter("product_id");		
        System.out.println("product_id:"+product_id);
     
		Long productid=Long.parseLong(product_id);
		

		return "redirect:/shop/product_detail?product_id="+productid;
	}
	
	
	
	@RequestMapping("/shop/product_inquiry_delete")
	public String product_reply_delete(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		System.out.println("reply_write()");
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductInquiryDeleteService(iDao);
		shopServiceinter.execute(model);
	
		//
		String product_id=request.getParameter("product_id");
		
		if(product_id==null || product_id.isEmpty()) {
			System.out.println("product_id 가 null 입니다.");
		}
		
		Long productid=Long.parseLong(product_id);
		
		return "redirect:/shop/product_detail?product_id="+productid;
	}
	
	
	@RequestMapping("/shop/product_reply_view")
	public String product_reply_view(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductReplyViewService(iDao);
		shopServiceinter.execute(model);
		
		return "shop/product_reply_view";
	}
	
	
	@RequestMapping("/shop/product_reply_write")
	public String product_reply_write(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		System.out.println("reply_write()");
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductReplyWriteService(iDao);
		shopServiceinter.execute(model);
	
		//
		String product_id=request.getParameter("product_id");
		
		System.out.println(product_id);
		
		Long productid=Long.parseLong(product_id);
		
		return "redirect:/shop/product_detail?product_id="+productid;
	}
	

	@RequestMapping("/shop/productmall")
	public String exhibition(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
	
		model.addAttribute("request",request);
		shopServiceinter=new ShopProductMallService(iDao);
		shopServiceinter.execute(model);
		
		return "shop/productmall";
	}

	@RequestMapping("/shop/best")
	public String best(HttpServletRequest request,Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopBestService(iDao);
		shopServiceinter.execute(model);
		
		return "shop/best";
	}

	@RequestMapping("/shop/todaydeliver")
	public String todaydeliver() {
		return "shop/todaydeliver";
	}

	@RequestMapping("/shop/cart")
	public String cart(HttpServletRequest request, Model model) {
		
		System.out.println("cartlist");
		model.addAttribute("request", request);
		shopServiceinter = new ShopCartService(iDao);
		shopServiceinter.execute(model);

		return "shop/cart";
	}

	@RequestMapping("/shop/cart_write")
	public String cart_write(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		model.addAttribute("request", request);
		shopServiceinter = new ShopCartWriteService(iDao);
		shopServiceinter.execute(model);

		return "redirect:/shop/product_detail?product_id=" + request.getParameter("product_id");
	}

	@RequestMapping("/shop/cart_delete")
	public String cart_delete(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		model.addAttribute("request", request);
		shopServiceinter = new ShopCartDeleteService(iDao);
		shopServiceinter.execute(model);

		String user_id = request.getParameter("user_id");
		return "redirect:/shop/cart?user_id=" + user_id;
	}

	@RequestMapping("/shop/cart_update")
	public String cart_update(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		model.addAttribute("request", request);
		shopServiceinter = new ShopCartUpdateService(iDao);
		shopServiceinter.execute(model);

		String user_id = request.getParameter("user_id");
		return "redirect:/shop/cart?user_id=" + user_id;
	}

	
	//주문 과정
	@RequestMapping("/shop/order_view")
	public String order_view(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		model.addAttribute("request", request);
		shopServiceinter = new ShopOrderViewService(iDao);
		shopServiceinter.execute(model);

		return "shop/order_view";			
	}

	@RequestMapping("/shop/order_write")
	public String order_complete(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		String paymentMethod = request.getParameter("payment_method");
		
        System.out.println("선택된 결제 방식: " + paymentMethod);
		
		model.addAttribute("request", request);
		shopServiceinter = new ShopOrderWriteService(iDao,paymentMethod);
		shopServiceinter.execute(model);
		
		return "shop/order_complete";
	}

	
	 @RequestMapping("/shop/order_modify_view") 
	 public String order_modify_view(HttpServletRequest request, Model model) {
		 
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
	
		model.addAttribute("request", request); 
		shopServiceinter= new ShopOrderModifyViewService(iDao); 
		shopServiceinter.execute(model); 
		return "shop/order_modify_view";
	
	}
	 

	@RequestMapping("/shop/order_update")
	public String order_update(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		
		model.addAttribute("request",request); 
		shopServiceinter=new ShopOrderUpdateSerivce(iDao); 
		shopServiceinter.execute(model);
		String order_id = request.getParameter("order_id");
	
		return "redirect:/shop/order_details?user_id="+request.getParameter("user_id");
	}

	@RequestMapping("/shop/order_details")
	public String order_details(HttpServletRequest request, Model model) {
		
		LoginMemberService loginMemberService=new LoginMemberService();
		MemberDto memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);

		model.addAttribute("request", request);
		shopServiceinter = new ShopOrderDetailService(iDao);
		shopServiceinter.execute(model);
		
		
		/*@Autowired꼭 써야됨 ShopIDao iDao;
			ShopOrderDetailService shopOrderDetailService=new
		 * ShopOrderDetailService(iDao); shopOrderDetailService.execute(model);
		 */
		
		return "shop/order_details";
	}
	
	
	//결재
	@RequestMapping("/shop/kakaopay")
	public String kakaopay(HttpServletRequest request,Model model) {
		
		model.addAttribute("request",request);
		shopServiceinter=new ShopKakaopayService(iDao);
		shopServiceinter.execute(model);
		
		//
		
		return "shop/kakaopaypayment";
	}
	
	
	

	/*
	 * @PostMapping("/kakaopay") public ResponseEntity<?>
	 * kakaoPayReady(@RequestParam int amount) { KakaoPayReadyResponse response =
	 * kakaoPayService.kakaoPayReady(amount); return ResponseEntity.ok(response); }
	 * 
	 * @GetMapping("/kakaopay/success") public ResponseEntity<?>
	 * kakaoPaySuccess(@RequestParam String pg_token) { KakaoPayApprovalResponse
	 * response = kakaoPayService.kakaoPayApprove(pg_token); return
	 * ResponseEntity.ok(response); }
	 */

}
