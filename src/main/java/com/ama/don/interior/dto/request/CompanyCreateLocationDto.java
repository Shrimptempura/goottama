package com.ama.don.interior.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원이 업체 가입할때 사용
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyCreateLocationDto {

    private Long locationId;

    // 카카오 api로 변환 예정
    @NotBlank
    private String locationAddr;    // 위치 주소(한글)

    @NotBlank
    private String locationLat;     // 위치 위도

    @NotBlank
    private String locationLng;     // 위치 경도

    @NotBlank
    private String locationAc;      // 위치 코드

    @NotBlank
    private String locationLimit;   // 위치 제한
}
