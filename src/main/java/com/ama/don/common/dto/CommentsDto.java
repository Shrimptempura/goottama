package com.ama.don.common.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
	long comment_id;
	long user_id;
	String comment_content;
	Timestamp comment_date;
	long target_id;
	String target_type;

}
