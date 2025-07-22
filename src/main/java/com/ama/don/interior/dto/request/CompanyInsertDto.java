package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체 테이블
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyInsertDto {

    private Long userId;
    private String locationId;
    private Long companyDetailId;
    private String companyImg;
}
