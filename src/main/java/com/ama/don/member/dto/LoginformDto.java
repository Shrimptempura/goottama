package com.ama.don.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginformDto {
	
	@NotBlank(message = "아이디를 입력하세요.")
	private String loginId;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	private String pw;

}
