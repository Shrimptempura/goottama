package com.ama.don.interior.dao;

import com.ama.don.interior.dto.request.CompanyPostCreateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
// 업체가 작성한 게시글
public interface CompanyPostDao {
    // 게시글 목록보기
    ArrayList<CompanyPostCreateDto> list(Long companyId);

    // 게시글 작성하기
    void create(CompanyPostCreateDto dto);

    // 게시글 상세보기
    CompanyPostCreateDto detail(Long companyPostId);

    // 게시글 수정
    void update(CompanyPostCreateDto dto);

    // 게시글 수정 뷰
    CompanyPostCreateDto getEditView(Long companyPostId);

    // 게시글 삭제
    void delete(Long companyPostId);

    // 조회수 증가
    void increaseHit(Long companyPostId);

    // 좋아요 수
    int getLikeCount(Long companyPostId);
}
