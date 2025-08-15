package com.ama.don.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPwDto {
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	@Size(min = 8, max = 20)
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$",
			message = "비밀번호는 8~20자리, 영문/숫자/특수문자를 포함해야 합니다.")
	private String resetPw;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	private String resetPw2;

}
