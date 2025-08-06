package com.ama.don.interior.service;

import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
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
}
