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
// 업체가 작성한 포스터
public class CompanyPostCreateDto {

    private Long companyPostId;      // 업체 포스트(게시글) 아이디
    private Long companyId;             // 업체 아이디
    private String companyIntro;        // 업체 소개말
    private Boolean isFollowed;      // 업체 팔로우

    private List<String> companyImgList;    // 업체가 올린 사진

    /**
     * sql 추가 부분, 인테리어 설명
     */
    private String spaceType;       // 공간 종류
    private String areaInPyeong;      // 평수
    private String location;        // 지역[location table 확인]
    private String style;           // 스타일
    private String constructionDetail;   // 세부 공사

    private Timestamp postDate;         // 작성시간
    private int likeCount;      // 좋아요 수
    private int viewCount;      // 게시판 조회수
}
