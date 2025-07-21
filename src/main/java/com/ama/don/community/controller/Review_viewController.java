package com.ama.don.community.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.community.Command.Review_viewCommand;

@Controller
public class Review_viewController {

	@Autowired
	private Review_viewCommand command;

	@RequestMapping("review_view")
	public String review(HttpServletRequest request, Model model) {
		System.out.println("review_view");
		model.addAttribute("request", request);
		command.execute(model);

		return "community/review_view";
	}
}
