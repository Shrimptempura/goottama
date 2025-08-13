package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.UserActivityLogIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SaveUserActivityLog {
    private static final Logger log = LoggerFactory.getLogger(SaveUserActivityLog.class);
    private final UserActivityLogIDao userActivityLogIDao;

    public SaveUserActivityLog(UserActivityLogIDao userActivityLogIDao) {
        this.userActivityLogIDao = userActivityLogIDao;
    }

    public void saveUserActivity(UserActivityDto userActivityDto) {
        Assert.notNull(userActivityDto, "[ERROR] UserActivityDto cannot be null");
        try {
            int saveCount = userActivityLogIDao.writeUserActivityLog(userActivityDto);
            if (saveCount <= 0) {
                log.warn("[WARN] Failed to write user activity log. No rows affected. userId: {}", userActivityDto.getUser_id());
            }
        } catch (Exception e) {
            // DB 관련 예외 발생 시 로그 기록
            log.error("[ERROR] Failed to save user activity log. userId: {}. Details: {}", userActivityDto.getUser_id(), userActivityDto.getUser_activity_details(), e);
        }
    }
}
