package com.ama.don.admin.dao;

import com.ama.don.admin.dto.NoticesDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface NoticesIDao {
    public ArrayList<NoticesDto> getAllNotices();
    public NoticesDto getNoticeById(String noticeId);
    public ArrayList<NoticesDto> searchNotice(String sk, String searchData);
    public ArrayList<NoticesDto> searchNoticesByDateRange(Timestamp start, Timestamp end);
    public boolean modifyNotice(String noticeId, String title, boolean isPinned, String filePath, String content);
    public boolean writeNotice(String title, boolean isPinned, String filePath, String content);
    public boolean deleteNotice(String noticeId);
}
