package com.ama.don.interior.dao;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import com.ama.don.interior.dto.response.CompanyScoreAvgDto;
import com.ama.don.member.dto.JoinformDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyReviewDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyReviewDao companyReviewDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("리뷰 작성 전 공통리뷰 먼저 생성후 업체 리뷰 작성")
    @Test
    void insertCommonReview() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet result = createPolyReviewAndCompanyReview();
        Long companyReviewId = result.getCompanyReviewDto().getReviewId();
        Long commonReviewId = result.getCommonReviewDto().getReviewId();

        assertThat(companyReviewId).isNotNull();
        assertThat(commonReviewId).isNotNull();
        assertThat(companyReviewId).isEqualTo(commonReviewId);
    }

    // isExistScoreTable + insertScoreTable
    @DisplayName("리뷰 작성시 첫번째 리뷰면 생성해야 함")
    @Test
    void insertCompanyReview() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();

        // 무조건 테이블이 비어있다고 가정
        jdbcTemplate.update("DELETE FROM company_score_avg WHERE company_id = ?", companyId);

        companyReviewDao.insertScoreTable(dto.getCompanyReviewDto());

        boolean exists = companyReviewDao.isExistScoreTable(dto.getCompanyReviewDto().getCompanyId());

        Double avg =  jdbcTemplate.queryForObject(
                "SELECT avg_communication FROM company_score_avg WHERE company_id = ?",
                Double.class, companyId
        );

        assertThat(exists).isTrue();
        assertThat(avg).isEqualTo(dto.getCompanyReviewDto().getCommunicationRate());
    }


    @DisplayName("업체 리뷰후 평균 점수 update 확인")
    @Test
    void updateCompanyReviewAvgScore () {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();
        
        companyReviewDao.insertScoreTable(dto.getCompanyReviewDto());

        // 첫번째 리뷰의 계산으로 얻은 총 별점
        CompanyScoreAvgDto avgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(avgDto).isNotNull();
        double firstAvg = avgDto.getAvgTotalRate();

        // 다른 회원 다형성 리뷰 작성
        ReviewDto reviewDto = createPolyReview(companyId);
        Long reviewId = reviewDto.getReviewId();

        // 다른 회원 업체 리뷰 작성
        CompanyReviewCreateDto forceReviewDto = createCheckCompanyReview(companyId, reviewId);

        int secondReview = companyReviewDao.insertCompanyReview(forceReviewDto);
        assertThat(secondReview).isEqualTo(1);

        int updated = companyReviewDao.updateScoreAvg(forceReviewDto);
        assertThat(updated).isEqualTo(1);

        // 첫번째 + 두번째 리뷰의 평균 뽑기
        CompanyScoreAvgDto SecondAvgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(SecondAvgDto).isNotNull();
        double secondAvg = SecondAvgDto.getAvgTotalRate();

        System.out.println("firstAvg: " + firstAvg);
        System.out.println("secondAvg: " + secondAvg);

        assertThat(firstAvg).isNotEqualTo(secondAvg);
    }

    @DisplayName("업체 점수 테이블 점수 확인")
    @Test
    void getAvgScoreByCompanyId() {

    }

}