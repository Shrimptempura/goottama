package com.ama.don.interior.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 댓글 조회
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyCommentTreeDto {

    private Long commentId;
    private Long parentCommendId;       // 대댓글
    
    private String userNickname;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isDeleted;
}
