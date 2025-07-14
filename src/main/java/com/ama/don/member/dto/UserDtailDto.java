package com.ama.don.member.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtailDto {
	private long user_id;
	private String user_name;
	private String user_nickname;
	private Gender user_gender;
	private String user_birth;
	private Timestamp user_created_at;
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
