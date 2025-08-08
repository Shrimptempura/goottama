package com.ama.don.interior.dao;

import com.ama.don.common.dto.FileDto;
import com.ama.don.interior.dto.company.*;
import com.ama.don.interior.dto.company.CompanyDetailDto;
import com.ama.don.interior.dto.company.CompanySummaryDto;
import com.ama.don.member.dto.JoinformDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

// 업체 자체에 대한 dao
@Mapper
public interface CompanyDao {

    // === create ===============================================
    /**
     * [업체 등록 흐름]
     * 1. 업체 이름 중복 검사 (isDuplicateCompanyName)
     * 2. 업체 상세 정보 등록 (company_detail)              -> insertCompanyDetail()
     * 3. 업체 위치 등록 (location)                         -> insertLocation()
     *      - 일정 변경으로 거의 사용안함 생성은 필수
     * 4. 업체 테이블 생성 (company)                        -> insertCompany()
     *      - 외래키: userId, companyDetailId, locationId
     * 5. 이미지 1장 업로드 필수 (FileService)
     * - 업체 생성 허락은 관리자 또는 바로 생성, 권한이 바뀜 100 -> 200
     */
    // 업체 상세 정보 등록 (company_detail)
    void insertCompanyDetail(CompanyCreateDto dto);

    // 위치 위치 정보 등록 (location)
    void insertLocation(CompanyCreateLocationDto dto);

    // 업체 생성 (company)
    void insertCompany(CompanyInsertDto dto);

    // === read ==================================================
    // 홈에서 보는 업체 리스트 -> 불안정한 추후 확인 필요
    List<CompanyHomeDto> findCompanyHomeList(@Param("limit") int limit);

    // 업체 상세 페이지에서 보는 정보 탭
    CompanyDetailDto selectDetailCompany(@Param("companyId") Long companyId);

    // 업체 상세 페이지에서 보는 좌측 따라다니는 요약 정보 박스
    // 박스 안에 별점과 관련된 처리 필요
    CompanySummaryDto selectSummaryCompany(@Param("companyId") Long companyId);

    // 업체 상세페이지의 photos 탭, 사진 보여주기
    List<FileDto> selectCompanyPhoto(@Param("targetType") String targetType,
                                     @Param("targetId") Long targetId);

    // 회원아이디로 업체아이디 찾기
    Optional<Long> findCompanyIdByUserId(@Param("userId") Long userId);

    // 업체 이름 중복 검사
    Boolean isDuplicateCompanyName(@Param("companyName") String companyName);

    // 업체 id로 업체 이름 조회
    String getCompanyNameById(@Param("companyId") Long companyId);
    
    // 업체 수정 뷰
    CompanyUpdateDto getUpdateView(@Param("companyId") Long companyId);

    // === update ==================================================
    // 업체 정보 수정 (company_detail + file)
    int updateCompanyDetail(CompanyUpdateDto dto);

    // === delete ==================================================
    // 업체 탈퇴, 소프트 삭제 (company, is_deleted)
    int deleteCompany(@Param("companyId") Long companyId);




    // === utils ==================================================
    // 회원 생성 예시
    void insertUser(JoinformDto dto);

    // === reserved ==================================================
    // 업체의 주소가 바뀌면 companyDetail의 companyAddr를 위도경도로 api 사용하여 업데이트
    // 현재 사용 안함 향후 일정 봐서 사용
    int updateLocation(CompanyUpdateLocationDto dto);
    
}
