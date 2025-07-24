package com.ama.don.interior.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

// 업체가 작성하는 게시글 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyPostCreateDto {

    private List<String> companyImgList;    // 업체가 올린 사진

    @NotBlank
    private String postTitle;       // 게시글 제목

    @NotBlank
    private String spaceType;       // 공간 종류(아파트, 주택)
    @NotBlank
    private String areaInPyeong;      // 평수
    @NotBlank
    private String style;           // 스타일(내추럴, 모던..)
    @NotBlank
    private String constructionDetail;   // 세부 공사(주방리모델링, 도배시공..)

    @NotBlank
    private String postContent;     // 게시글 내용
}
