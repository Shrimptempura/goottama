package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체에 대한 모든 정보 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyCreateDto {

    private Long companyDetailId;

    private String companyName;
    private String companyImg;
    private String companyAddr;         // location

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
}
