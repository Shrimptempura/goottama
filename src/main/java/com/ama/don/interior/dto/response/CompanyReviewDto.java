package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 사용자가 쓰는 업체에 대한 리뷰 read dto
// 업체상세정보 리뷰 탭에 존재
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyReviewDto {

    private Long reviewId;          // 리뷰 아이디
    private Long userId;            // 유저 아이디

    private String userNickName;            // 유저 닉네임
    private String userImg;          // 유저 프로필 사진
    
    private String structureType;       // 건물 유형
    private String areaPyeong;        // 평수
    private String constructionField;   // 시공 분야

    private String reviewContent;      // 리뷰 내용
    private LocalDateTime reviewDate;       // 리뷰 작성시간
    private LocalDateTime reviewModify;     // 리뷰 수정시간

    private int reviewLikes;    // 좋아요 수(단순 클릭, 중복 제거)

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private List<String> reviewImg;     // 리뷰 사진
}
