package com.ama.don.community.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class Review_viewDto {
	private Long post_id;
	private Long user_id;
	private String post_title;
	private String post_content;
	private Timestamp post_date;
	private int post_count;
	private int post_like_count;
	private String post_img;
	private Long target_type;
	private Long target_id;

	public Review_viewDto(Long post_id, Long user_id, String post_title, String post_content, Timestamp post_date,
			int post_count, int post_like_count, String post_img, Long target_type, Long target_id) {
		this.post_id = post_id;
		this.user_id = user_id;
		this.post_title = post_title;
		this.post_content = post_content;
		this.post_date = post_date;
		this.post_count = post_count;
		this.post_like_count = post_like_count;
		this.post_img = post_img;
		this.target_type = target_type;
		this.target_id = target_id;
	}
}
