package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.*;
import com.ama.don.interior.dto.response.CompanyDetailDto;
import com.ama.don.interior.dto.response.CompanySummaryDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyDao companyDao;

    @DisplayName("company_detail 테이블 생성 테스트")
    @Test
    void insertCompanyDetail() {
        CompanyCreateDto detail = createTestCompanyDetail();
        companyDao.insertCompanyDetail(detail);

        assertThat(detail.getCompanyDetailId()).isNotNull();
    }

    @DisplayName("location 테이블에서 주소만 확인")
    @Test
    void insertLocation() {
        CompanyCreateLocationDto location = createTestLocation();

        companyDao.insertLocation(location);

        assertThat(location.getLocationId()).isNotNull();
    }

    @DisplayName("여러 테이블에서 fk를 받아 생성한 company 테이블")
    @Test
    void insertCompany() {
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();

        assertThat(dto.getCompanyId()).isNotNull();
    }


    @DisplayName("중복 없이 통과조건")
    @Test
    void isDuplicateCompanyName() {
        String otherCompanyName = "중복없는업체이름";
        Boolean isDuplicate = companyDao.isDuplicateCompanyName(otherCompanyName);

        assertThat(isDuplicate).isFalse();
    }

    @DisplayName("중복된 이름 실패조건")
    @Test
    void isDuplicateCompanyNameFail() {
        String duplicateCompanyName = "중복업체이름";
        companyDao.insertCompanyDetail(createTestCompanyDetail(duplicateCompanyName));

        Boolean isDuplicate = companyDao.isDuplicateCompanyName(duplicateCompanyName);

        assertThat(isDuplicate).isTrue();
    }

    @DisplayName("로그인된 user_id로 company_id 찾기")
    @Test
    void findCompanyIdByUserId() {
        // given
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        Long expectedCompanyId = dto.getCompanyId();

        Optional<Long> result = companyDao.findCompanyIdByUserId(dto.getUserId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedCompanyId);
    }

    @DisplayName("회원이 업체가입안한 상태로 company_id 찾고 실패 경우")
    @Test
    void findCompanyIdByUserIdFail() {
        JoinformDto user = createTestUser();
        Optional<Long> companyId = companyDao.findCompanyIdByUserId(user.getUserId());

        assertThat(companyId).isEmpty();
    }

    @DisplayName("업체 상세정보의 CompanyDetailDto 보기")
    @Test
    void selectDetailCompany() {
        // given
        TestCompanyContext ctx = insertTestCompanyContext();

        // when
        CompanyDetailDto result = companyDao.selectDetailCompany(ctx.getCompanyId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCompanyId()).isEqualTo(ctx.getCompanyId());
        assertThat(result.getCompanyAddr()).isEqualTo(ctx.getDetail().getCompanyAddr());
        assertThat(result.getCompanyField()).isEqualTo(ctx.getDetail().getCompanyField());
        assertThat(result.getCompanyLicense()).isEqualTo(ctx.getDetail().getCompanyLicense());
        assertThat(result.getCompanyAs()).isEqualTo(ctx.getDetail().getCompanyAs());
        assertThat(result.getCompanyCareer()).isEqualTo(ctx.getDetail().getCompanyCareer());
    }

    @DisplayName("업체 상세페이지의 좌측 요약 정보 박스(정보 + 별점), 별점은" +
            "기입이 없을시 null 가능")
    @Test
    void selectSummaryCompany() {
        // given
        TestCompanyContext ctx = insertTestCompanyContext();

        // when
        CompanySummaryDto result = companyDao.selectSummaryCompany(ctx.getCompanyId());

        // then
        assertThat(result).isNotNull();     // 모든값이 null일때
        assertThat(result.getCompanyId()).isEqualTo(ctx.getCompanyId());
        assertThat(result.getCompanyName()).isEqualTo(ctx.getDetail().getCompanyName());
        assertThat(result.getCompanyIntro()).isEqualTo(ctx.getDetail().getCompanyIntro());
        assertThat(result.getCompanyAddr()).isEqualTo(ctx.getDetail().getCompanyAddr());
        assertThat(result.getCompanyLicense()).isEqualTo(ctx.getDetail().getCompanyLicense());
        assertThat(result.getCompanyField()).isEqualTo(ctx.getDetail().getCompanyField());
        assertThat(result.getCompanyRate()).isNull();
    }

    @DisplayName("업체 정보 수정에서 업체 이미지 수정")
    @Test
    void updateCompanyImage() {
        // 업체 생성, 사진 설정
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        dto.setCompanyImg("testCompanyImg");
        Long companyId = dto.getCompanyId();

        CompanyUpdateDto updateDto = new CompanyUpdateDto();
        updateDto.setCompanyId(companyId);
        updateDto.setCompanyImg("testCompanyImg2");

        int updateCount = companyDao.updateCompanyImg(updateDto);

        assertThat(updateCount).isEqualTo(1);
    }

    @DisplayName("업체 수정, company_deatil 테이블 수정")
    @Test
    void updateCompanyDetail() {
        CompanyInsertDto dto = insertTestCompanyWithUserLocationAndDetail();
        dto.setCompanyImg("testCompanyImg");
        Long companyDetailId = dto.getCompanyDetailId();

        CompanyUpdateDto updateDto = new CompanyUpdateDto();
        updateDto.setCompanyDetailId(companyDetailId);
        updateDto.setCompanyName("testCompanyName");
        updateDto.setCompanyAddr("testCompanyAddr");
        updateDto.setCompanyField("testCompanyField");
        updateDto.setCompanyLicense("testCompanyLicense");
        updateDto.setCompanyAs("testCompanyAs");
        updateDto.setCompanyCareer("testCompanyCareer");
        updateDto.setCompanyIntro("testCompanyIntro");

        int updateCount = companyDao.updateCompanyDetail(updateDto);

        assertThat(updateCount).isEqualTo(1);
    }

    @DisplayName("위치 테이블만 수정")
    @Test
    void updateLocation() {
        TestCompanyContext ctx = insertTestCompanyContext();

        Long companyId = ctx.getCompanyId();
        String companyAddr = ctx.getDetail().getCompanyAddr();

        CompanyUpdateDto updateDto = new CompanyUpdateDto();
        updateDto.setCompanyDetailId(companyId);
        updateDto.setCompanyAddr("testCompanyAddr");

        companyDao.updateCompanyDetail(updateDto);

        String updatedCompanyAddr = updateDto.getCompanyAddr();

        assertThat(updatedCompanyAddr).isNotEqualTo(companyAddr);

        CompanyUpdateLocationDto updateLocationDto = new CompanyUpdateLocationDto();
        updateLocationDto.setLocationId(ctx.getLocation().getLocationId());
        updateLocationDto.setLocationAddr("testCompanyAddr");

        assertThat(updateLocationDto.getLocationAddr()).isEqualTo("testCompanyAddr");
    }

}
