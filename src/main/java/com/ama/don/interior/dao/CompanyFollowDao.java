package com.ama.don.interior.dao;

import com.ama.don.interior.dto.follow.CompanyFollowDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyFollowDao {
    // 시간되면 추후 company_post에 like_count로 redis처리
    // follow 수정 필요

    // 회원 -> 업체에게 팔로우 등록
    void insertFollowCompany(CompanyFollowDto companyFollowDto);

    // 팔로우 취소
    void deleteFollowCompany(CompanyFollowDto companyFollowDto);

    // 로그인 후 팔로우 여부 확인, 비로그인 시 아에 자바에서 세션 확인으로 false 처리
    Boolean isFollowedCompany(@Param("companyId") Long companyId,
                              @Param("userId") Long userId);

    // 업체의 총 팔로워 수
    int getFollowCompanyCount(@Param("companyId") Long companyId);
}
