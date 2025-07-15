package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// 업체 상세페이지의 자세한 업체정보 탭
public class CompanyDetailviewDto {

    private String companyAddr;     // 업체 주소

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
}
