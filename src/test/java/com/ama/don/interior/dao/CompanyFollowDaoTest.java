package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyCreateDto;
import com.ama.don.interior.dto.request.CompanyCreateLocationDto;
import com.ama.don.interior.dto.request.CompanyFollowDto;
import com.ama.don.interior.dto.request.CompanyInsertDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyFollowDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyFollowDao companyFollowDao;

    @DisplayName("회원이 업체 팔로우, 테이블 생성")
    @Test
    void insertFollowCompany() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);
        Long companyId = dto.getCompanyId();

        JoinformDto otherUser = createTestUser("otherUser111");
        CompanyFollowDto followDto = new CompanyFollowDto();
        followDto.setCompanyId(companyId);
        followDto.setUserId(otherUser.getUserId());

        companyFollowDao.insertFollowCompany(followDto);

        assertThat(followDto.getFollowId()).isNotNull();
    }

    @DisplayName("회원이 특정 업체 팔로우 취소")
    @Test
    void deleteFollowCompany() {
        // 업체 2개 생성
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        JoinformDto otherUser = createTestUser("otherUser111");
        CompanyCreateDto otherDetail = createTestCompanyDetail("otherCompany");
        CompanyCreateLocationDto otherLocation = createTestLocation();

        CompanyInsertDto otherDto = new CompanyInsertDto();
        otherDto.setUserId(otherUser.getUserId());
        otherDto.setCompanyDetailId(otherDetail.getCompanyDetailId());
        otherDto.setLocationId(otherLocation.getLocationId());

        companyDao.insertCompany(otherDto);

        // 팔로우 테스트 회원
        JoinformDto thirdUser = createTestUser("thirdUser111");

        // 2개의 업체에 팔로우
        CompanyFollowDto followDto = new CompanyFollowDto();
        followDto.setUserId(thirdUser.getUserId());
        followDto.setCompanyId(dto.getCompanyId());

        companyFollowDao.insertFollowCompany(followDto);

        CompanyFollowDto secondFollowDto = new CompanyFollowDto();
        secondFollowDto.setUserId(thirdUser.getUserId());
        secondFollowDto.setCompanyId(otherDto.getCompanyId());

        companyFollowDao.insertFollowCompany(secondFollowDto);

        // 2개의 업체 팔로우 확인
        assertThat(followDto.getFollowId()).isNotNull();
        assertThat(followDto.getUserId()).isEqualTo(thirdUser.getUserId());

        assertThat(secondFollowDto.getFollowId()).isNotNull();
        assertThat(secondFollowDto.getUserId()).isEqualTo(thirdUser.getUserId());

        // 2개 업체 팔로우 취소
        companyFollowDao.deleteFollowCompany(followDto);
        companyFollowDao.deleteFollowCompany(secondFollowDto);

        assertThat(companyFollowDao.isFollowedCompany(dto.getCompanyId(), thirdUser.getUserId())).isFalse();
        assertThat(companyFollowDao.isFollowedCompany(otherDto.getCompanyId(), thirdUser.getUserId())).isFalse();
    }

    @DisplayName("로그인 한 회원이 업체를 팔로우 한 경우")
    @Test
    void isFollowedCompanyReturnTrueWhenFollowed() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());
        dto.setCompanyImg("images/interior/test.img");

        companyDao.insertCompany(dto);
        Long companyId = dto.getCompanyId();

        // 회원과 업체는 팔로우 상태
        CompanyFollowDto followDto = new CompanyFollowDto();
        followDto.setUserId(user.getUserId());
        followDto.setCompanyId(companyId);

        companyFollowDao.insertFollowCompany(followDto);

        Boolean result = companyFollowDao.isFollowedCompany(companyId, user.getUserId());

        assertThat(result).isTrue();
    }

    @DisplayName("로그인 한 회원이 업체를 팔로우 하지 않은 경우")
    @Test
    void isFollowedCompanyReturnFalseWhenNotFollowed() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());
        dto.setCompanyImg("images/interior/test.img");

        companyDao.insertCompany(dto);
        Long companyId = dto.getCompanyId();

        // 팔로우하지 않은 다른 유저
        JoinformDto otherUser = createTestUser("otherUser111");

        Boolean result = companyFollowDao.isFollowedCompany(companyId, otherUser.getUserId());

        assertThat(result).isFalse();
    }

}