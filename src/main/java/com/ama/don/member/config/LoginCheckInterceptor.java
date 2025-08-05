package com.ama.don.member.config;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @deprecated security config의 도입으로 사용 중지
 */
public class LoginCheckInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginMember") == null) {
			response.sendRedirect("/login_view");
			return false;
		}
		
		return true;
		
	}

}
