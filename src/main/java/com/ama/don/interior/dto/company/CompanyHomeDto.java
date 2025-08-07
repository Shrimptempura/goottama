package com.ama.don.interior.dto.company;

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

    private String companyName;     // 업체 이름
    private String companyRate;            // 업체 별점
    private int reviewCount;        // 리뷰 수

    // 파일 경로 + 파일명
    // 조인으로 할까 생각중
    private String thumbnailPath;       // 썸네일

    // private String location;        // 지역

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private String companyImg;      // 업체 사진(썸네일용)
}
