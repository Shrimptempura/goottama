package com.ama.don.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommunityController {

	@RequestMapping("community_home")
	public String home() {

		return "community/community_home";
	}

	@RequestMapping("review_view")
	public String review() {

		return "community/review_view";
	}

	@RequestMapping("suggest_view")
	public String suggest() {

		return "community/suggest_view";
	}

	@RequestMapping("popularity_view")
	public String popularity() {

		return "community/popularity_view";
	}

	@RequestMapping("house_photo_view")
	public String house_photo() {

		return "community/house_phosto_view";
	}

	@RequestMapping("house_decoration_view")
	public String house_decoration() {

		return "community/house_decoration_view";
	}

	@RequestMapping("write_view")
	public String write() {

		return "community/write_view";
	}

}
