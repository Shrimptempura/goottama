package com.ama.don.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticesDto {
    private int noticesId;
    private String noticesTitle;
    private boolean noticesIsPinned;
    private Timestamp noticesCreatedAt;
    private String noticesFilePath;
    private String noticesContent;
}
