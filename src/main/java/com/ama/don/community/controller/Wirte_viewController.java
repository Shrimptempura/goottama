package com.ama.don.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.community.Command.Write_viewCommand;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/community")
public class Wirte_viewController {

	Write_viewCommand command;
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view() ctr");

		return "community/write_view";
	}

	@RequestMapping("write")
	public String write(HttpServletRequest request, Model model) {
		System.out.println("write() ctr");
		model.addAttribute("request", request);
		command = new Write_viewCommand();
		command.execute(model);
		return "redirect:review_view";
	}

}
