package com.ama.don.admin.service.userManage;

import com.ama.don.admin.dao.ManageUserIDao;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ManageUserIDao manageUserIDao;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        UserTotalDataDTO userLoginData = manageUserIDao.getUserByLoginId(loginId);

        if (userLoginData == null) {
            throw new UsernameNotFoundException(">>> 사용자를 찾을 수 없음 : " + loginId);
        }

        Timestamp sanctionsUntil = null;
        String sanctionsUntilStr = userLoginData.getUser_sanctions_until();
        if (sanctionsUntilStr != null && !sanctionsUntilStr.isEmpty()) {
            sanctionsUntil = Timestamp.valueOf(sanctionsUntilStr);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new ManageUserByAdmin(
                userLoginData,      // UserTotalDataDTO 객체 자체를 넘김
                authorities,
                userLoginData.getUser_status(),
                sanctionsUntil
        );
    }
}
