package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.UserActivityLogIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class SaveUserActivityLog {
    private static final Logger log = LoggerFactory.getLogger(SaveUserActivityLog.class);
    private final UserActivityLogIDao userActivityLogIDao;

    public SaveUserActivityLog(UserActivityLogIDao userActivityLogIDao) {
        this.userActivityLogIDao = userActivityLogIDao;
    }

    public void createAndSaveLog(Long userId, String type, String targetType, Long targetId, String details) {
        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setUser_id(userId);
        userActivityDto.setUser_activity_type(type);
        userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
        userActivityDto.setUser_activity_target_type(targetType);
        userActivityDto.setUser_activity_target_id(targetId);
        userActivityDto.setUser_activity_details(details);

        saveUserActivity(userActivityDto);
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
