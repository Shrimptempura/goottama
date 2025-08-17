package com.ama.don.admin.dto.commentsForAdminDTO;

import com.ama.don.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class CommentsForAdminDTO {
    private Long commentId;
    private Long userId;

    private String commentContent;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Boolean isDeleted;

    private Long targetId;
    private TargetType targetType;
}
