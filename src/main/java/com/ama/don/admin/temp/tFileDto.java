package com.ama.don.admin.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 파일 정보를 담는 데이터 전송 객체(DTO).<br/>
 * 데이터베이스의 `file` 테이블과 매핑되며,
 * 업로드된 파일의 메타데이터 및 저장 경로 등을 표현함.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class tFileDto {
    private Long file_id;
    private String file_uploader;
    private String file_name; // original_filename
    private String file_path; // saved_filename
    private String target_type; // ENUM 'COMMUNITY', 'INTERIOR', 'MEMBER', 'SHOP', 'ADMIN'
    private Long target_id;
}
