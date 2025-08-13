package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.UserActivityLogIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.dto.userDTO.UserActivitySearchDTO;
import com.ama.don.admin.utils.SearchVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetUserActivityList {

    private final UserActivityLogIDao userActivityLogIDao;

    public GetUserActivityList(UserActivityLogIDao userActivityLogIDao) {
        this.userActivityLogIDao = userActivityLogIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        SearchVO searchVO = (SearchVO) map.get("searchVO");
        UserActivitySearchDTO userActivitySearchDTO = (UserActivitySearchDTO) map.get("userActivitySearchDTO");
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<UserActivityDto> userActivityDtos;
        int total;

        if (userActivitySearchDTO == null ||
                (userActivitySearchDTO.getUser_activity_type() == null || userActivitySearchDTO.getUser_activity_type().isEmpty()) &&
                (userActivitySearchDTO.getUser_activity_target() == null || userActivitySearchDTO.getUser_activity_target().isEmpty()) &&
                (userActivitySearchDTO.getUser_activity_time_start() == null || userActivitySearchDTO.getUser_activity_time_start().isEmpty()) &&
                (userActivitySearchDTO.getUser_activity_time_end() == null || userActivitySearchDTO.getUser_activity_time_end().isEmpty()) &&
                (userActivitySearchDTO.getUser_activity_details() == null || userActivitySearchDTO.getUser_activity_details().isEmpty())) {
            total = userActivityLogIDao.countGetAllUserActivity();
            userActivityDtos = userActivityLogIDao.getAllUserActivity(searchVO);
        } else {
            total = userActivityLogIDao.countGetSearchUserActivity(userActivitySearchDTO);
            userActivityDtos = userActivityLogIDao.getSearchUserActivity(userActivitySearchDTO, searchVO);
        }
        searchVO.pageCalculate(total);

        for (UserActivityDto dto : userActivityDtos) {
            Map<String, Object> row = new HashMap<>();
            row.put("userActivityId", dto.getUser_activity_id());
            row.put("userId", dto.getUser_id());
            row.put("userActivityType", dto.getUser_activity_type());
            row.put("userActivityTime", dto.getUser_activity_time());
            row.put("userActivityTargetId", dto.getUser_activity_target_id());
            row.put("userActivityTargetType", dto.getUser_activity_target_type());
            row.put("userActivityDetails", dto.getUser_activity_details());
            mapList.add(row);
        }

        model.addAttribute("list", mapList);
        model.addAttribute("searchVO", searchVO);
    }
}
