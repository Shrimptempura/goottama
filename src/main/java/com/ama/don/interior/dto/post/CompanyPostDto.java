package com.ama.don.interior.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyPostDto {

    private Long companyId;
    private Long companyPostId;
    private Long postId;

    private String companyPostTitle;
    private String companyPostContent;

    private String areaPyeong;          // 평수
    private String style;
    private String spaceType;           // 공간 종류(아파트, 주택)
    private String constructionDetail;  // 세부 공사(주방리모델링, 도배시공..)

    private int companyPostLikeCount;     // 좋아요 수
    private int companyPostCount;      // 조회수

    // ui 전달 값x, 테스트 및 필터링
    private LocalDateTime postDate;     // 다형성 생성일
    private String region;              // 지역구
}
