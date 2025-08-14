package com.ama.don.interior.service;

import com.ama.don.interior.dto.post.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyPostService {

    // 업체 게시글 생성 (companyPostId 리턴)
    Long createCompanyPost(CompanyPostCreateDto dto, List<MultipartFile> files);

    // 홈 리스트 (정렬:최신, 랜덤, 인기, 지역)
    List<CompanyHomePostDto> getHomePostsLatest();
    List<CompanyHomePostDto> getHomePostsRandom();
    List<CompanyHomePostDto> getHomePostsPopular();
    List<CompanyHomePostDto> getHomePostsByRegion(String region);

    // 업체 상세페이지의 업체 게시글 리스트
    List<CompanyPostPreviewDto> listByCompanyId(Long companyId);

    // 업체 게시글 상세 정보
    CompanyPostDetailView getPostDetail(Long companyPostId);

    // 게시글 수정 뷰
    CompanyPostUpdateDto getEditView(Long companyPostId);

    // 게시글 수정
    void updatePost(CompanyPostUpdateDto dto, List<MultipartFile> files);
    
    // 게시글 삭제
    void deletePost(Long companyPostId);

}
