package com.ama.don.interior.service;

import java.util.Optional;

public interface CompanyAuthService {
    
    // 로그인한 userId 찾기
    Long getLoginUserId();

    // userId로 companyId 찾기, 없을수도 있음
    Optional<Long> findMyCompanyId();

    // userId로 companyId 찾기, 무조건 있어야 함
    Long requireMyCompanyId();
    
    // 본인 확인
    boolean isOwner(Long companyId);

}
