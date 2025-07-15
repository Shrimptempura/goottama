package com.ama.don.interior.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// 업체 좌측 미니 박스에 담길 정보
public class CompanyMiniInfoDto {

    private Long companyDetailId;       // 업체상세 아이디
    private String companyName;         // 업체 이름
    private Boolean companyFollow;      // 팔로우 여부
    private Integer companyRate;        // 업체 평점(아직 평가하지 않음 -> Integer)

    /**
     * 업체 AS
     */
    private String companyAs;

    /**
     * 업체 소개말
     */
    private String companyIntro;
    private String companyAddr;         // 업체 주소

    /**
     * 업체 분야
     */
    private String companyField;
    private String companyLicense;      // 업체 면허
    private String companyCareer;       // 업체 경력[사용 확인]
}
