package com.ama.don.member.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ama.don.admin.service.userManage.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {
	
	 private final CustomUserDetailsService customUserDetailsService;

	    /**
	     * 로그인한 사용자의 인증 정보를 갱신
	     * @param loginId 회원의 로그인 ID
	     */
	    public void refreshAuthentication(String loginId) {
	        UserDetails updatedUserDetails = customUserDetailsService.loadUserByUsername(loginId);

	        Authentication newAuth = new UsernamePasswordAuthenticationToken(
	                updatedUserDetails,
	                updatedUserDetails.getPassword(),
	                updatedUserDetails.getAuthorities()
	        );

	        SecurityContextHolder.getContext().setAuthentication(newAuth);
	    }

}
