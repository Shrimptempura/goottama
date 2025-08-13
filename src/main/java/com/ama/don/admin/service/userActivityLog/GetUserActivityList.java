package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.UserActivityLogIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import org.springframework.stereotype.Service;

@Service
public class GetUserActivityList {

    private final UserActivityLogIDao userActivityLogIDao;

    public GetUserActivityList(UserActivityLogIDao userActivityLogIDao) {
        this.userActivityLogIDao = userActivityLogIDao;
    }

    public void execute(UserActivityDto userActivityDto) {


    }
}
