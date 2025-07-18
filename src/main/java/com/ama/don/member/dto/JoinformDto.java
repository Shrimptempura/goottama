package com.ama.don.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinformDto {
	
	private long userId;
	
	@NotBlank(message = "아이디를 입력하세요.")
	private String loginId;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	@Size(min = 8, max = 20)
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$",
			message = "비밀번호는 비밀번호는 8~20자리, 영문/숫자/특수문자를 포함해야 합니다.")
	private String pw;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	private String pw2;
	
	private int rolesId;
	
	@NotBlank(message = "이름을 입력하세요.")
	private String name;
	
	@NotBlank(message = "닉네임을 입력하세요.")
	private String nickname;
	
	private Gender gender;
	
	@NotBlank(message = "생년월일을 선택하세요.")
	private String birth;
	
	@NotBlank(message = "연락처를 입력하세요.")
	private String tel;
	
	@NotBlank(message = "우편번호를 입력하세요.")
	private String zipcode;
	
	@NotBlank(message = "주소를 입력하세요.")
	private String addr;
	
	@NotBlank(message = "이메일을 입력하세요.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String email;
	
	
	public enum Gender {
        M,
        F
    }
	

}
