package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 홈에서 보는 업체의 게시글 목록 dto
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyHomePostDto {

    private Long companyId;
    private Long postId;
    private Long companyPostId;

    private String companyPostTitle;       // 게시글 제목
    private String areaPyeong;    // 평수
    private String style;           // 스타일(모던, 내추럴..), enum 확장 필요?

    private String location;        // 위치

    // 파일 관련은 다형성 file dto로 서비스에서 해결
    // private String postImg;         // 사진, 썸네일용
}
