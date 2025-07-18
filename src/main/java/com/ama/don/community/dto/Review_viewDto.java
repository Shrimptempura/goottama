package com.ama.don.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review_viewDto {

	private int post_id; // 게시글 아이디
	private int user_id; // 회원 아이디
	private String post_title; // 제목
	private String post_content; // 내용
	private int post_count; // 조회수
	private int post_like_count; // 좋아요수
	private String post_img; // 사진

	public Review_viewDto(int post_id, int user_id, String post_title) {
		
		this.post_title = post_title;
		
	}

}
