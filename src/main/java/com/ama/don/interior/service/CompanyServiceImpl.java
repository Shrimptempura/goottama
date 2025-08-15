package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyDao;
import com.ama.don.interior.dev.DevFindTarget;
import com.ama.don.interior.dto.company.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;
    private final FileService fileService;
    private final CompanyAuthService companyAuthService;

    @Transactional
    @Override
    public void createCompany(CompanyCreateDto createDto, CompanyCreateLocationDto locationDto,
                              MultipartFile file) {
        Long userId = companyAuthService.getLoginUserId();
        Long companyId = null;
        log.info("ComapnyService - 업체 등록 시작 - userId: {}", userId);

        try {
            validateCompanyNameDuplication(createDto.getCompanyName());

            Long companyDetailId = createCompanyDetail(createDto);          // company_detail 테이블 insert
            Long locationId = createLocation(locationDto);                  // location 테이블 insert
            companyId = insertCompany(userId, companyDetailId, locationId);    // company 테이블 insert

            saveCompanyImg(companyId, file);

            log.info("CompanyService - 업체 등록 성공 - companyId: {}", companyId);
        } catch (Exception e) {
            cleanUpFileCatch(companyId);
            log.error("CompanyService - 업체 등록 실패 - companyId: {}", companyId, e);
            throw new IllegalStateException("업체 등록 실패", e);
        }
    }

    // 업체 페이지내 상세 정보
    @Transactional(readOnly = true)
    @Override
    public CompanyDetailDto getDetailCompany(Long companyId) {
        return companyDao.selectDetailCompany(companyId);
    }

    // 업체 페이지 내 좌측 요약 정보 박스
    @Transactional(readOnly = true)
    @Override
    public CompanySummaryDto getSummaryCompany(Long companyId) {
        return companyDao.selectSummaryCompany(companyId);
    }

    // 인테리어 홈의 업체 랜덤 리스트, 개수 제한은 controller에서 건내줌
    @Transactional(readOnly = true)
    @Override
    public List<CompanyHomeDto> getHomeCompanyList(int limit) {
        return companyDao.findCompanyHomeList(limit);
    }
    
    // 업체 업데이트 전 정보 확인
    @Transactional(readOnly = true)
    @Override
    public CompanyUpdateDto getMyCompanyUpdateView() {
        Long companyId = companyAuthService.requireMyCompanyId();
        return companyDao.getUpdateView(companyId);
    }

    // 업체 수정
    @Transactional
    @Override
    public Long updateCompany(CompanyUpdateDto updateDto, MultipartFile file) {
        Long companyId = companyAuthService.requireMyCompanyId();
        log.info("CompanyService - 업체 정보 수정 시작 - companyId: {}", companyId);

        if (updateDto == null) {
            log.error("CompanyService - updateDto 누락 - updateDto: {}", updateDto);
            throw new IllegalArgumentException("companyId 또는 수정할 데이터가 없습니다.");
        }

        String newName = updateDto.getCompanyName();
        if (newName == null) {
            log.error("CompanyService - 업체 이름 누락 - companyId: {}", companyId);
            throw new IllegalStateException("회사 이름은 필수입니다.");
        }

        try {
            String originName = companyDao.getCompanyNameById(companyId);
            if (originName == null) {
                log.error("CompanyService - 업체 원본 이름 조회 실패 - companyId: {}", companyId);
                throw new IllegalStateException("업체 정보 조회 실패. companyId: " + companyId);
            }

            if (!originName.equals(newName) && companyDao.isDuplicateCompanyName(newName) ) {
                log.warn("CompanyService - 업체명 중복 - companyName: {}", updateDto.getCompanyName());
                throw new IllegalArgumentException("이미 등록된 이름입니다.");
            }

            updateDto.setCompanyId(companyId);
            int updated = companyDao.updateCompanyDetail(updateDto);
            if (updated == 0) {
                log.warn("CompanyService - 수정된 업체 정보가 없습니다. 실패 - companyId: {}", companyId);
                throw new IllegalStateException("업체 정보 수정 실패");
            }
            // 사진이 존재할때만 교체가능
            updateCompanyFile(companyId, file);

            log.info("CompanyService - 업체 수정 성공 - companyId: {}", companyId);
            return companyId;

        } catch (Exception e) {
            cleanUpFileCatch(companyId);
            log.error("CompanyService - 업체 수정 실패 - companyId: {}", companyId, e);
            throw new IllegalStateException("업체 수정 실패", e);
        }
    }

    // 업체 탈퇴(소프트 삭제)
    @Transactional
    @Override
    public int deleteCompany() {
        Long companyId = companyAuthService.requireMyCompanyId();
        log.info("CompanyService - 업체 소프트 삭제 시작 - companyId: {}", companyId);

        try {
            int updated = companyDao.deleteCompany(companyId);
            if (updated == 0) {
                throw new IllegalStateException("잘못된 삭제 companyId: " + companyId);
            }
            log.info("CompanyService - 업체 소프트 삭제 성공 - companyId: {}, updated: {}", companyId, updated);
            return updated;

        } catch (Exception e) {
            cleanUpFileCatch(companyId);
            log.error("CompanyService - 업체 삭제 실패 - companyId: {}", companyId, e);
            throw new IllegalStateException("업체 삭제 실패", e);
        }
    }

    // ------------------------------------------------------------------------
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
        fileService.saveFile(TargetType.INTERIOR, companyId, file, true);
        log.info("CompanyService - 업체 이미지 저장 성공 - companyId: {}", companyId);
    }

    // 저장 실패시 이미지 삭제후 null 되는 위험성 있음
    private void updateCompanyFile(Long companyId, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            log.info("CompanyService - 업체 이미지 삭제 후 새로 생성 - companyId: {}", companyId);
            fileService.deleteThumbnail(TargetType.INTERIOR, companyId);
            fileService.saveFile(TargetType.INTERIOR, companyId, file, true);
        }
    }

    // catch 보상 정리
    private void cleanUpFileCatch(Long companyId) {
        if (companyId != null) {
            try {
                fileService.deleteThumbnail(TargetType.INTERIOR, companyId);
            } catch (Exception failed) {
                log.warn("CompanyService - 파일 정리 실패 - companyId: {}", companyId, failed);
            }
        }
    }
}
