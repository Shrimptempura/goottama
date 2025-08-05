package com.ama.don.interior.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체가 작성하는 게시글 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyPostCreateDto {

    private Long postId;
    private Long companyPostId;
    private Long companyId;

    @NotBlank
    private String companyPostTitle;       // 게시글 제목

    @NotBlank
    private String companyPostContent;     // 게시글 내용

    @NotBlank
    private String spaceType;       // 공간 종류(아파트, 주택)
    @NotBlank
    private String areaPyeong;      // 평수
    @NotBlank
    private String style;           // 스타일(내추럴, 모던..)
    @NotBlank
    private String constructionDetail;   // 세부 공사(주방리모델링, 도배시공..)

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private List<String> companyImgList;    // 업체가 올린 사진
}
