package com.ama.don.admin.service.userActivityLog.memberPart;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.service.userActivityLog.SaveUserActivityLog;
import com.ama.don.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class WithdrawalLogAspect {

    private static final Logger log = LoggerFactory.getLogger(WithdrawalLogAspect.class);

    private final SaveUserActivityLog userActivityLog;
    private final ManageUserIDao manageUserIDao;

    @Pointcut("execution(* com.ama.don.member.service.WithdrawalService.deletedMember(..)) && args(agree, reason, memberDto)")
    public void withdrawalMethod(String agree, int reason, MemberDto memberDto) {}

    @AfterReturning("withdrawalMethod(agree, reason, memberDto)")
    public void logUserWithdrawal(String agree, int reason, MemberDto memberDto) {

        System.out.println("\n>>> 탈퇴 AOP 로그 메서드 호출됨.");

        if (!"yes".equals(agree)) {
            log.info("User did not agree to withdrawal. No log recorded.");
            return;
        }

        if (memberDto == null) {
            log.warn("MemberDto is null. Cannot log user withdrawal activity.");
            return;
        }

        userActivityLog.createAndSaveLog(
                memberDto.getUser_id(),
                "USER_WITHDRAWAL",
                "USER",
                memberDto.getUser_id(),
                "User Withdrawal (Reason: " + reason + ")"
        );
        log.info("User agree to withdrawal, User id : {}", memberDto.getUser_id());
    }
}
