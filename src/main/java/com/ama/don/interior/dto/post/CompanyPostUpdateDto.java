package com.ama.don.interior.dto.post;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 업체 작성한 게시글에 대한 수정 dto
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyPostUpdateDto {

    private Long companyPostId;

    private String companyPostTitle;               // 게시글 제목

    private String spaceType;               // 공간 종류(아파트, 주택..)
    private String areaPyeong;            // 평수
    private String style;                   // 스타일(내추럴, 모던..)
    private String constructionDetail;      // 세부 공사(주방리모델링, 도배시공..)

    private String companyPostContent;             // 게시글 내용

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private List<String> companyImgList;    // 게시글 사진들
}
