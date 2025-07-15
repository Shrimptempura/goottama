package com.ama.don.interior.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// 사용자가 쓰는 업체에 대한 리뷰 dto
public class CompanyReviewCreateDto {

    private Long reviewId;          // 리뷰 아이디
    private Long userId;            // 유저 아이디
    private String userNickName;    // 유저 닉네임
    private String userProfileImg;         // 유저 프로필 사진

    private String reviewTitle;     // 리뷰 제목
    private String reviewContent;      // 리뷰 내용
    private int reviewCount;            // 리뷰 조회수
    private Timestamp reviewDate;       // 리뷰 작성시간
    private Timestamp reviewModify;     // 리뷰 수정시간
    private List<String> reviewImg;     // 리뷰 사진

    private Integer targetId;       // 대상아이디
    private String targetType;    // enum : "INTERIOR", "COMMUNITY", "SHOP"
    private int reviewLikes;    // 좋아요 수(단순 클릭, 중복 제거)
}
