package com.ama.don.interior.dto.company;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String companyName;

    @NotBlank
    private String companyAddr;         // location

    /**
     * 업체 분야
     */
    @NotBlank
    private String companyField;

    @NotBlank
    private String companyLicense;      // 업체 면허

    /**
     * 업체 AS
     */
    @NotBlank
    private String companyAs;

    @NotBlank
    private String companyCareer;       // 업체 경력

    /**
     * 업체 소개말
     */
    @NotBlank
    private String companyIntro;

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private String companyImg;
}
