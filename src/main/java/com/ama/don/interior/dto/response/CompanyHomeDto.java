package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 홈에서 보는 회사 dto
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyHomeDto {

    private Long companyId;

    private String companyImg;      // 업체 사진(썸네일용)
    private String companyName;     // 업체 이름
    private String rate;            // 업체 별점
    private int countReview;        // 리뷰 수

    private String location;        // 지역
}
