package com.ama.don.admin.service.userManage;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
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
                true, // enabled (활성화 여부)
                true, // accountNonExpired (계정 만료 여부)
                true, // credentialsNonExpired (자격 증명 만료 여부)
                isAccountNonLockedCheck(sanctionsUntil), // 여기서 계정 잠금 여부를 판단
                authorities);

        this.userTotalDataDTO = userTotalDataDTO;
        this.userStatus = userStatus;
        this.sanctionsUntil = sanctionsUntil;
    }

    // 재제 기간이 현재 시각보다 이후인지 확인하는 로직
    private static boolean isAccountNonLockedCheck(Timestamp sanctionsUntil) {
        if (sanctionsUntil == null) {
            return true;
        }
        return Date.from(Instant.now()).after(sanctionsUntil);
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLockedCheck(this.sanctionsUntil);
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
}