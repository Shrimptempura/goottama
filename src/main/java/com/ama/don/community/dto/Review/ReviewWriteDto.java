package com.ama.don.community.dto.Review;

import java.sql.Timestamp;

import com.ama.don.common.enums.TargetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewWriteDto {

	private Long review_id; // 리뷰 아이디
	private Long post_id; // 게시글 아이디
	private Long user_id; // 유저 아이디
	private String review_title; // 게시글 제목
	private String review_content; // 게시글 내용
	private Timestamp review_date; // 게시글 작성 날짜
	private int review_count; // 게시글 조회수
	private int review_like_count; // 게시글 좋아요 수

	private Long targetId;
	private TargetType targetType;

}