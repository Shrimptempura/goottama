package com.ama.don.community.Command;

import java.util.Map;

import org.springframework.ui.Model;

import com.ama.don.community.dao.Write_viewDao;

import jakarta.servlet.http.HttpServletRequest;

public class Write_viewCommand implements CommunityCommand{

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request =(HttpServletRequest) map.get("request");
		String post_title = request.getParameter("post_title");
		String post_content = request.getParameter("post_content");
		String post_img = request.getParameter("post_img");
		
		Write_viewDao dao = new Write_viewDao();
		dao.write(post_title, post_content, post_img);
		
	} 

}
