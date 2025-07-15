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

    Long companyDetailId;       // 업체상세 아이디
    String companyName;         // 업체 이름
    Boolean companyFollow;      // 팔로우 여부
    Integer companyRate;        // 업체 평점(아직 평가하지 않음 -> Integer)

    /**
     * 업체 AS
     */
    String companyAs;

    /**
     * 업체 소개말
     */
    String companyLetter;
    String companyAddr;         // 업체 주소

    /**
     * 업체 분야
     */
    String companyField;
    String companyLicense;      // 업체 면허
    String companyCareer;       // 업체 경력[사용 확인]
}
