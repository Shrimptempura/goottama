package com.ama.don.interior.service;

import com.ama.don.interior.dto.company.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CompanyService {

    /**
     * 회원이 업체 등록
     * @param companyCreateDto 업체 생성 dto
     * @param locationDto 업체 주소 생성 dto
     */
    void createCompany(CompanyCreateDto companyCreateDto, CompanyCreateLocationDto locationDto,
                       MultipartFile file);

    Optional<Long> findCompanyIdByUserId(Long userId);

    // 업체 상세페이지 내 정보 조회
    CompanyDetailDto getDetailCompany(Long companyId);

    // 업체 상세페이지 내 왼쪽 요약 상자
    CompanySummaryDto getSummaryCompany(Long companyId);

    CompanyUpdateDto getUpdateView(Long companyId);

    // 업체 수정
    Long updateCompany(CompanyUpdateDto updateDto, MultipartFile file);
}
