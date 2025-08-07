package com.ama.don.interior.dao;

import com.ama.don.interior.dto.follow.CompanyFollowDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyFollowDao {
    // 추후 company_post의 like_count 칼럼 redis 처리 확인
    // 해당 팔로우는 숫자는 보여주지 않는다. 오로지 친구 의미

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
