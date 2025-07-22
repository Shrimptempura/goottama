package com.ama.don.common.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentsDto {
	long comment_id;
	long user_id;
	String comment_content;
	Timestamp comment_date;
	long target_id;
	String tartet_type;
	
	public CommentsDto(long comment_id, long user_id, String comment_content, Timestamp comment_date, long target_id,
			String tartet_type) {
		super();
		this.comment_id = comment_id;
		this.user_id = user_id;
		this.comment_content = comment_content;
		this.comment_date = comment_date;
		this.target_id = target_id;
		this.tartet_type = tartet_type;
	}
	
}
	

