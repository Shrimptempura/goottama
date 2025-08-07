package com.ama.don.interior.dev;

import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class DevFindTarget {

    public static ManageUserByAdmin findTarget() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof ManageUserByAdmin)) {
            throw new IllegalStateException("사용자 인증 실패");
        }

        return (ManageUserByAdmin) auth.getPrincipal();
    }

    public static Long getUserId() {
        return findTarget().getUserId();
    }

    public static String getUserNickname() {
        return findTarget().getUserNickname();
    }

    public static String getLoginId() {
        return findTarget().getUserTotalDataDTO().getLogin_id();
    }
}
