package com.ama.don.interior.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyFollowDao {

    // 로그인 후 팔로우 여부 확인, 비로그인 시 아에 자바에서 세션 확인으로 false 처리
    Boolean isFollowedCompany(@Param("companyId") Long companyId,
                              @Param("userId") Long userId);
}
