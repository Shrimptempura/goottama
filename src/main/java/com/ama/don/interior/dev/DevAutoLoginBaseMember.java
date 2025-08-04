package com.ama.don.interior.dev;


import com.ama.don.member.dao.LoginDao;
import com.ama.don.member.dto.MemberDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 실제 DB 테스트 유저로 자동 로그인
@Component
@WebFilter(urlPatterns = {"/*"})
public class DevAutoLoginBaseMember implements Filter {

    @Autowired
    private LoginDao loginDao;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        if (session.getAttribute("loginMember") == null) {

            // 실제 디비에 저장된 로그인 id 입력
            // user_login, user_detail 둘다 생성되어있어야 함
            MemberDto member = loginDao.interiorFindByLoginId("goott");
            session.setAttribute("loginMember", member);
        }

        chain.doFilter(request, response);
    }


}
