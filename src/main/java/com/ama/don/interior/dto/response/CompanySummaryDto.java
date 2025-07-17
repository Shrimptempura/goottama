package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체 좌측 미니 박스에 담길 정보
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanySummaryDto {

    private Long companyId;

    private String companyName;         // 업체 이름
    private Boolean companyFollow;      // 팔로우 여부
    private Integer companyRate;        // 업체 평점(아직 평가하지 않음 -> Integer)

    /**
     * 업체 소개말
     */
    private String companyIntro;
    private String companyAddr;         // 업체 주소
    private String companyLicense;      // 업체 면허

    /**
     * 업체 분야
     */
    private String companyField;

}
