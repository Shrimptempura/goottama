package com.ama.don.common.dao;

import com.ama.don.common.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

// 공통 리뷰 테이블에 대한 dao
// 주로 관리자만 사용
@Mapper
public interface ReviewDao {
    // targetType과 targetId를 이용한 리뷰 목록 조회
    ArrayList<ReviewDto> findByTarget(String targetType, Long targetId);
    
    // 리뷰 작성
    void create(ReviewDto dto);
    
    // 리뷰 상세보기
    ReviewDto detail(Long reviewId);
    
    // 리뷰 삭제
    void delete(Long reviewId);
    
    // 리뷰 수정
    void update(ReviewDto dto);

    // 리뷰 개수
    int reviewCount(String targetType, Long targetId);
}
