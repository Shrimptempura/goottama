package com.ama.don.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditDto {
	
	@NotBlank(message = "이름을 입력하세요.")
	private String changeName;
	
	@NotBlank(message = "닉네임을 입력하세요.")
	private String changeNickname;
	
	@NotBlank(message = "연락처를 입력하세요.")
	@Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$|^01[016789]\\d{3,4}\\d{4}$", message = "올바른 연락처 형식이 아닙니다.")
	private String changeTel;
	
	@NotBlank(message = "우편번호를 입력하세요.")
	private String changeZipcode;
	
	@NotBlank(message = "주소를 입력하세요.")
	private String changeAddr;
	
	private String changeDetailAddr;
	
	private String changeFullAddr;
	
	public void combineAddress() {
		if (changeAddr != null) {
			this.changeFullAddr = changeAddr + (changeDetailAddr != null ? " " + changeDetailAddr : "");
		}
	}

}
