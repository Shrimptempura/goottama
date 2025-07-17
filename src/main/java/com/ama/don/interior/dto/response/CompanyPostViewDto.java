package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// 업체 포스트 조회용 dto
public class CompanyPostViewDto {

    private Long companyPostId;     // 업체 포스트(게시글) 아이디
    private Long companyId;     // 업체 아이디
    private String companyName;   // 업체 이름

    private Integer reviewCount;        // 리뷰 수
    private int viewCount;              // 조회 수
    private String postIntro;           // 게시글 소개말
}
