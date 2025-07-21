package com.ama.don.community.Command;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ama.don.community.dao.Review_viewDao;
import com.ama.don.community.dto.Review_viewDto;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class Review_viewCommand implements CommunityCommand {

	@Override
	public void execute(Model model) {
	    HttpServletRequest request = (HttpServletRequest) model.getAttribute("request");
	    String param = request.getParameter("post_id");
	    if (param == null || param.isEmpty()) return;

	    int post_id = Integer.parseInt(param);
	    
	    Review_viewDao dao = new Review_viewDao();
	    dao.increaseViewCount(post_id); // 조회수 증가
	    Review_viewDto dto = dao.findById(post_id); // 게시글 조회

	    model.addAttribute("review_view", dto);
	}

}
