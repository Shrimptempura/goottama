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
	
	private long file_id;
	private String file_uploader;
	private String file_name;
	private String file_path;
	private  TargetType target_type;
	private long target_id;

	@Override
	public String toString() {
		return "FileDto{" +
				"file_id=" + file_id +
				", file_uploader='" + file_uploader + '\'' +
				", file_name='" + file_name + '\'' +
				", file_path='" + file_path + '\'' +
				", target_type=" + target_type +
				", target_id=" + target_id +
				'}';
	}

}
