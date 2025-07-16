package com.ama.don.interior.dao;

import com.ama.don.interior.dto.response.CompanyReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

// 사용자가 업체에 쓰는 리뷰 dao
@Mapper
public interface CompanyReviewDao {
    // 리뷰 목록 보기
    ArrayList<CompanyReviewDto> list();

    // 리뷰 작성
    void create(CompanyReviewDto dto);

    // 리뷰 상세보기
    CompanyReviewDto detail(int reviewId);

    // 리뷰 삭제
    void delete(int reviewId);

    // 리뷰 수정
    void update(CompanyReviewDto dto);

    // 리뷰 수정 뷰
    CompanyReviewDto updateView(int reviewId);
}
