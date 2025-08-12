package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.member.dto.JoinformDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class JoinLogAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ManageUserIDao manageUserIDao;

    @Pointcut("execution(* com.ama.don.member.service.JoinService.join(..) && args(joinformDto, ..)")
    public void joinMethod(JoinformDto joinformDto) {}

    @AfterReturning("joinMethod(joinformDto)")
    public void logUserJoin(JoinPoint joinPoint, JoinformDto joinformDto) {

        if (joinformDto == null) {
            log.warn("JoinFormDto is null. Cannot log user join activity.");
            return;
        }

        String loginId = joinformDto.getLoginId();
        if (loginId == null || loginId.isEmpty()) {
            log.warn("LoginId is null or empty. Cannot log user join activity.");
            return;
        }

        UserTotalDataDTO userTotalDataDTO = manageUserIDao.getUserByLoginId(loginId);
        if (userTotalDataDTO == null) {
            log.error("Failed to retrieve user data for loginId: {}. Cannot log user join activity.", loginId);
            return;
        }

        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setUser_id(userTotalDataDTO.getUser_id());
        userActivityDto.setUser_activity_type("USER_JOIN");
        userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
        userActivityDto.setUser_activity_details("User joined with ID : " + loginId);
        userActivityDto.setUser_activity_target_type("USER");
        userActivityDto.setUser_activity_target_id(userTotalDataDTO.getUser_id());

        userActivityLog.saveUserActivity(userActivityDto);
    }
}
