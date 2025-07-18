package com.ama.don.community.Command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.ama.don.community.dao.Review_viewDao;
import com.ama.don.community.dto.Review_viewDto;

public class Review_viewCommand implements CommunityCommand {

	@Override
	public void execute(Model model) {

		Review_viewDao dao = new Review_viewDao();
		ArrayList<Review_viewDto> dtos = dao.review_view();

		model.addAttribute("review_view", dtos);

	}

}
