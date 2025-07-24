package com.ama.don.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPwDto {
	
	@NotBlank(message = "아이디를 입력하세요.")
	private String LoginId;
	
	@Email(message = "올바를 이메일 형식이 아닙니다.")
	private String email;

}
