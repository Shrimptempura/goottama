package com.ama.don.admin.service.userActivityLog.memberPart;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.member.dto.JoinformDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class JoinLogAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ManageUserIDao manageUserIDao;

    @Pointcut("execution(* com.ama.don.member.service.JoinService.join(..)) && args(joinformDto, model)")
    public void joinMethod(JoinformDto joinformDto, Model model) {}

    @AfterReturning("joinMethod(joinformDto, model)")
    public void logUserJoin(JoinPoint joinPoint, JoinformDto joinformDto, Model model) {

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

        userActivityLog.createAndSaveLog(
                userTotalDataDTO.getUser_id(),
                "USER_JOIN",
                "USER",
                userTotalDataDTO.getUser_id(),
                "User joined with ID : " + loginId
        );
    }
}