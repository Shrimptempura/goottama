package com.ama.don.interior.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// 댓글 조회
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyCommentTreeDto {

    private Long commentId;
    private Long parentCommentId;       // 대댓글
    private Long userId;

    private String userNickname;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isDeleted;

    // 댓글 이미지
    private String userProfileImgPath;
    private String userProfileImgName;

    private List<CompanyCommentTreeDto> tree;       // 대댓글
}
