package com.ama.don.shop.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.shop.dao.IDao;
import com.ama.don.shop.service.SCartService;
import com.ama.don.shop.service.SCartWriteService;
import com.ama.don.shop.service.SExhibitionService;
import com.ama.don.shop.service.SListService;
import com.ama.don.shop.service.SProductdetailService;
import com.ama.don.shop.service.SServiceinter;
import com.ama.don.shop.service.SWriteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SController {
	
	SServiceinter sServiceinter;
	
	@Autowired
	private IDao iDao;

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
	public String write(HttpServletRequest request,Model model) {
		
		
		model.addAttribute("request",request);
		sServiceinter=new SWriteService(iDao);
		sServiceinter.execute(model);
		
		return "shop/home";
	}
	
	@RequestMapping("/shop/category")
	public String category(HttpServletRequest request,Model model) {
		
		
		model.addAttribute("request",request);
		sServiceinter=new SListService(iDao);
		sServiceinter.execute(model);
		
		return "shop/category";
	}
	
	@RequestMapping("/shop/product_detail")
	public String product_detail(HttpServletRequest request, Model model) {
		
		
		model.addAttribute("request",request);
		sServiceinter=new SProductdetailService(iDao);
		sServiceinter.execute(model);
		
		return "shop/product_detail";
	}

	@RequestMapping("/shop/exhibition")
	public String exhibition(HttpServletRequest request,Model model) {
		
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
	public String cart(HttpServletRequest request,Model model) {
		
//		model.addAttribute(model);
//		sServiceinter=new SCartService(iDao);
//		sServiceinter.execute(model);
		
		return "shop/cart";
	}
	
	@RequestMapping("/shop/cart_write")
	public String cart_write(HttpServletRequest request,Model model) {
		
		model.addAttribute("request",request);
		sServiceinter=new SCartWriteService(iDao);
		sServiceinter.execute(model);
		
		
		return "redirect:/shop/product_detail?product_id=" + request.getParameter("product_id");
	}
	
	
}
