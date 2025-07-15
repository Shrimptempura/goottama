package com.ama.don.interior.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyMiniInfoDto {

    Long company_detail_id;     // 업체상세 아이디
    String company_addr;        // 업체 주소

    /**
     * 업체 분야
     */
    String company_field;
    String company_license;     // 업체 면허

    /**
     * 업체 AS
     */
    String company_as;
    String company_career;      // 업체 경력
    String company_name;        // 업체 이름
    int company_rate;           // 업체 평점

    /**
     * 업체 소개말
     */
    String company_letter;
}
