package com.ama.don.admin.service.userManage;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public class ManageUserByAdmin implements UserDetails {
    private String loginId;
    private String password;
    private List<GrantedAuthority> authorities;
    private String userStatus;
    private Timestamp sanctionsUntil;

    public ManageUserByAdmin(String loginId, String password, List<GrantedAuthority> authorities, String userStatus, Timestamp sanctionsUntil) {
        this.loginId = loginId;
        this.password = password;
        this.authorities = authorities;
        this.userStatus = userStatus;
        this.sanctionsUntil = sanctionsUntil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public boolean isAccountNonLocked() {
        // userStatus가 "suspended"이고, sanctionsUntil이 현재 시간 이후라면 계정 잠금 상태로 판단
        if ("suspended".equals(this.userStatus) && this.sanctionsUntil != null) {
            return !this.sanctionsUntil.after(new Timestamp(System.currentTimeMillis()));
        }
        return true;
    }
}
