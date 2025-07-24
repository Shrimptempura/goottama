package com.ama.don.member.dto;

import com.ama.don.member.dto.JoinformDto.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindLoginIdDto {
	
	@NotBlank(message = "이름을 입력하세요.")
	private String name;
	
	@Email(message = "올바를 이메일 형식이 아닙니다.")
	private String email;

}
