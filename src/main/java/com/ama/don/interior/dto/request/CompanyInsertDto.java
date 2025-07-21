package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
