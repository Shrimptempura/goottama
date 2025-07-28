package com.ama.don.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	
	@NotNull
	private Gender gender;
	
	@NotBlank(message = "생년월일을 선택하세요.")
	@Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "생년월일 형식이 올바르지 않습니다. (예: 19900715)")
	private String birth;
	
	@NotBlank(message = "연락처를 입력하세요.")
	@Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$", message = "올바른 연락처 형식이 아닙니다.")
	private String tel;
	
	@NotBlank(message = "우편번호를 입력하세요.")
	private String zipcode;
	
	@NotBlank(message = "주소를 입력하세요.")
	private String addr;
	
	private String detailAddr;
	
	private String fullAddr;  //컨트롤러에서 combineAddress호출하여 주소 조합
	
	@NotBlank(message = "이메일을 입력하세요.")
	private String emailId;
	
	@NotBlank(message = "이메일을 입력하세요.")
	private String emailDomain;
	
	@Email(message = "올바를 이메일 형식이 아닙니다.")  //컨트롤러에서 combineEmail호출하여 이메일 조합
	private String email;
	
	
	//테이블 insert를 위한 이메일 병합 
	public void combineEmail() {
		if (emailId != null && emailDomain != null) {
			this.email = emailId + "@" + emailDomain;
		}
	}
	
	//테이블 insert를 위한 이메일 병합 
	public void combineAddress() {
		if (addr != null) {
			this.fullAddr = addr + (detailAddr != null ? " " + detailAddr : "");
		}
	}

	
	public enum Gender {
        M,
        F
    }
	

}
