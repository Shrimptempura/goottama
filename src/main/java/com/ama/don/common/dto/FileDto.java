package com.ama.don.common.dto;

import com.ama.don.common.enums.TargetType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

	private Long file_id; // 파일 고유 ID
	private String file_uploader; // 업로드 주체
	private String file_name; // 원본 파일명
	private String file_path; // 저장된 파일명
	private TargetType target_type; // 파일이 속한 게시판 타입
	private Long target_id; // 게시글 ID

	@Override
	public String toString() {
		return "FileDto{" + "file_id=" + file_id + ", file_uploader='" + file_uploader + '\'' + ", file_name='"
				+ file_name + '\'' + ", file_path='" + file_path + '\'' + ", target_type=" + target_type
				+ ", target_id=" + target_id + '}';
	}
}
