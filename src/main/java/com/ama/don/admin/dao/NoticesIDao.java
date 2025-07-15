package com.ama.don.admin.dao;

import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface NoticesIDao {
    /**
     * 전체 공지 반환
     * @see #countAllNotices()
     * @author 정순석
     */
    public List<NoticesDto> getAllNotices();

    /**
     * 전체 공지 개수 반환
     * @see #getAllNotices()
     * @author 정순석
     */
    public int countAllNotices();

    /**
     * notice_id에 해당하는 공지 반환
     * @see #getAllNotices()
     * @author 정순석
     */
    public NoticesDto getNoticeById(String noticeId);

    /**
     * 공지 검색. 제목, 내용, 날짜범위로 검색 가능.
     * @see #countSearchNotice(NoticeSearchVO)
     * @author 정순석
     */
    public List<NoticesDto> searchNotice(NoticeSearchVO noticeSearchVO);

    /**
     * 공지 검색 개수 반환
     * @see #searchNotice(NoticeSearchVO)
     * @author 정순석
     */
    public int countSearchNotice(NoticeSearchVO noticeSearchVO);

    /**
     * 공지 수정. 성공 시 true 반환, 그 외 모든 경우에는 false 반환.
     * @return boolean
     * @author 정순석
     */
    public boolean modifyNotice(String noticeId, String title, boolean isPinned, String filePath, String content);

    /**
     * 공지 작성. 성공 시 true 반환, 그 외 모든 경우에는 false 반환.
     * @return boolean
     * @author 정순석
     */
    public boolean writeNotice(String title, boolean isPinned, String filePath, String content);

    /**
     * 공지 삭제. 성공 시 true 반환, 그 외 모든 경우에는 false 반환.
     * @return boolean
     * @author 정순석
     */
    public boolean deleteNotice(String noticeId);
}
