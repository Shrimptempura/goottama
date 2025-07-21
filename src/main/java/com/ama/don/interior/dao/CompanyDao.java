package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.interior.dto.request.CompanyUpdateDto;
import com.ama.don.interior.dto.response.CompanyDetailDto;
import com.ama.don.interior.dto.response.CompanySummaryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

// 업체 자체에 대한 dao
@Mapper
public interface CompanyDao {
    // 업체 정보 등록 + 내부적으로 정보 등록하면서 권한등급(번호)를 회원->업체 변경
    void insertCompanyDetail(CompanyCreateDto dto);

    void insertCompany(CompanyInsertDto dto);

    // 업체 이름 중복 검사, insertCompanyInfo에서 확인해야함
    Boolean isDuplicateCompanyName(String companyName);

    // 업체 상세 정보 조회
    CompanyDetailDto selectDetailCompany(Long companyId);

    // 업체 요약 정보 조회(박스)
    CompanySummaryDto selectSummaryCompany(Long companyId);

    // 업체 정보 수정
    void updateCompany(CompanyUpdateDto dto);

    // 업체 탈퇴(실제 지우기보단 status 또는 is_active, is_deleted 사용 생각)
    void deleteCompany(Long companyId);

    // 회원아이디로 업체아이디 찾기, 회원아이디로 업체 아이디가 없으면 optional
    Optional<Long> findCompanyIdByUserId(Long userId);
}
