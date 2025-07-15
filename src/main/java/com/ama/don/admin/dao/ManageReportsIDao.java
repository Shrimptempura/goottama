package com.ama.don.admin.dao;

import com.ama.don.admin.dto.ReportSearchVO;
import com.ama.don.admin.dto.ReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManageReportsIDao {
    /**
     * 전체 신고 내역 반환
     * @see #countAllReports() 
     * @author 정순석
     */
    public List<ReportVO> getAllReports();

    /**
     * 전체 신고 내역 개수 반환
     * @see #getAllReports() 
     * @author 정순석
     */
    public int countAllReports();

    /**
     * report_id에 해당하는 신고 내역 반환
     * @see #getAllReports() 
     * @author 정순석
     */
    public ReportVO getReportByReportId(String reportId);

    /**
     * 신고 내역 검색. 검색 조건은 신고 한 유저아이디, 신고 내용, 타겟 타입, 타켓 아이디, 신고 처리 상태, 신고일 가능
     * @see #countSearchReports(ReportSearchVO) 
     * @author 정순석
     */
    public List<ReportSearchVO> searchReports(ReportSearchVO searchVO);/**
     
     * 신고 내역 검색 결과 개수를 반환
     * @see #searchReports(ReportSearchVO)
     * @author 정순석
     */
    public int countSearchReports(ReportSearchVO searchVO);
}
