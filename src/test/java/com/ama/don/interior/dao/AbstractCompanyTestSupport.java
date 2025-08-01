package com.ama.don.interior.dao;

import com.ama.don.common.dao.ReviewDao;
import com.ama.don.common.dto.ReviewDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.*;
import com.ama.don.member.dto.JoinformDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractCompanyTestSupport {

    @Autowired
    protected CompanyDao companyDao;

    @Autowired
    protected ReviewDao reviewDao;

    @Autowired
    protected CompanyReviewDao companyReviewDao;

    protected JoinformDto createTestUser() {
        return createTestUser("테스트아이디");
    }

    protected JoinformDto createTestUser(String loginId) {
        JoinformDto dto = new JoinformDto();
        dto.setLoginId(loginId);
        dto.setPw("abcdefghi!@");
        dto.setPw2("abcdefghi!@");

        dto.setName("홍길동");
        dto.setNickname("테스트닉네임");
        dto.setGender(JoinformDto.Gender.M);
        dto.setBirth("1999-09-09");
        dto.setTel("010-1234-5678");

        dto.setZipcode("12345");
        dto.setAddr("서울특별시 구로구");
        dto.setDetailAddr("은마아파트 123동");
        dto.combineAddress();

        dto.setEmailId("abcdefg");
        dto.setEmailDomain("naver.com");
        dto.combineEmail();

        companyDao.insertUser(dto);

        return dto;
    }

    protected CompanyCreateDto createTestCompanyDetail() {
        return createTestCompanyDetail("업체이름");
    }

    protected CompanyCreateDto createTestCompanyDetail(String companyName) {
        CompanyCreateDto dto = new CompanyCreateDto();
        dto.setCompanyName(companyName);
        dto.setCompanyAddr("업체주소");
        dto.setCompanyField("업체필드");
        dto.setCompanyLicense("업체라이센스");
        dto.setCompanyAs("업체AS");
        dto.setCompanyCareer("업체경력");
        dto.setCompanyIntro("업체소개글");

        companyDao.insertCompanyDetail(dto);

        return dto;
    }

    protected CompanyCreateLocationDto createTestLocation() {
        CompanyCreateLocationDto dto = new CompanyCreateLocationDto();
        dto.setLocationAddr("서울특별시 구로구");

        companyDao.insertLocation(dto);

        return dto;
    }

    protected CompanyInsertDto insertTestCompanyWithUserLocationAndDetail() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        return dto;
    }

    protected CompanyInsertDto insertTestCompanyWithUserLocationAndDetail(String loginId, String companyName) {
        JoinformDto user = createTestUser(loginId);
        CompanyCreateDto detail = createTestCompanyDetail(companyName);
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        return dto;
    }

    protected TestCompanyContext insertTestCompanyContext() {
        JoinformDto user = createTestUser();
        CompanyCreateDto detail = createTestCompanyDetail();
        CompanyCreateLocationDto location = createTestLocation();

        CompanyInsertDto dto = new CompanyInsertDto();
        dto.setUserId(user.getUserId());
        dto.setCompanyDetailId(detail.getCompanyDetailId());
        dto.setLocationId(location.getLocationId());

        companyDao.insertCompany(dto);

        return new TestCompanyContext(user, detail, location, dto);
    }

    // 다형성 리뷰 + 업체 리뷰
    protected CreateReviewSet createPolyReviewAndCompanyReview() {
        // 업체 생성
        CompanyInsertDto companyDto = insertTestCompanyWithUserLocationAndDetail();
        Long companyId = companyDto.getCompanyId();
        Long userId = companyDto.getUserId();

        // 업체가 쓸 다형성 리뷰 생성
        ReviewDto dto = new ReviewDto();
        dto.setUserId(userId);
        dto.setTargetId(companyId);
        dto.setTargetType(TargetType.valueOf("INTERIOR"));
        dto.setReviewContent("다형성에 적는 리뷰 내용 테스트");

        reviewDao.insertPolyReview(dto);

        // 새 객체에서 default만 읽고 기존 dto에 넣어줌
        ReviewDto getDefault = reviewDao.selectDefaultFieldById(dto.getReviewId());
        dto.setReviewDate(getDefault.getReviewDate());
        dto.setReviewModify(getDefault.getReviewModify());

        // 업체 리뷰 생성
        CompanyReviewCreateDto companyReviewDto = new CompanyReviewCreateDto();
        companyReviewDto.setReviewId(dto.getReviewId());
        companyReviewDto.setCompanyId(companyId);
        companyReviewDto.setCommunicationRate(4);
        companyReviewDto.setPriceRate(4);
        companyReviewDto.setResultRate(5);
        companyReviewDto.setScheduleRate(5);

//        companyReviewDto.setReviewContent("여기는 업체 리뷰 내용 테스트");

//        List<String> reviewImgList = List.of("interior/images1", "interior/images2",
//                "interior/images3");
//        companyReviewDto.setReviewImg(reviewImgList);

        companyReviewDto.setStructureType("아파트 건물유형 테스트");
        companyReviewDto.setAreaPyeong("30평");
        companyReviewDto.setConstructionField("장판공사");

        companyReviewDao.insert(companyReviewDto);

        // context 방식
        CreateReviewSet result = new CreateReviewSet();
        result.setCompanyReviewDto(companyReviewDto);
        result.setCommonReviewDto(dto);

        return result;
    }
    
    // 다형성 건너띄고 업체 리뷰 강제 생성
    protected CompanyReviewCreateDto createCheckCompanyReview(Long companyId, Long reviewId) {
        CompanyReviewCreateDto dto = new CompanyReviewCreateDto();
        dto.setCompanyId(companyId);
        dto.setReviewId(reviewId);
        dto.setCommunicationRate(3);
        dto.setPriceRate(3);
        dto.setResultRate(3);
        dto.setScheduleRate(3);

        dto.setReviewContent("두번째 테스트 내용");

        dto.setStructureType("빌딩 테스트");
        dto.setAreaPyeong("34평");
        dto.setConstructionField("욕실 공사");

        return dto;
    }

    // 다형성 리뷰 생성
    protected ReviewDto createPolyReview(Long companyId) {
        JoinformDto user = createTestUser("otherUser222");
        ReviewDto dto = new ReviewDto();
        dto.setUserId(user.getUserId());
        dto.setTargetId(companyId);
        dto.setTargetType(TargetType.valueOf("INTERIOR"));
        dto.setReviewContent("다형성에 적는 리뷰 내용 테스트");

        reviewDao.insertPolyReview(dto);

        return dto;
    }

    // 업체 리뷰 수정 헬퍼
    protected CompanyReviewUpdateDto updateCompanyDto(Long reviewId) {
        CompanyReviewUpdateDto updateDto = new CompanyReviewUpdateDto();
        updateDto.setReviewId(reviewId);

        updateDto.setAreaPyeong("테스트");
        updateDto.setResultRate(9);
        updateDto.setCommunicationRate(8);
        updateDto.setScheduleRate(7);
        updateDto.setPriceRate(6);
        updateDto.setConstructionField("테스트");
        updateDto.setStructureType("테스트");

        return updateDto;
    }

}
