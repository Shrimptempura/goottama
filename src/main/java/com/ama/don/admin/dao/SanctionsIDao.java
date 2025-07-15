package com.ama.don.admin.dao;

import com.ama.don.admin.dto.SanctionSearchVO;
import com.ama.don.admin.dto.SanctionsDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface SanctionsIDao {
    /**
     * 모든 제재 내역 출력
     * @author 정순석
     */
    List<SanctionsDto> getAllSanctions();

    /**
     * 제재 아이디를 통한 제재 내역 검색.
     * @author 정순석
     */
    SanctionsDto getSanctionsById(String sanctionsId);

    /**
     * 제재 검색. 검색 조건은 유저 아이디, 제재 타입, 관리자 아이디, 제재 사유, 제재 시작 및 종료일, 제재 기간.
     * @author 정순석
     */
    List<SanctionsDto> searchSanctions(SanctionSearchVO sanctionSearchVO);

    /**
     * 제재 생성. 성공 시 true 반환, 그 외 모든 경우에는 false 반환.
     * @return boolean
     * @author 정순석
     */
    boolean makeSanction(String userId, String sanctionType, Timestamp start, Timestamp end, String reason, String adminId, Timestamp createdAt);

    /**
     * 제재 수정. 성공 시 true 반환, 그 외 모든 경우에는 false 반환.
     * @return boolean
     * @author 정순석
     */
    boolean modifySanction(String userId, String sanctionType, Timestamp start, Timestamp end, String reason, String adminId, Timestamp createdAt);

    /**
     * 제재 삭제. 성공 시 true 반환, 그 외 모든 경우에는 false 반환.
     * @return boolean
     * @author 정순석
     */
    boolean deleteSanction(String userId);
}
