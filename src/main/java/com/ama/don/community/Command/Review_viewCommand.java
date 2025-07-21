package com.ama.don.community.Command;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.ama.don.community.dao.Review_viewDao;
import com.ama.don.community.dto.Review_viewDto;

import jakarta.servlet.http.HttpServletRequest;

public class Review_viewCommand implements CommunityCommand {

	@Autowired
	private Review_viewDao reviewDao;

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int post_id = Integer.parseInt(request.getParameter("post_id"));

		Review_viewDto dto = reviewDao.getReview(post_id);
		model.addAttribute("dto", dto);
	}
}
