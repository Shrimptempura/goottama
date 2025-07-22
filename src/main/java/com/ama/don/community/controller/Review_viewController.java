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
		String param = request.getParameter("post_id");
		if (param == null || param.isEmpty()) {
			System.out.println("post_id 없음");
			return "redirect:review_view";
		}

		int post_id = Integer.parseInt(param);
		model.addAttribute("request", request);
		command.execute(model);

		return "community/review_view";
	}

}
