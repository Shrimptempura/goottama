package com.ama.don.admin.service.postViewerForAdmin;

import com.ama.don.admin.dao.PostForAdminIDao;
import com.ama.don.admin.dto.postForAdminDTO.PostForAdminDTO;
import com.ama.don.admin.dto.postForAdminDTO.PostSearchForAdminDTO;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetPostListService {

    private final PostForAdminIDao postForAdminIDao;

    public GetPostListService(PostForAdminIDao postForAdminIDao) {
        this.postForAdminIDao = postForAdminIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        PostSearchForAdminDTO postSearchForAdminDTO = (PostSearchForAdminDTO) map.get("postSearchForAdminDTO");
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<PostForAdminDTO> postForAdminDTOS;
        int total;

        // 검색 조건이 없거나 비어있는 경우
        if (postSearchForAdminDTO == null ||
                postSearchForAdminDTO.getPost_id() == null &&
                        postSearchForAdminDTO.getUser_id() == null &&
                        (postSearchForAdminDTO.getPost_title() == null || postSearchForAdminDTO.getPost_title().isEmpty()) &&
                        (postSearchForAdminDTO.getPost_content() == null || postSearchForAdminDTO.getPost_content().isEmpty()) &&
                        (postSearchForAdminDTO.getPost_date_start() == null || postSearchForAdminDTO.getPost_date_start().isEmpty()) &&
                        (postSearchForAdminDTO.getPost_date_end() == null || postSearchForAdminDTO.getPost_date_end().isEmpty()) &&
                        postSearchForAdminDTO.getTargetId() == null &&
                        (postSearchForAdminDTO.getTargetType() == null || postSearchForAdminDTO.getTargetType().isEmpty())) {
            total = postForAdminIDao.countAllPost();
            postForAdminDTOS = postForAdminIDao.getAllPost(searchVO);
        } else {
            // 검색 조건이 있는 경우
            total = postForAdminIDao.countSearchPost(postSearchForAdminDTO);
            postForAdminDTOS = postForAdminIDao.searchPost(searchVO, postSearchForAdminDTO);
        }

        searchVO.pageCalculate(total);

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

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("mapList", mapList);
    }
}