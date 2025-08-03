package com.ama.don.common.dto;

import com.ama.don.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


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

	private Long targetId;
	private TargetType targetType;
}

