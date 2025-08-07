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

    // 회원 생성 예시
    void insertUser(JoinformDto dto);

    // 회원아이디로 업체아이디 찾기, 회원아이디로 업체 아이디가 없으면 optional
    Optional<Long> findCompanyIdByUserId(@Param("userId") Long userId);

    // 업체 정보 등록 + 내부적으로 정보 등록하면서 권한등급(번호)를 회원->업체 변경
    // dto가 테이블 2개의 내용이므로, 먼저 company_detail 테이블 먼저 생성
    void insertCompanyDetail(CompanyCreateDto dto);

    // 업체 이름 중복 검사, insertCompanydetail에서 확인해야함
    Boolean isDuplicateCompanyName(@Param("companyName") String companyName);

    // 위치 정보 등록
    void insertLocation(CompanyCreateLocationDto dto);

    // company 테이블, pk: user_id, company_detail, location_id
    void insertCompany(CompanyInsertDto dto);

    // homeDto dao, mapper 누락
    List<CompanyHomeDto> findCompanyHomeList();

    // 업체 상세 정보 조회
    CompanyDetailDto selectDetailCompany(@Param("companyId") Long companyId);

    // 업체 요약 정보 조회(박스)
    // company_detail, company_score_avg
    CompanySummaryDto selectSummaryCompany(@Param("companyId") Long companyId);

    // 업체 정보 수정(detail + company + location(api->service)), file
    // company_detail table 수정(대부분)
    int updateCompanyDetail(CompanyUpdateDto dto);

    // 이미지는 다형성 file 사용

    // 업체 상세페이지의 photos 탭 사진 보여주기
    List<FileDto> selectCompanyPhoto(@Param("targetType") String targetType,
                                   @Param("targetId") Long targetId);


    // 업체의 주소가 바뀌면 company_addr를 가져다 위치 정보 업데이트
    // 현재 사용 안함
    int updateLocation(CompanyUpdateLocationDto dto);

    // 업체 탈퇴(is_deleted), company table
    int deleteCompany(@Param("companyId") Long companyId);

    // api location -> 정렬로 대체
}
