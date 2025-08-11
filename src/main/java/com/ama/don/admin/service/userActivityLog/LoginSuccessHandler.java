package com.ama.don.admin.service.userActivityLog;

import com.ama.don.admin.dto.userDTO.UserActivityDto;
import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SaveUserActivityLog getUserActivityList;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Long userId = null;
        if (authentication != null && authentication.getPrincipal() instanceof ManageUserByAdmin) {
            ManageUserByAdmin userDetails = (ManageUserByAdmin) authentication.getPrincipal();
            UserTotalDataDTO userTotalDataDTO = userDetails.getUserTotalDataDTO();
            userId = userTotalDataDTO.getUser_id();
        }

        //아이피
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        if (userId != null) {
            UserActivityDto userActivityDto = new UserActivityDto();
            userActivityDto.setUser_id(userId);
            userActivityDto.setUser_activity_type("LOGIN_SUCCESS");
            userActivityDto.setUser_activity_target_type("LOGIN");
            userActivityDto.setUser_activity_time(Timestamp.from(Instant.now()));
            userActivityDto.setUser_activity_details("Login IP : " + ipAddress);

            getUserActivityList.saveUserActivity(userActivityDto);
        }

        response.sendRedirect("/");
    }
}
