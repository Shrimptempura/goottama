package com.ama.don.admin.dao;

import com.ama.don.admin.dto.sanctionsDTO.SanctionSearchDTO;
import com.ama.don.admin.dto.sanctionsDTO.SanctionsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 제재(Sanctions) 관련 데이터베이스 접근을 정의한 MyBatis Mapper 인터페이스.
 * <p>
 * 기능: <br>
 * - 전체 제재 내역 조회 및 개수 <br>
 * - ID 기반 단일 조회 <br>
 * - 조건 기반 검색 및 검색 결과 개수 <br>
 * - 제재 생성, 수정, 삭제
 * </p>
 *
 * @author 정순석
 */
@Mapper
public interface SanctionsIDao {

    /**
     * 모든 제재 내역을 반환한다.
     *
     * @return 전체 제재 내역 목록 (List of SanctionsDTO)
     * @see #countAllSanctions()
     */
    List<SanctionsDTO> getAllSanctions();

    /**
     * 모든 제재 내역의 개수를 반환한다.
     *
     * @return 전체 제재 건수
     * @see #getAllSanctions()
     */
    int countAllSanctions();

    /**
     * 특정 유저의 모든 제재 내역을 반환한다.
     * @param userId
     * @return 제재 내역 목록 (List of SanctionsDTO)
     */
    List<SanctionsDTO> getSanctionsByUserId(String userId);

    /**
     * 제재 ID로 단일 제재 내역을 조회한다.
     *
     * @param sanctionsId 조회할 제재의 고유 ID
     * @return 해당 제재의 정보 (없으면 null)
     * @see #getAllSanctions()
     */
    SanctionsDTO getSanctionsById(String sanctionsId);

    /**
     * 조건에 따라 제재 내역을 검색한다.
     *
     * @param sanctionSearchDTO 검색 조건이 담긴 객체 <br>
     *                         - userId: 대상 사용자 ID <br>
     *                         - sanctionType: 제재 유형 <br>
     *                         - adminId: 제재한 관리자 ID <br>
     *                         - reason: 사유 키워드 <br>
     *                         - startDate ~ endDate: 제재 시작/종료일
     * @return 검색 결과 목록
     * @see #countSearchSanctions(SanctionSearchDTO)
     */
    List<SanctionsDTO> searchSanctions(SanctionSearchDTO sanctionSearchDTO);

    /**
     * 조건 검색 결과의 개수를 반환한다.
     *
     * @param sanctionSearchDTO 검색 조건 객체
     * @return 검색 결과 수
     * @see #searchSanctions(SanctionSearchDTO)
     */
    int countSearchSanctions(SanctionSearchDTO sanctionSearchDTO);

    /**
     * 새로운 제재 내역을 생성한다.
     *
     * @return 성공 시 true, 실패 시 false
     */
    boolean makeSanction(SanctionsDTO sanctionsDto);

    /**
     * 기존 제재 내역을 수정한다.
     *
     * @return 성공 시 true, 실패 시 false
     */
    boolean modifySanction(SanctionsDTO sanctionsDto);

    /**
     * 대상 사용자의 제재 내역을 삭제한다.
     *
     * @param userId 삭제할 대상 사용자 ID
     * @return 성공 시 true, 실패 시 false
     */
    boolean deleteSanction(String userId);
}