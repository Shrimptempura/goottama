package com.ama.don.admin.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long file_id;
    private String file_uploader;
    private String file_name; // original_filename
    private String file_path; // saved_filename
    private String target_type; // ENUM 'COMMUNITY', 'INTERIOR', 'MEMBER', 'SHOP', 'ADMIN'
    private Long target_id;
}
