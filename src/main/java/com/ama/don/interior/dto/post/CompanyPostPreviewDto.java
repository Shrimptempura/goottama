package com.ama.don.interior.dto.post;

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
    private Long companyPostId;


    private String companyPostTitle;           // 게시글 제목
    private int companyPostCount;              // 조회수
    private int scrapCount;             // 스크랩 수

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private String postImg;             // 게시글 사진(썸네일용)
}
