package com.ama.don.community.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Write_viewDto {
	private int id;
	private String title;
	private String content;
	private String img;
}
