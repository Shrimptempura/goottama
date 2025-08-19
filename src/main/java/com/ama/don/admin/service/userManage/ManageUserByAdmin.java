package com.ama.don.admin.service.userManage;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class ManageUserByAdmin extends User {

    private final UserTotalDataDTO userTotalDataDTO;
    private final String userStatus;
    private final Timestamp sanctionsUntil;

    public ManageUserByAdmin(
            UserTotalDataDTO userTotalDataDTO,
            Collection<? extends GrantedAuthority> authorities,
            String userStatus,
            Timestamp sanctionsUntil) {

        super(userTotalDataDTO.getLogin_id(),
                userTotalDataDTO.getUser_password(),
                !isDeletedCheck(userStatus),
                true,
                true,
                isAccountNonLockedCheck(sanctionsUntil),
                getAuthorities(userTotalDataDTO.getRoles_id()));

        this.userTotalDataDTO = userTotalDataDTO;
        this.userStatus = userStatus;
        this.sanctionsUntil = sanctionsUntil;
    }
    private static Collection<? extends GrantedAuthority> getAuthorities(Long rolesIdLong) {
        int rolesId = rolesIdLong.intValue();
        String roleName = switch (rolesId) {
            case 100 -> "ROLE_USER"; // 일반 유저
            case 200 -> "ROLE_SELLER"; // 판매자
            case 300 -> "ROLE_ADMIN"; // 관리자
            case 400 -> "ROLE_SUPER_ADMIN"; // 운영자
            default -> "ROLE_USER"; // 기본값
        };
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    // 재제 기간이 현재 시각보다 이후인지 확인하는 로직
    private static boolean isAccountNonLockedCheck(Timestamp sanctionsUntil) {
        if (sanctionsUntil == null) {
            return true;
        }
        return Date.from(Instant.now()).after(sanctionsUntil);
    }

    private static boolean isDeletedCheck(String userStatus) {
        return "deleted".equals(userStatus);
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLockedCheck(this.sanctionsUntil);
    }

    @Override
    public boolean isEnabled() {
        return !isDeletedCheck(this.userStatus);
    }

    public UserTotalDataDTO getUserTotalDataDTO() {
        return userTotalDataDTO;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public Timestamp getSanctionsUntil() {
        return sanctionsUntil;
    }

    // 인테리어에서 사용함(userId, userNickname)
    public Long getUserId() {
        return userTotalDataDTO.getUser_id();
    }

    public String getUserNickname() {
        return userTotalDataDTO.getUser_nickname();
    }
}