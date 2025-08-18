package com.ama.don.interior.dev;


import com.ama.don.admin.service.userManage.CustomUserDetailsService;
import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.dto.MemberDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

// 실제 DB 테스트 유저로 자동 로그인
@Slf4j
public class DevAutoLoginBaseMember implements Filter {

    private final LoginDao loginDao;
    private final CustomUserDetailsService customUserDetailsService;

    public DevAutoLoginBaseMember(LoginDao loginDao, CustomUserDetailsService customUserDetailsService) {
        this.loginDao = loginDao;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("DevAutoLoginBaseMember - filter 실행 중");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        // 이미 인증된 경우는 패스
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            // test2, test4 사용중, company11, 12, 13, 14, 15
            MemberDto member = loginDao.interiorFindByLoginId("company11");

            if (member != null) {
                log.info("DevAutoLoginBaseMember - DB에서 테스트 유저 조회 성공: login_id {}", member.getLogin_id());
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(member.getLogin_id());

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // SecurityContextHolder에 등록
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("SecurityContextHolder에 인증 설정 완료");
            } else {
                log.warn("로그인 실패 - 테스트 계정 없음");
            }
        }

        chain.doFilter(request, response);
    }
}

