package com.ama.don.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
	private String login_id;
	private long user_id;
	private int roles_id;
	private String user_password;

}
