package com.ama.don.interior.service;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyPostServiceImpl implements CompanyPostService {

    private final CompanyPostDao companyPostDao;
    private final CompanyAuthService companyAuthService;
    private final PostDao postDao;

    // 업체 게시글 생성
    @Transactional
    @Override
    public Long createCompanyPost(CompanyPostCreateDto dto) {
        log.info("CompanyPostService - 업체 게시글 생성 시작 - dto: {}", dto);
        // 업체 확인
        if (!companyAuthService.isOwner(dto.getCompanyId())) {
            log.warn("CompanyPostService - 업체 권한이 없습니다.");
            throw new AccessDeniedException("회사만 게시글을 작성할 수 있습니다.");
        }

        Long userId = companyAuthService.getLoginUserId();

        // 다형성 게시글 생성
        PostDto poly = new PostDto();
        poly.setUser_id(userId);
        poly.setTargetType(TargetType.INTERIOR_POST);
        poly.setTargetId(dto.getCompanyId());       // companyId
        
        // 다형성 생성
        postDao.insertPolyPostForCompany(poly);

        // 다형성 조회 (date, default value)
        // 하위 생성
        // return은 companyPostId로




        return 0L;
    }

    @Override
    public List<CompanyHomePostDto> getHomePostsLatest() {
        return List.of();
    }

    @Override
    public List<CompanyHomePostDto> getHomePostsRandom() {
        return List.of();
    }

    @Override
    public List<CompanyHomePostDto> getHomePostsPopular() {
        return List.of();
    }

    @Override
    public List<CompanyHomePostDto> getHomePostsByRegion(String region) {
        return List.of();
    }

    @Override
    public List<CompanyPostPreviewDto> listByCompanyId(Long companyId) {
        return List.of();
    }

    @Override
    public CompanyPostDetailView getPostDetail(Long companyPostId) {
        return null;
    }

    @Override
    public CompanyPostUpdateDto getEditView(Long companyPostId) {
        return null;
    }

    @Override
    public void updatePost(CompanyPostUpdateDto dto) {

    }

    @Override
    public void deletePost(Long companyPostId) {

    }
}
