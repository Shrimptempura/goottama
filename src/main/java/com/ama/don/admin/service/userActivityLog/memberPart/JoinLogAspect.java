package com.ama.don.admin.service.userActivityLog.memberPart;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.member.dto.JoinformDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class JoinLogAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ManageUserIDao manageUserIDao;

    @Pointcut("execution(* com.ama.don.member.controller.JoinController.emailCheck(..)) && args(.. , session)")
    public void emailCheckMethod(HttpSession session) {}

    @AfterReturning(value = "emailCheckMethod(session)", returning = "result")
    public void logUserJoin(JoinPoint joinPoint, HttpSession session, Object result) {

        if (result instanceof String && ((String) result).equals("redirect:/successJoin_view")) {

            JoinformDto joinformDto = (JoinformDto) session.getAttribute("tempJoinUser");

            if (joinformDto == null) {
                log.warn("JoinFormDto is null in session. Cannot log user join activity.");
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
        } else {
            log.info("Email verification failed. No log recorded.");
        }
    }
}