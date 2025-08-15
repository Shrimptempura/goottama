package com.ama.don.interior.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyUpdateDto {

    private Long companyId;
    private Long companyDetailId;
    private Long locationId;        // 일단 안쓰는 기능

    private String companyName;
    private String companyAddr;

    /**
     * 업체 분야
     */
    private String companyField;
    private String companyLicense;      // 업체 면허

    /**
     * 업체 AS
     */
    private String companyAs;
    private String companyCareer;       // 업체 경력

    /**
     * 업체 소개말
     */
    private String companyIntro;

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    private String companyImg;
}
