package com.ama.don.interior.dto.service;

import com.ama.don.interior.dao.CompanyDao;
import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
import com.ama.don.interior.dto.company.CompanyInsertDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;

    @Transactional
    @Override
    public void createCompany(Long userId, CompanyCreateDto createDto, CompanyCreateLocationDto locationDto) {
        log.info("ComapnyService - 업체 등록 시작 - userId: {}", userId);

        if (companyDao.isDuplicateCompanyName(createDto.getCompanyName())) {
            log.warn("CompanyService - 업체명 중복 - companyName: {}", createDto.getCompanyName());
            throw new IllegalArgumentException("이미 등록된 이름입니다.");
        }

        // company_detail 테이블 생성
        companyDao.insertCompanyDetail(createDto);
        Long companyDetailId = createDto.getCompanyDetailId();
        if (companyDetailId == null) {
            log.error("CompanyService - DB저장 실패, companyDetailId is Null - 등록 DTO: {}", createDto);
            throw new IllegalStateException("companyDetailId에 값이 안들어옴");
        }

        companyDao.insertLocation(locationDto);
        Long locationId = locationDto.getLocationId();
        if (locationId == null) {
            log.error("CompanyService - DB저장 실패, locationId is Null - 등록 DTO: {}", locationDto);
            throw new IllegalStateException("locationId에 값이 안들어옴");
        }

        // company 테이블의 fk값 더하기, userId, companyDetailId, locationId
        CompanyInsertDto insertDto = new CompanyInsertDto();
        insertDto.setUserId(userId);
        insertDto.setCompanyDetailId(companyDetailId);
        insertDto.setLocationId(locationId);

        companyDao.insertCompany(insertDto);

        // file
        Long companyId = insertDto.getCompanyId();
        // companyId null 확인



    }
}
