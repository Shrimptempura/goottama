package com.ama.don.shop.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.dto.KakaoPayApprovalResponse;
import com.ama.don.shop.dto.KakaoPayReadyResponse;
import com.ama.don.shop.service.ShopCartDeleteService;
import com.ama.don.shop.service.ShopCartService;
import com.ama.don.shop.service.ShopCartUpdateService;
import com.ama.don.shop.service.ShopCartWriteService;
import com.ama.don.shop.service.ShopProductMallService;
import com.ama.don.shop.service.ShopListService;
import com.ama.don.shop.service.ShopProductdetailService;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.ShopWriteService;
import com.ama.don.shop.service.Kakaopay.ShopKakaopayService;
import com.ama.don.shop.service.orderservice.ShopOrderDetailService;
import com.ama.don.shop.service.orderservice.ShopOrderModifyViewService;
import com.ama.don.shop.service.orderservice.ShopOrderUpdateSerivce;
import com.ama.don.shop.service.orderservice.ShopOrderViewService;
import com.ama.don.shop.service.orderservice.ShopOrderWriteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ShopController {

	ShopServiceinter sServiceinter;
	ShopServiceinter shopServiceinter;

	private final ShopKakaopayService kakaoPayService;

	public ShopController(ShopKakaopayService kakaoPayService) {
		this.kakaoPayService = kakaoPayService;
	}

	@Autowired
	private ShopIDao iDao;

	@RequestMapping("/shop/subheader")
	public String subheader() {
		return "shop/subheader";
	}

	@RequestMapping("/shop/home")
	public String home() {
		return "shop/home";
	}

	@RequestMapping("/shop/write_view")
	public String write_view() {
		return "shop/write_view";
	}

	@RequestMapping("/shop/write")
	public String write(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopWriteService(iDao);
		shopServiceinter.execute(model);

		return "shop/home";
	}

	@RequestMapping("/shop/category")
	public String category(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopListService(iDao);
		shopServiceinter.execute(model);

		return "shop/category";
	}

	@RequestMapping("/shop/product_detail")
	public String product_detail(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopProductdetailService(iDao);
		shopServiceinter.execute(model);

		return "shop/product_detail";
	}

	@RequestMapping("/shop/exhibition")
	public String exhibition(HttpServletRequest request, Model model) {

//		model.addAttribute("request",request);
//		sServiceinter=new SExhibitionService(iDao);
//		sServiceinter.execute(model);

		return "shop/exhibition";
	}

	@RequestMapping("/shop/best")
	public String best() {
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

		model.addAttribute("request", request);
		shopServiceinter = new ShopCartWriteService(iDao);
		shopServiceinter.execute(model);

		return "redirect:/shop/product_detail?product_id=" + request.getParameter("product_id");
	}

	@RequestMapping("/shop/cart_delete")
	public String cart_delete(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopCartDeleteService(iDao);
		shopServiceinter.execute(model);

		String user_id = request.getParameter("user_id");
		return "redirect:/shop/cart?user_id=" + user_id;
	}

	@RequestMapping("/shop/cart_update")
	public String cart_update(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopCartUpdateService(iDao);
		shopServiceinter.execute(model);

		String user_id = request.getParameter("user_id");
		return "redirect:/shop/cart?user_id=" + user_id;
	}

	@RequestMapping("/shop/order_view")
	public String order_view(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopOrderViewService(iDao);
		shopServiceinter.execute(model);

		return "shop/order_view";
	}

	@RequestMapping("/shop/order_write")
	public String order_complete(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopOrderWriteService(iDao);
		shopServiceinter.execute(model);
		return "shop/order_complete";
	}

	
	 @RequestMapping("/shop/order_modify_view") 
	 public String order_modify_view(HttpServletRequest request, Model model) {
	
	
		model.addAttribute("request", request); 
		shopServiceinter= new ShopOrderModifyViewService(iDao); 
		shopServiceinter.execute(model); 
		return "shop/order_modify_view";
	
	}
	 

	@RequestMapping("/shop/order_update")
	public String order_update(HttpServletRequest request, Model model) {

		
		model.addAttribute("request",request); 
		shopServiceinter=new ShopOrderUpdateSerivce(iDao); 
		shopServiceinter.execute(model);
		String order_id = request.getParameter("order_id");
	
		return "redirect:/shop/order_details?user_id="+request.getParameter("user_id");
	}

	@RequestMapping("/shop/order_details")
	public String order_details(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);
		shopServiceinter = new ShopOrderDetailService(iDao);
		shopServiceinter.execute(model);
		return "shop/order_details";
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
