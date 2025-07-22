package com.ama.don.admin.dao;

import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공지사항에 관련된 DB 접근을 정의한 MyBatis Mapper 인터페이스. <br>
 * - 공지 목록 조회, 검색, 등록, 수정, 삭제 기능 포함
 *
 * @author 정순석
 */
@Repository
@Mapper
public interface NoticesIDao {
    /**
     * 전체 공지사항 목록을 반환한다.
     * @return 공지사항 목록 (List<NoticesDto>)
     * @see #countAllNotices()
     */
    public List<NoticesDto> getAllNotices(@Param("searchVO") SearchVO searchVO);

    /**
     * 전체 공지사항의 개수를 반환한다.
     * @return 전체 공지 개수
     * @see #getAllNotices(SearchVO)
     */
    public int countAllNotices();

    /**
     * 주어진 ID에 해당하는 공지사항을 반환한다.
     * @param noticeId 조회할 공지의 ID
     * @return 공지사항 DTO (존재하지 않으면 null)
     * @see #getAllNotices(SearchVO)
     */
    public NoticesDto getNoticeById(String noticeId);

    /**
     * 검색 조건(제목, 내용, 날짜 범위 등)에 따라 공지사항 목록을 반환한다.
     * @param noticeSearchVO 검색 조건을 담은 VO <br>
     *        - title: 검색할 제목 키워드 <br>
     *        - content: 검색할 내용 키워드 <br>
     *        - dateStart, dateEnd: 검색할 날짜 범위
     * @return 검색 결과 공지사항 목록
     * @see #countSearchNotice(NoticeSearchVO)
     */
    List<NoticesDto> searchNotice(@Param("noticeSearchVO") NoticeSearchVO noticeSearchVO
                                , @Param("searchVO") SearchVO searchVO);

    /**
     * 검색 조건에 해당하는 공지사항 개수를 반환한다.
     * @param noticeSearchVO 검색 조건 VO
     * @return 검색 결과 개수
     * @see #searchNotice(NoticeSearchVO, SearchVO)
     */
    public int countSearchNotice(@Param("noticeSearchVO") NoticeSearchVO noticeSearchVO);

    /**
     * 공지사항 내용을 수정한다.
     * @param noticeId 수정할 공지 ID
     * @param title 수정할 제목
     * @param isPinned 상단 고정 여부
     * @param filePath 첨부 파일 경로 (null 가능)
     * @param content 공지 본문 내용
     * @return 성공 시 true, 실패 시 false
     */
    public boolean modifyNotice(String noticeId, String title, boolean isPinned, String filePath, String content);

    /**
     * 새로운 공지사항을 작성한다.
     * @param title 제목
     * @param isPinned 상단 고정 여부
     * @param filePath 첨부 파일 경로 (null 가능)
     * @param content 본문 내용
     * @return 성공 시 true, 실패 시 false
     */
    public boolean writeNotice(String title, boolean isPinned, String filePath, String content);

    /**
     * 주어진 ID의 공지사항을 삭제한다.
     * @param noticeId 삭제할 공지의 ID
     * @return 성공 시 true, 실패 시 false
     */
    public boolean deleteNotice(String noticeId);
}
