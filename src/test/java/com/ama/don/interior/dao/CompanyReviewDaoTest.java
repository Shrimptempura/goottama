package com.ama.don.interior.dao;

import com.ama.don.common.dao.ReviewDao;
import com.ama.don.common.dto.ReviewDto;
import com.ama.don.interior.dto.request.CompanyReviewCreateDto;
import com.ama.don.interior.dto.request.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.request.CompanyScoreAdjustDto;
import com.ama.don.interior.dto.response.CompanyHomeReviewDto;
import com.ama.don.interior.dto.response.CompanyReviewDto;
import com.ama.don.interior.dto.response.CompanyScoreAvgDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CompanyReviewDaoTest extends AbstractCompanyTestSupport {

    @Autowired
    CompanyReviewDao companyReviewDao;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private ReviewDao reviewDao;

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
    void insert() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();

        // 무조건 테이블이 비어있다고 가정
        jdbcTemplate.update("DELETE FROM company_score_avg WHERE company_id = ?", companyId);

        companyReviewDao.createScoreTable(dto.getCompanyReviewDto());

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
    void softDeleteCompanyReviewAvgScore() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();
        
        companyReviewDao.createScoreTable(dto.getCompanyReviewDto());

        // 첫번째 리뷰의 계산으로 얻은 총 별점
        CompanyScoreAvgDto avgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(avgDto).isNotNull();
        double firstAvg = avgDto.getAvgTotalRate();

        // 다른 회원 다형성 리뷰 작성
        ReviewDto reviewDto = createPolyReview(companyId);
        Long reviewId = reviewDto.getReviewId();

        // 다른 회원 업체 리뷰 작성
        CompanyReviewCreateDto forceReviewDto = createCheckCompanyReview(companyId, reviewId);

        int secondReview = companyReviewDao.insert(forceReviewDto);
        assertThat(secondReview).isEqualTo(1);

        int updated = companyReviewDao.addScoreOnCreate(forceReviewDto);
        assertThat(updated).isEqualTo(1);

        // 첫번째 + 두번째 리뷰의 평균 뽑기
        CompanyScoreAvgDto SecondAvgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(SecondAvgDto).isNotNull();
        double secondAvg = SecondAvgDto.getAvgTotalRate();

        System.out.println("firstAvg: " + firstAvg);
        System.out.println("secondAvg: " + secondAvg);

        assertThat(firstAvg).isNotEqualTo(secondAvg);
    }

    @DisplayName("업체 점수 테이블 평균 점수 꺼내기")
    @Test
    void getAvgScoreByCompanyId() {
        // 다형성 리뷰 + 업체 리뷰
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();

        companyReviewDao.createScoreTable(dto.getCompanyReviewDto());

        CompanyScoreAvgDto avgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(avgDto).isNotNull();

        double totalRate = avgDto.getAvgTotalRate();

        double result = avgDto.getAvgResult();
        double communication = avgDto.getAvgCommunication();
        double schedule = avgDto.getAvgSchedule();
        double price = avgDto.getAvgPrice();

        double total = result + communication + schedule + price;

        assertThat(totalRate).isEqualTo(total / 4);
        assertThat(result).isEqualTo(dto.getCompanyReviewDto().getResultRate());
        assertThat(communication).isEqualTo(dto.getCompanyReviewDto().getCommunicationRate());
    }

    @DisplayName("리뷰 상세보기")
    @Test
    void selectReviewDetail() {
        // 다형성 리뷰 + 업체 리뷰
        CreateReviewSet dto = createPolyReviewAndCompanyReview();

        CompanyReviewDto read = companyReviewDao.getReviewDetail(dto.getCompanyReviewDto().getReviewId());
        assertThat(read).isNotNull();

        assertThat(dto.getCompanyReviewDto().getConstructionField()).isEqualTo(read.getConstructionField());
        assertThat(dto.getCommonReviewDto().getUserId()).isEqualTo(read.getUserId());
        assertThat(read.getUserNickName()).isNotNull();
    }

    @DisplayName("홈에서 보는 업체리뷰 리스트들")
    @Test
    void findRecentForHome() {
        jdbcTemplate.update("DELETE FROM company_review");
        jdbcTemplate.update("DELETE FROM review");

        // 다형성 리뷰 + 업체 리뷰
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        List<CompanyHomeReviewDto> list = companyReviewDao.findRecentForHome();

        assertThat(list).isNotNull();
        assertThat(list.get(0).getReviewContent()).isEqualTo(dto.getCommonReviewDto().getReviewContent());
        assertThat(list.get(0).getStructureType()).isEqualTo(dto.getCompanyReviewDto().getStructureType());

        assertThat(list).hasSize(1);
    }

    @DisplayName("업체 상세페이지에서 보는 업체 리뷰 리스트들")
    @Test
    void getCompanyReviews() {
        // 다형성 리뷰 + 업체 리뷰
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();

        List<CompanyReviewDto> list = companyReviewDao.listByCompanyId(companyId);

        assertThat(list).isNotNull();
        assertThat(list.get(0).getReviewDate()).isEqualTo(dto.getCommonReviewDto().getReviewDate());
        assertThat(list.get(0).getReviewContent()).isEqualTo(dto.getCommonReviewDto().getReviewContent());
        assertThat(list.get(0).getUserNickName()).isNotNull();
    }

    @DisplayName("업체 리뷰 개수 구하기")
    @Test
    void countByCompanyId() {
        jdbcTemplate.update("DELETE FROM company_review");
        jdbcTemplate.update("DELETE FROM review");
        
        // 다형성 리뷰 + 업체 리뷰
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();

        int reviews = companyReviewDao.countByCompanyId(companyId);

        assertThat(reviews).isNotNull();
        assertThat(reviews).isEqualTo(1);
    }
    
    @DisplayName("다형성 리뷰에서 리뷰아이디로 해당 리뷰 있는지 확인용")
    @Test
    void isExistReviewId() {
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long reviewId = dto.getCommonReviewDto().getReviewId();
        Long userId = dto.getCommonReviewDto().getUserId();

        Boolean result = companyReviewDao.existByReviewIdAndUserId(reviewId, userId);

        assertThat(result).isTrue();
    }

    @DisplayName("하위 리뷰 소프트 삭제와 상위 리뷰 소프트 삭제 검증")
    @Test
    void updateSubReview() {
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long reviewId = dto.getCommonReviewDto().getReviewId();
        Long userId = dto.getCommonReviewDto().getUserId();

        // 상위 리뷰가 존재하는가
        Boolean result = companyReviewDao.existByReviewIdAndUserId(reviewId, userId);
        assertThat(result).isTrue();

        // 하위 리뷰 소프트 삭제
        companyReviewDao.softDeleteCompanyReview(reviewId);

        // 추가없이 간단하게 검증
        Boolean isSubReviewDeleted = jdbcTemplate.queryForObject(
                "SELECT is_deleted FROM company_review WHERE review_id = ?",
                Boolean.class, reviewId
        );
        assertThat(isSubReviewDeleted).isTrue();


        // 상위 리뷰 소프트 삭제
        companyReviewDao.softDeletePolyReview(reviewId, userId);

        // 추가없이 간단하게 검증
        Boolean isPolyReviewDeleted = jdbcTemplate.queryForObject(
                "SELECT is_deleted FROM review WHERE review_id = ? AND user_id = ?",
                Boolean.class, reviewId, userId
        );
        assertThat(isPolyReviewDeleted).isTrue();
    }

    @DisplayName("다형성 리뷰 수정(내용, 수정일)")
    @Test
    void updatePolyReview() throws InterruptedException {
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long reviewId = dto.getCommonReviewDto().getReviewId();
        Long userId = dto.getCommonReviewDto().getUserId();

        Boolean result = companyReviewDao.existByReviewIdAndUserId(reviewId, userId);
        assertThat(result).isTrue();

        // 기존 리뷰
        ReviewDto original = reviewDao.selectById(reviewId);
        Timestamp originDate = original.getReviewDate();
        String originContent = original.getReviewContent();

        String newContent = "이것은 새로운 리뷰 내용";
        dto.getCommonReviewDto().setReviewContent(newContent);

        // sleep 사용 실제로는 자제
        Thread.sleep(1000);

        // 실제 디비값이랑 비교하는건 아님 잘못된 예시
        // updated.setReviewModify(new Timestamp(System.currentTimeMillis() - 1000000));

        // Timestamp newModify = new Timestamp(System.currentTimeMillis() - 1000000);
        // 테스트 쿼리 추가 없이 간단한게 사용
//        jdbcTemplate.update(
//                "UPDATE review SET review_content = ?, review_modify = ? WHERE review_id = ?",
//                newContent, newModify, reviewId);

        // 다형성 업데이트
        companyReviewDao.updatePolyReview(dto.getCommonReviewDto());

        ReviewDto updated = reviewDao.selectById(reviewId);

        assertThat(updated.getReviewContent()).isNotEqualTo(originContent);
        assertThat(updated.getReviewModify()).isNotEqualTo(originDate);
    }

    @DisplayName("업체 리뷰를 수정")
    @Test
    void updateCompanyReview() {
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long reviewId = dto.getCommonReviewDto().getReviewId();
        Long userId = dto.getCommonReviewDto().getUserId();

        Boolean result = companyReviewDao.existByReviewIdAndUserId(reviewId, userId);
        assertThat(result).isTrue();

        // 수정 전 값
        String originArea = dto.getCompanyReviewDto().getAreaPyeong();

        // 업체 리뷰 수정
        CompanyReviewUpdateDto updateDto = updateCompanyDto(reviewId);
        // updateDto.setPriceRate(6);
        // updateDto.setAreaPyeong("테스트");

        int updated = companyReviewDao.updateCompanyReview(updateDto);
        assertThat(updated).isEqualTo(1);

        assertThat(updateDto.getPriceRate()).isEqualTo(6);
        assertThat(updateDto.getAreaPyeong()).isNotEqualTo(originArea);
    }

    @DisplayName("기존 리뷰 수정시 다시 점수 계산")
    @Test
    void recalculateCompanyScoreAfterReviewUpdate() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet dto = createPolyReviewAndCompanyReview();
        Long companyId = dto.getCompanyReviewDto().getCompanyId();
        Long reviewId = dto.getCommonReviewDto().getReviewId();

        companyReviewDao.createScoreTable(dto.getCompanyReviewDto());

        // 첫번째 리뷰의 계산으로 얻은 총 별점
        CompanyScoreAvgDto avgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(avgDto).isNotNull();

        double firstAvg = avgDto.getAvgTotalRate();

        // 리뷰 수정
        CompanyReviewUpdateDto updateDto = updateCompanyDto(reviewId);
        int updated = companyReviewDao.updateCompanyReview(updateDto);
        assertThat(updated).isEqualTo(1);
        
        // 테스트하는 수정후 점수 계산
        CompanyScoreAdjustDto adjustDto = new CompanyScoreAdjustDto();
        adjustDto.setCompanyId(companyId);
        adjustDto.setOldPriceRate(dto.getCompanyReviewDto().getPriceRate());
        adjustDto.setOldResultRate(dto.getCompanyReviewDto().getResultRate());
        adjustDto.setOldScheduleRate(dto.getCompanyReviewDto().getScheduleRate());
        adjustDto.setOldCommunicationRate(dto.getCompanyReviewDto().getCommunicationRate());

        double oldPrice = adjustDto.getOldPriceRate();
        assertThat(oldPrice).isEqualTo(dto.getCompanyReviewDto().getPriceRate());

        adjustDto.setNewPriceRate(updateDto.getPriceRate());
        adjustDto.setNewResultRate(updateDto.getResultRate());
        adjustDto.setNewScheduleRate(updateDto.getScheduleRate());
        adjustDto.setNewCommunicationRate(updateDto.getCommunicationRate());

        double newPrice = adjustDto.getNewPriceRate();
        assertThat(newPrice).isEqualTo(updateDto.getPriceRate());

        int adjusted = companyReviewDao.adjustEditScoreAvg(adjustDto);
        assertThat(adjusted).isEqualTo(1);

        CompanyScoreAvgDto afterAvgDto = companyReviewDao.getAvgScoreByCompanyId(companyId);
        assertThat(afterAvgDto).isNotNull();
        assertThat(afterAvgDto.getAvgTotalRate()).isNotEqualTo(firstAvg);
    }

    // 관련 테스트 두개를 묶어서 분기별로 표현
    @DisplayName("리뷰를 삭제시 분기별 점수 계산")
    @Test
    void recalculateCompanyScoreAfterReviewDelete() {
        // 다형성 리뷰 생성 + 업체 리뷰 생성
        CreateReviewSet firstReview = createPolyReviewAndCompanyReview();
        Long companyId = firstReview.getCompanyReviewDto().getCompanyId();
        Long firstReviewId = firstReview.getCommonReviewDto().getReviewId();
        Long userId = firstReview.getCommonReviewDto().getUserId();

        // 해당 리뷰로 첫 점수 테이블 생성
        companyReviewDao.createScoreTable(firstReview.getCompanyReviewDto());

        double firstAvg = companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgTotalRate();

        // 2번째 리뷰, 같은 회사에 연결
        ReviewDto secondReviewDto = createPolyReview(companyId);
        CompanyReviewCreateDto secondReview = createCheckCompanyReview(companyId, secondReviewDto.getReviewId());
        Long secondReviewId = secondReview.getReviewId();
        secondReview.setPriceRate(10);
        secondReview.setResultRate(10);
        secondReview.setScheduleRate(10);
        secondReview.setCommunicationRate(10);

        companyReviewDao.insert(secondReview);
        companyReviewDao.addScoreOnCreate(secondReview);

        assertThat(companyId).isEqualTo(secondReview.getCompanyId());
        assertThat(secondReviewId).isNotEqualTo(firstReview.getCommonReviewDto().getReviewId());

        // 현재 리뷰 수는 2개
        int reviewCount = companyReviewDao.countByCompanyId(companyId);
        assertThat(reviewCount).isEqualTo(2);

        // jdbctemplate을 사용하여 리뷰 뽑기
        CompanyScoreAdjustDto adjustDto = jdbcTemplate.queryForObject(
                "SELECT company_id, communication_rate, price_rate, result_rate, schedule_rate " +
                        "FROM company_review WHERE review_id = ?",
                (rs, rowNum) -> {
                    CompanyScoreAdjustDto dto = new CompanyScoreAdjustDto();
                    dto.setCompanyId(rs.getLong("company_id"));
                    dto.setOldPriceRate(rs.getInt("price_rate"));
                    dto.setOldResultRate(rs.getInt("result_rate"));
                    dto.setOldScheduleRate(rs.getInt("schedule_rate"));
                    dto.setOldCommunicationRate(rs.getInt("communication_rate"));
                    return dto;
                },
                firstReviewId
        );

        // 리뷰 소프트 삭제
        companyReviewDao.softDeleteCompanyReview(firstReviewId);
        companyReviewDao.softDeletePolyReview(firstReviewId, userId);
        
        // 쿼리 중복 합산 문제있음 -> sum, avg 따로 쿼리처리 해야함
        System.out.println("getAvgTotalRate: " + companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgTotalRate());
        System.out.println("getAvgCommunication: " + companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgCommunication());
        System.out.println("getAvgPrice: " + companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgPrice());
        System.out.println("getAvgSchedule: " + companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgSchedule());
        System.out.println("getAvgResult: " + companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgResult());

        reviewCount = companyReviewDao.countByCompanyId(companyId);
        if (reviewCount > 1) {
            companyReviewDao.adjustDeleteScoreAvg(adjustDto);
            double secondAvg = companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgTotalRate();
            assertThat(companyReviewDao.countByCompanyId(companyId)).isEqualTo(1);
            assertThat(secondAvg).isNotEqualTo(firstAvg);
        }

        // 리뷰 소프트 삭제
        companyReviewDao.softDeleteCompanyReview(secondReviewId);
        companyReviewDao.softDeletePolyReview(secondReviewId, userId);
        
        // 리뷰 개수만 1개면 세부 점수 상관없이 0으로 리셋
        reviewCount = companyReviewDao.countByCompanyId(companyId);
        if (reviewCount == 1) {
            companyReviewDao.resetScoresIfOne(companyId);
            assertThat(companyReviewDao.getAvgScoreByCompanyId(companyId).getAvgTotalRate()).isEqualTo(0);
        }
    }



}