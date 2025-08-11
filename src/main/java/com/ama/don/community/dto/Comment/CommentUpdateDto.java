package com.ama.don.community.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentUpdateDto {
	private Long commentId;
	private Long userId;
	private String commentContent;
}