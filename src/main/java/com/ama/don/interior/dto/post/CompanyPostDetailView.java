package com.ama.don.interior.dto.post;

import com.ama.don.common.dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// 업체 게시글 상세보기 dto
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyPostDetailView {

    // 정보 dto 조합
    private CompanyPostDetailSplitDto post;
    private CompanyPostBasicInfoDto company;

    private List<FileDto> images;

    // 확장 예정 필드명 불확실
    private Boolean postLiked;
    private Boolean scraped;

    // 조회수, 좋아요 수는 다른 dto 또는 CompanyPostDetailSplitDto.post에 존재함

}
