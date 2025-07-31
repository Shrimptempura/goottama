package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.*;
import com.ama.don.interior.dto.response.CompanyDetailDto;
import com.ama.don.interior.dto.response.CompanySummaryDto;
import com.ama.don.member.dto.JoinformDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

// 업체 자체에 대한 dao
@Mapper
public interface CompanyDao {

    // 회원 생성 예시
    void insertUser(JoinformDto dto);

    // 회원아이디로 업체아이디 찾기, 회원아이디로 업체 아이디가 없으면 optional
    Optional<Long> findCompanyIdByUserId(Long userId);

    // 업체 정보 등록 + 내부적으로 정보 등록하면서 권한등급(번호)를 회원->업체 변경
    // dto가 테이블 2개의 내용이므로, 먼저 company_detail 테이블 먼저 생성
    void insertCompanyDetail(CompanyCreateDto dto);

    // 업체 이름 중복 검사, insertCompanydetail에서 확인해야함
    Boolean isDuplicateCompanyName(String companyName);

    // 위치 정보 등록
    void insertLocation(CompanyCreateLocationDto dto);

    // company 테이블, pk: user_id, company_detail, location_id
    void insertCompany(CompanyInsertDto dto);

    // 업체 상세 정보 조회
    CompanyDetailDto selectDetailCompany(Long companyId);

    // 업체 요약 정보 조회(박스)
    // company_detail, company_score_avg
    CompanySummaryDto selectSummaryCompany(@Param("companyId") Long companyId);

    // 업체 정보 수정(detail + company + location(api->service)), file
    // company_detail table 수정(대부분)
    int updateCompanyDetail(CompanyUpdateDto dto);

    // 이미지는 다형성 file 사용

    // 업체의 주소가 바뀌면 company_addr를 가져다 위치 정보 업데이트
    int updateLocation(CompanyUpdateLocationDto dto);

    // 업체 탈퇴(is_deleted), company table
    int deleteCompany(Long companyId);

    // api location lat, lng, code 추후 변경 메서드 필요
}
