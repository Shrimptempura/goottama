package com.ama.don.interior.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 분할 게시글 조회: post + company_post
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyPostDetailSplitDto {

    private Long postId;
    private Long companyId;
    private Long companyPostId;

    private LocalDateTime postDate;

    private String companyPostTitle;
    private String companyPostContent;
    private LocalDateTime updatedAt;

    private String spaceType;
    private String areaPyeong;
    private String style;
    private String constructionDetail;

    private int companyPostLikeCount;
    private int companyPostCount;
    private int scrapCount;

    private Boolean isPostLiked;

}
