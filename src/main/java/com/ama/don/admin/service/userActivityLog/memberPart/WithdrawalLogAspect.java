package com.ama.don.admin.service.userActivityLog.memberPart;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
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
public class WithdrawalLogAspect {

    private static final Logger log = LoggerFactory.getLogger(WithdrawalLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ManageUserIDao manageUserIDao;

    @Pointcut("execution(* com.ama.don.member.service.WithdrawalService.deletedMember(..) && args(agree, reason, memberDto) ")
    public void withdrawalMethod(String agree, int reason, MemberDto memberDto) {}

    @AfterReturning("withdrawalMethod(memberDto)")
    public void logUserWithdrawal(String agree, int reason, MemberDto memberDto) {

        if (!"yes".equals(agree)) {
            log.info("User did not agree to withdrawal. No log recorded.");
            return;
        }

        if (memberDto == null) {
            log.warn("MemberDto is null. Cannot log user withdrawal activity.");
            return;
        }

        UserActivityDto userActivityDto = new UserActivityDto();
        userActivityDto.setUser_id(memberDto.getUser_id());
        userActivityDto.setUser_activity_type("USER_WITHDRAWAL");
        userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
        userActivityDto.setUser_activity_target_type("USER");
        userActivityDto.setUser_activity_target_id(memberDto.getUser_id());
        userActivityDto.setUser_activity_details("User Withdrawal (Reason: " + reason + ")");

        userActivityLog.saveUserActivity(userActivityDto);
    }
}
