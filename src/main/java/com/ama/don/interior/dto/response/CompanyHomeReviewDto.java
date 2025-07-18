package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 홈에서 보는 사용자가 작성한 업체에 대한 리뷰 dto
// 클릭시 업체 상세보기의 리뷰탭의 리뷰 목록으로 이동함(CompanyReviewDto)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyHomeReviewDto {

    private Long reviewId;
    private Long companyId;

    private String reviewImg;       // 사진(썸네일용)
    private String reviewTitle;     // 제목(약간 intro 느낌)
    private String reviewContent;   // 리뷰 내용
    
    private String location;        // 위치
}
