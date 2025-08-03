package com.ama.don.common.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.ama.don.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {

	private Long commentId;
	private Long userId;

	private String commentContent;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private Boolean isDeleted;				// 소프트 삭제

	private Long target_id;
	private TargetType target_type;
}

