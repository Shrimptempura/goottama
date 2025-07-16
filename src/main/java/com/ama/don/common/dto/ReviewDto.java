package com.ama.don.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

// 공통 리뷰 테이블에 대한 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {

    private Long reviewId;              // 리뷰 아이디
    private String reviewTitle;         // 리뷰 제목
    private String reviewContent;       // 리뷰 내용
    private Timestamp reviewDate;       // 리뷰 작성 날짜
    private Timestamp reviewModify;     // 리뷰 수정 일지
    private String reviewImg;           // 리뷰 사진

    private Integer targetId;       // 대상 아이디
    private String targetType;      //  enum: INTERIOR, COMMUNITY, SHOP
}
