package com.ama.don.interior.service;

import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
import com.ama.don.interior.dto.company.CompanyDetailDto;
import com.ama.don.interior.dto.company.CompanySummaryDto;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {

    /**
     * 회원이 업체 등록
     * @param userId 세션에서 받는 회원 ID
     * @param companyCreateDto 업체 생성 dto
     * @param locationDto 업체 주소 생성 dto
     */
    void createCompany(Long userId, CompanyCreateDto companyCreateDto, CompanyCreateLocationDto locationDto,
                       MultipartFile file);

    // 업체 상세페이지 내 정보 조회
    CompanyDetailDto selectDetailCompany(Long companyId);

    // 업체 상세페이지 내 왼쪽 요약 상자
    CompanySummaryDto selectSummaryCompany(Long companyId);
}
