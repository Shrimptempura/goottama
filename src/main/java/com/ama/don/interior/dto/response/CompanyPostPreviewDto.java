package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체 상세페이지에서 보는 게시글
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyPostPreviewDto {

    private Long postId;     // 업체 포스트(게시글) 아이디
    private Long companyId;     // 업체 아이디

    private String postImg;             // 게시글 사진(썸네일용)

    private String postTitle;           // 게시글 제목
    private int countView;              // 조회수
    private int countScrap;             // 스크랩 수
}
