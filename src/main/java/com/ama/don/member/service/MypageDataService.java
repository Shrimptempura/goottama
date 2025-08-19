package com.ama.don.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ama.don.admin.dao.PostForAdminIDao;
import com.ama.don.admin.dto.postForAdminDTO.PostForAdminDTO;
import com.ama.don.admin.dto.postForAdminDTO.PostSearchForAdminDTO;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.member.dto.MemberDto;

@Service
public class MypageDataService {
	
	private final PostForAdminIDao postForAdminIDao;
	
	public MypageDataService(PostForAdminIDao postForAdminIDao) {
		this.postForAdminIDao = postForAdminIDao;
	}
	
	public void excute(Model model, MemberDto memberDto) {
		Map<String, Object> map = model.asMap();
        PostSearchForAdminDTO postSearchForAdminDTO = (PostSearchForAdminDTO) map.get("postSearchForAdminDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<PostForAdminDTO> postForAdminDTOS;
        
        postSearchForAdminDTO.setUser_id(memberDto.getUser_id());
        postForAdminDTOS = postForAdminIDao.searchPost(searchVO, postSearchForAdminDTO);
        
        for (PostForAdminDTO dto : postForAdminDTOS) {
            Map<String, Object> row = new HashMap<>();
            row.put("post_id", dto.getPost_id());
            row.put("user_id", dto.getUser_id());
            row.put("post_title", dto.getPost_title());
            row.put("post_content", dto.getPost_content());
            row.put("post_date", dto.getPost_date());
            row.put("targetId", dto.getTargetId());
            row.put("targetType", dto.getTargetType());
            mapList.add(row);
        }
        model.addAttribute("mapList", mapList);
	}
	
}
