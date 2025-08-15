package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dao.UserActivityLogIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public class GetUserActivityDetail {

    private final UserActivityLogIDao userActivityLogIDao;
    private final ManageUserIDao manageUserIDao;

    public GetUserActivityDetail(UserActivityLogIDao userActivityLogIDao, ManageUserIDao manageUserIDao) {
        this.userActivityLogIDao = userActivityLogIDao;
        this.manageUserIDao = manageUserIDao;
    }

    public void execute(Model model) {
        Map<String, Object> map = model.asMap();
        String userActivityId = (String) model.getAttribute("userActivityId");
        UserActivityDto userActivityLog = userActivityLogIDao.getUserActivityById(userActivityId);
        if (userActivityLog == null) {
            throw new RuntimeException("유저 로그 찾을 수 없음. ID: " + userActivityId);
        }
        Long userId = userActivityLog.getUser_id();
        UserTotalDataDTO userTotalDataDTO = manageUserIDao.getUserByUserId(String.valueOf(userId));
        model.addAttribute("userData", userTotalDataDTO);
        model.addAttribute("userActivityLog", userActivityLog);
    }
}
