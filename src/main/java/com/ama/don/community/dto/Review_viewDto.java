package com.ama.don.community.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class Review_viewDto {
	private int post_id;
	private int user_id;
	private String post_title;
	private String post_content;
	private Date post_date;
	private int post_count;
	private int post_like_count;
	private String post_img;
	private int target_type;
	private int target_id;

	public Review_viewDto(int post_id, int user_id, String post_title, String post_content, Timestamp post_date,
			int post_count, int post_like_count, String post_img) {
		// TODO Auto-generated constructor stub
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_content() {
		return post_content;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}

	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	public int getPost_count() {
		return post_count;
	}

	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}

	public int getPost_like_count() {
		return post_like_count;
	}

	public void setPost_like_count(int post_like_count) {
		this.post_like_count = post_like_count;
	}

	public String getPost_img() {
		return post_img;
	}

	public void setPost_img(String post_img) {
		this.post_img = post_img;
	}

	public int getTarget_type() {
		return target_type;
	}

	public void setTarget_type(int target_type) {
		this.target_type = target_type;
	}

	public int getTarget_id() {
		return target_id;
	}

	public void setTarget_id(int target_id) {
		this.target_id = target_id;
	}

}
