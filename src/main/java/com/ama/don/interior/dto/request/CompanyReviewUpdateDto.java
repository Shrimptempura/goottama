package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// 회원이 업체에 쓴 리뷰 수정 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyReviewUpdateDto {

    private Long reviewId;

    private int communicationRate;  // 소통 점수
    private int priceRate;          // 가격 점수
    private int resultRate;         // 결과 점수
    private int scheduleRate;       // 일정 점수

    private String reviewContent;       // 리뷰 내용
    private List<String> reviewImg;     // 리뷰 사진

    private String structureType;       // 건물 유형
    private String areaPyeong;           // 평수
    private String constructionField;       // 시공 분야
}
