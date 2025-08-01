package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

// 업체가 작성한 게시글을 상세보는 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyPostDetailDto {
    
    // 연관된 id들
    private Long postId;
    private Long companyId;
    private Long companyPostId;

    private String companyPostTitle;               // 게시글 제목
    private String companyName;             // 업체 이름
    private String companyIntro;            // 업체 소개말

    private Boolean isFollowed;               // 팔로우 여부

    private String spaceType;       // 공간 종류(아파트, 주택..)
    private String areaPyeong;    // 평수
    private String style;           // 스타일(내추럴, 모던..)
    private String constructionDetail;      // 세부 공사(주방리모델링, 도배시공..)

    private String companyPostContent;     // 게시글 내용

    private Timestamp postDate;     // 게시글 작성 시간
    
    private int countLikes;     // 좋아요 수
    private int countScrap;     // 스크랩 수
    private int countView;      // 조회수

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private List<String> companyImgList;    // 게시글 사진들
    // private String companyProfileImg;       // 업체 프로필 사진
}
