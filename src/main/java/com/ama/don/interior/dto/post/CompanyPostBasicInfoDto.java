package com.ama.don.interior.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 분할 게시글 조회: company + company_detail
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyPostBasicInfoDto {

    private Long companyId;
    private Long companyDetailId;

    private String companyName;
    private String companyIntro;
}
