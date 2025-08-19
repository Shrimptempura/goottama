package com.ama.don.interior.dto.comment;

import com.ama.don.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyCommentDto {

    private Long commentId;
    private Long parentCommentId;       // 대댓글
    private Long userId;

    private String userNickname;
    private String commentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean deleted;

    private Long targetId;
    private TargetType targetType;
    
    // 댓글 이미지
    private String userProfileImgPath;
    private String userProfileImgName;
}
