package com.ama.don.interior.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체 주소를 바꿀시 변경
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyUpdateLocationDto {

    private Long locationId;

    // 카카오 api로 변환 예정
    private String locationAddr;    // 위치 주소(한글)
    private String locationLat;     // 위치 위도
    private String locationLng;     // 위치 경도
    private String locationAc;      // 위치 코드
    private String locationLimit;   // 위치 제한
}
