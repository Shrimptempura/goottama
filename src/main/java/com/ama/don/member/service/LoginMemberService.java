package com.ama.don.member.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ama.don.admin.dto.userDTO.UserTotalDataDTO;
import com.ama.don.admin.service.userManage.ManageUserByAdmin;
import com.ama.don.member.dto.MemberDto;

@Service
public class LoginMemberService {
	
	public MemberDto getCurrentLoginMemberDto() {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	      
	      if (authentication != null && authentication.getPrincipal() instanceof ManageUserByAdmin) {
	         ManageUserByAdmin loggedInUser = (ManageUserByAdmin) authentication.getPrincipal();
	         UserTotalDataDTO userTotalData = loggedInUser.getUserTotalDataDTO();
	         
	         MemberDto memberDto = new MemberDto();
	         memberDto.setLogin_id(userTotalData.getLogin_id());
	         memberDto.setUser_id(userTotalData.getUser_id());
	         memberDto.setRoles_id((int) userTotalData.getRoles_id());
	         memberDto.setUser_password(userTotalData.getUser_password());
	         memberDto.setUser_name(userTotalData.getUser_name());
	         memberDto.setUser_nickname(userTotalData.getUser_nickname());
	         memberDto.setUser_gender(userTotalData.getUser_gender());
	         memberDto.setUser_birth(userTotalData.getUser_birth());
	         memberDto.setUser_created_at(userTotalData.getUser_created_at());
	         memberDto.setUser_tel(userTotalData.getUser_tel());
	         memberDto.setUser_zipcode(userTotalData.getUser_zipcode());
	         memberDto.setUser_addr(userTotalData.getUser_addr());
	         memberDto.setUser_email(userTotalData.getUser_email());
	         memberDto.setUser_img(userTotalData.getUser_img());
	         memberDto.setUser_status(userTotalData.getUser_status());
	         memberDto.setUser_sanctions_until(userTotalData.getUser_sanctions_until());
		
		return memberDto;
	}
return null;
}
}
