package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.member.dto.JoinformDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class JoinLogAspect {

    private final SaveUserActivityLog userActivityLog;
    private final ManageUserIDao manageUserIDao;

    @Pointcut("execution(* com.ama.don.member.service.JoinService.join(..) && args(joinformDto, ..)")
    public void joinMethod(JoinformDto joinformDto) {}

    public void joinMember(JoinformDto joinformDto) {
        UserActivityDto userActivityDto = new UserActivityDto();

        String loginId = joinformDto.getLoginId();
        manageUserIDao.getUserByLoginId(loginId);
    }
}
