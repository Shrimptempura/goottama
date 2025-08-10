package com.ama.don.interior.service;

import com.ama.don.interior.dao.CompanyDao;
import com.ama.don.interior.dev.DevFindTarget;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyAuthServiceImpl implements CompanyAuthService {

    private final CompanyDao companyDao;

    @Transactional(readOnly = true)
    @Override
    public Optional<Long> findMyCompanyId() {
        Long userId = DevFindTarget.getUserId();
        return companyDao.findCompanyIdByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long requireMyCompanyId() {
        Long userId = DevFindTarget.getUserId();
        try {
            return companyDao.findCompanyIdByUserId(userId)
                    .orElseThrow(() -> {
                        log.warn("CompaynAuth - 나의 업체가 없습니다. userId: {}", userId);
                        return new IllegalStateException("업체가 없습니다. userId: " + userId);
                    });
        } catch (DataAccessException e) {
            log.error("CompaynAuth - companyId DB 오류 - userId: {}", userId, e);
            throw new IllegalStateException("companyId 조회 실패 - userId: " + userId, e);
        }
    }

    // 보고있는 페이지가 본인 것인지 확인
    @Transactional(readOnly = true)
    @Override
    public boolean isOwner(Long companyId) {
        if (companyId == null) {
            return false;
        }

        return findMyCompanyId()
                .filter(id -> Objects.equals(id, companyId))
                .isPresent();
    }



}
