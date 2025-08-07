package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyDao;
import com.ama.don.interior.dto.company.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;
    private final FileService fileService;

    @Transactional
    @Override
    public void createCompany(Long userId, CompanyCreateDto createDto, CompanyCreateLocationDto locationDto,
                              MultipartFile file) {

        log.info("ComapnyService - 업체 등록 시작 - userId: {}", userId);

        try {
            validateCompanyNameDuplication(createDto.getCompanyName());

            Long companyDetailId = createCompanyDetail(createDto);          // company_detail 테이블 insert
            Long locationId = createLocation(locationDto);                  // location 테이블 insert
            Long companyId = insertCompany(userId, companyDetailId, locationId);    // company 테이블 insert

            saveCompanyImg(companyId, file);

            log.info("CompanyService - 업체 등록 성공 - companyId: {}", companyId);
        } catch (DataAccessException e) {
            log.error("CompanyService - DB 오류 발생", e);
            throw new IllegalStateException("DB 오류 발생으로 업체 등록 실패");
        }
    }

    @Override
    public Optional<Long> findCompanyIdByUserId(Long userId) {
        return companyDao.findCompanyIdByUserId(userId);
    }

    // 업체 페이지내 상세 정보
    @Override
    public CompanyDetailDto getDetailCompany(Long companyId) {
        return companyDao.selectDetailCompany(companyId);
    }

    // 업체 페이지 내 좌측 요약 정보 박스
    @Override
    public CompanySummaryDto getSummaryCompany(Long companyId) {
        return companyDao.selectSummaryCompany(companyId);
    }

    @Override
    public CompanyUpdateDto getUpdateView(Long companyId) {
        if (companyId == null) {
            log.warn("CompanyService - 업체 수정 뷰 오류 - companyId is null, companyId: {}", companyId);
            throw new IllegalArgumentException("companyId가 없습니다.");
        }

        CompanyUpdateDto dto = companyDao.getUpdateView(companyId);
        if (dto == null) {
            log.warn("CompanyService - 업체 수정 뷰 오류 - 수정 정보 없음, companyId: {} ", companyId);
            throw new IllegalStateException("업체 정보가 없습니다.");
        }

        return dto;
    }

    // 업체 수정
    @Override
    public void updateCompany(CompanyUpdateDto updateDto, Long companyId, MultipartFile file) {
        log.info("CompanyService - 업체 정보 수정 시작 - companyId: {}", companyId);

        try {
            if (companyId == null || updateDto == null) {
                log.error("CompanyService - companyId 또는 updatDto 누락 - companyId: {}, updateDto: {}", companyId, updateDto);
                throw new IllegalArgumentException("companyId 또는 수정할 데이터가 없습니다.");
            }

            String newName = updateDto.getCompanyName();
            String originName = companyDao.getCompanyNameById(companyId);

            if (updateDto.getCompanyName() == null) {
                log.error("CompanyService - 업체 이름 누락 - companyId: {}", companyId);
                throw new IllegalArgumentException("회사 이름은 필수입니다.");
            }

            if (!originName.equals(newName) && companyDao.isDuplicateCompanyName(newName) ) {
                log.warn("CompanyService - 업체명 중복 - companyName: {}", updateDto.getCompanyName());
                throw new IllegalArgumentException("이미 등록된 이름입니다.");
            }

            int updated = companyDao.updateCompanyDetail(updateDto);
            if (updated == 0) {
                log.warn("CompanyService - 수정된 업체 정보가 없습니다. 실패 - companyId: {}", companyId);
                throw new IllegalStateException("업체 정보 수정 실패");
            }
            // 사진이 존재할때만 교체가능
            updateCompanyFile(companyId, file);

            log.info("CompanyService - 업체 수정 성공 - companyId: {}", companyId);
        } catch (DataAccessException e) {
            log.error("CompanyService - DB 오류, 업체 수정 실패 - companyId: {}", companyId, e);
            throw new IllegalStateException("DB오류 발생, 업체 수정 실패");
        }
    }

    private void validateCompanyNameDuplication(String name) {
        if (companyDao.isDuplicateCompanyName(name)) {
            log.warn("CompanyService - 업체명 중복 - companyName: {}", name);
            throw new IllegalArgumentException("이미 등록된 이름입니다.");
        }
    }

    private Long createCompanyDetail(CompanyCreateDto dto) {
        companyDao.insertCompanyDetail(dto);
        Long companyDetailId = dto.getCompanyDetailId();
        if (companyDetailId == null) {
            log.error("CompanyService - DB저장 실패, companyDetailId is null - dto: {}", dto);
            throw new IllegalStateException("companyDetailId에 값이 안들어옴");
        }
        return companyDetailId;
    }

    private Long createLocation(CompanyCreateLocationDto dto) {
        companyDao.insertLocation(dto);
        Long locationId = dto.getLocationId();
        if (locationId == null) {
            log.error("CompanyService - DB저장 실패, locationId is null - dto: {}", dto);
            throw new IllegalStateException("locationId에 값이 안들어옴");
        }
        return locationId;
    }

    private Long insertCompany(Long userId, Long companyDetailId, Long locationId) {
        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(userId);
        dto.setCompanyDetailId(companyDetailId);
        dto.setLocationId(locationId);

        companyDao.insertCompany(dto);

        Long companyId = dto.getCompanyId();
        if (companyId == null) {
            log.error("CompanyService - 업체 등록 실패 - companyId is null");
            throw new IllegalStateException("companyId가 없음, 업체 등록 실패");
        }
        return companyId;
    }

    private void saveCompanyImg(Long companyId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("CompanyService - 업체 이미지 필요 - companyId: {}", companyId);
            throw new IllegalArgumentException("1장의 이미지는 필수");
        }
        fileService.saveFile(TargetType.INTERIOR, companyId, file);
        log.info("CompanyService - 업체 이미지 저장 성공 - companyId: {}", companyId);
    }

    private void updateCompanyFile(Long companyId, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            log.info("CompanyService - 업체 이미지 삭제 후 새로 생성 - companyId: {}", companyId);
            fileService.deleteFile(companyId);
            fileService.saveFile(TargetType.INTERIOR, companyId, file);
        }
    }


}
