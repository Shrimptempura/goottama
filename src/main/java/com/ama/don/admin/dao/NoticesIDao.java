package com.ama.don.admin.dao;

import com.ama.don.admin.dto.NoticeSearchVO;
import com.ama.don.admin.dto.NoticesDto;
import com.ama.don.admin.utils.SearchVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공지사항 데이터베이스 접근을 위한 DAO(Data Access Object) 인터페이스.<br/>
 * 공지사항 정보(NoticesDto)를 조회, 삽입, 수정, 삭제하는 데이터 연산 메서드 정의함.<br/>
 * MyBatis 매퍼와 연동되어 데이터베이스와의 상호작용 처리함.
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
     * @param noticesDto NoticesDto의 아래 값들을 받는다. <br>
     *          - title <br>
     *          - content <br>
     *          - file_path <br>
     *          - is_pinned <br>
     *          - id
     * @return 성공 시 true, 실패 시 false
     * @see NoticesDto
     */
    public boolean modifyNotice(NoticesDto noticesDto);

    /**
     * 새로운 공지사항을 작성한다.
     * @param noticesDto NoticesDto의 아래 값들을 받는다. <br>
     *           - title <br>
     *           - content <br>
     *           - file_path <br>
     *           - is_pinned <br>
     * created_at은 NOW()를 갖고, id는 auto increment이다.
     * @return 성공 시 true, 실패 시 false
     * @see NoticesDto
     */
    public boolean writeNotice(NoticesDto noticesDto);

    /**
     * 주어진 ID의 공지사항을 삭제한다.
     * @param noticeId 삭제할 공지의 ID
     * @return int, 성공시 1, 실패시 0
     */
    public int deleteNotice(String noticeId);
}
