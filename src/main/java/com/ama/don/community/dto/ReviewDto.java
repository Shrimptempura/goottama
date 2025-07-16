package com.ama.don.community.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
	private int post_id;
	private int user_id;
	private String post_title;
	private String post_content;
	private Timestamp post_date;
	private int post_count;
	private int post_like_count;
	private String post_img;

}
