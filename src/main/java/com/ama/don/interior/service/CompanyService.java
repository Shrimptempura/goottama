package com.ama.don.interior.service;

import com.ama.don.interior.dto.company.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CompanyService {

    /**
     * 회원이 업체 등록
     * @param userId 세션에서 받는 회원 ID
     * @param companyCreateDto 업체 생성 dto
     * @param locationDto 업체 주소 생성 dto
     */
    void createCompany(Long userId, CompanyCreateDto companyCreateDto, CompanyCreateLocationDto locationDto,
                       MultipartFile file);

    Optional<Long> findCompanyIdByUserId(Long userId);

    // 업체 상세페이지 내 정보 조회
    CompanyDetailDto getDetailCompany(Long companyId);

    // 업체 상세페이지 내 왼쪽 요약 상자
    CompanySummaryDto getSummaryCompany(Long companyId);

    CompanyUpdateDto getUpdateView(Long companyId);

    // 업체 수정
    void updateCompany(CompanyUpdateDto updateDto, Long companyId, MultipartFile file);
}
