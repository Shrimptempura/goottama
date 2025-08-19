package com.ama.don.interior.dto.review;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 리뷰 작성시 사용 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyReviewCreateDto {

    private Long reviewId;
    private Long companyId;


    private int communicationRate;  // 소통 점수
    private int priceRate;          // 가격 점수
    private int resultRate;         // 결과 점수
    private int scheduleRate;       // 일정 점수
    @NotBlank
    private String reviewContent;       // 리뷰 내용

    @NotBlank
    private String structureType;       // 건물 유형
    @NotBlank
    private String areaPyeong;           // 평수
    @NotBlank
    private String constructionField;       // 시공 분야

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private List<String> reviewImg;     // 리뷰 사진
}
