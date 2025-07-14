package com.ama.don.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list() ctr");


		return "list";
	}
	

}
