package com.ama.don.interior.dto.company;

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

    private Long companyId;

    private Long userId;
    private Long locationId;
    private Long companyDetailId;
    private String companyImg;
}
