package com.ama.don.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ama.don.community.Command.CommunityCommand;

@Controller
public class CommunityController {

	CommunityCommand command;
	// 컨트롤러

	@RequestMapping("community_home")
	public String home() {

		return "community/community_home";
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

		return "community/house_photo_view";
	}

	@RequestMapping("house_decoration_view")
	public String house_decoration() {

		return "community/house_decoration_view";
	}

}
