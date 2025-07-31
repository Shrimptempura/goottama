package com.ama.don.admin.dao;

import com.ama.don.admin.dto.ReportSearchDTO;
import com.ama.don.admin.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 관리자용 신고 관리 DAO 인터페이스 (MyBatis Mapper).
 * <p>
 * 전체 신고 조회, 상세 조회, 조건 검색 및 개수 조회 기능을 포함함.
 * </p>
 *
 * 검색 조건은 {@link ReportSearchDTO}를 통해 전달되며,
 * 신고자 ID, 신고 내용, 대상 타입/ID, 처리 상태, 신고일(범위) 등으로 검색 가능하다.
 *
 * @author 정순석
 */
@Mapper
public interface ManageReportsIDao {

    /**
     * 전체 신고 내역을 반환한다.
     *
     * @return 신고 목록 (List of {@link ReportDTO})
     * @see #countAllReports()
     */
    List<ReportDTO> getAllReports();

    /**
     * 전체 신고 개수를 반환한다.
     *
     * @return 신고 개수
     * @see #getAllReports()
     */
    int countAllReports();

    /**
     * 특정 신고 ID에 해당하는 신고 정보를 조회한다.
     *
     * @param reportId 조회할 신고의 고유 ID
     * @return 해당 신고 정보 (없으면 null)
     * @see #getAllReports()
     */
    ReportDTO getReportByReportId(String reportId);

    /**
     * 신고 조건 검색 결과 목록을 반환한다.
     * <p>
     * 검색 조건: <br>
     * - 신고자 ID <br>
     * - 신고 내용 (부분 일치) <br>
     * - 대상 타입 (예: 게시글, 댓글 등) <br>
     * - 대상 ID <br>
     * - 신고 처리 상태 <br>
     * - 신고일 범위
     * </p>
     *
     * @param searchVO 검색 조건을 담은 VO
     * @return 조건에 맞는 신고 목록
     * @see #countSearchReports(ReportSearchDTO)
     */
    List<ReportSearchDTO> searchReports(ReportSearchDTO searchVO);

    /**
     * 신고 조건 검색 결과의 개수를 반환한다.
     *
     * @param searchVO 검색 조건 VO
     * @return 검색 결과 수
     * @see #searchReports(ReportSearchDTO)
     */
    int countSearchReports(ReportSearchDTO searchVO);
}
