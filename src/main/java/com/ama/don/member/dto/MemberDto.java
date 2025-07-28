package com.ama.don.member.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
	
	//user_login 테이블
	private String login_id;
	private long user_id;
	private int roles_id;
	private String user_password;
	
	//user_detail 테이블
	private String user_name;
	private String user_nickname;
	private Gender user_gender;
	private String user_birth;
	private LocalDateTime user_created_at;
	private String user_tel;
	private String user_zipcode;
	private String user_addr;
	private String user_email;
	private String user_img;
	
	public enum Gender {
        M,
        F
    }

}
