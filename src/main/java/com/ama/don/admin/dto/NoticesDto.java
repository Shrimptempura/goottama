package com.ama.don.admin.dto;

import com.ama.don.admin.temp.FileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticesDto {
//    private int noticesId;
//    private String noticesTitle;
//    private boolean noticesIsPinned;
//    private Timestamp noticesCreatedAt;
//    private String noticesFilePath;
//    private String noticesContent;
    private int notices_id;
    private String notices_title;
    private boolean notices_is_pinned;
    private Timestamp notices_created_at;
    private String notices_file_path;
    private String notices_content;
    private List<FileDto> attachedFiles;
}
