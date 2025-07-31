package com.ama.don.common.dao;

import com.ama.don.common.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 공통 리뷰 테이블에 대한 dao
// 주로 관리자만 사용
@Mapper
public interface ReviewDao {
    // 다형성 리뷰 작성(userId, targetId, targetType만)
    int insertPolyReview(ReviewDto dto);

    /**
     * 기본값 오류 방지 조회
     * @param reviewId 조회할 다형성 리뷰의 ID
     * @return 리뷰 정보
     */
    ReviewDto selectDefaultFieldById(Long reviewId);

    // reviewId 기준 전체 조회
    ReviewDto selectById(Long reviewId);

    // 리뷰 작성
    int insertCommonReview(ReviewDto dto);

    // 리뷰 작성중 파일 저장(여러장)
    // 공통 file의 해당 메서드 사용
    //void insertReviewFile(File dto);

    // targetType과 targetId를 이용한 리뷰 목록 조회
    List<ReviewDto> findReviewByTarget(String targetType, Long targetId);

    // 리뷰 상세보기
    ReviewDto detail(Long reviewId);
    
    // 리뷰 삭제
    void delete(Long reviewId);
    
    // 리뷰 수정
    void update(ReviewDto dto);

    // 리뷰 개수
    int reviewCount(String targetType, Long targetId);
}
