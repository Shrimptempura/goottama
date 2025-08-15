package com.ama.don.interior.service;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.FileDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyPostServiceImpl implements CompanyPostService {

    private final CompanyPostDao companyPostDao;
    private final CompanyAuthService companyAuthService;
    private final PostDao postDao;
    private final FileService fileService;

    // 업체 게시글 생성
    @Transactional
    @Override
    public Long createCompanyPost(CompanyPostCreateDto createDto, List<MultipartFile> files) {
        Long userId = companyAuthService.getLoginUserId();
        Long companyId = createDto.getCompanyId();

        // 업체 확인
        if (!companyAuthService.isOwner(createDto.getCompanyId())) {
            log.warn("CompanyPostService - 업체 권한이 없습니다.");
            throw new AccessDeniedException("회사만 게시글을 작성할 수 있습니다.");
        }

        // 이중검사
        if (isBlank(createDto.getCompanyPostTitle()) || isBlank(createDto.getCompanyPostContent()) ) {
            log.warn("CompanyPostService - 제목과 내용이 누락입니다 - companyId: {}", companyId);
            throw new IllegalArgumentException("제목과 내용은 필수 입니다.");
        }

        log.info("CompanyPostService - 상위 게시글 생성 시작 - companyId: {}, userId: {}", companyId, userId);
        PostDto poly = new PostDto();
        poly.setUser_id(userId);
        poly.setTargetType(TargetType.INTERIOR_POST);
        poly.setTargetId(createDto.getCompanyId());       // companyId

        // 상위 생성
        int polyed = postDao.insertPolyPostForCompany(poly);
        if (polyed == 0 || poly.getPost_id() == null) {
            log.error("CompanyPostService - 상위 게시글 생성 실패 - companyId: {}", companyId);
            throw new IllegalStateException("상위 게시글 생성 실패");
        }
        Long postId = poly.getPost_id();

        log.info("CompanyPostService - 하위 게시글 생성 시작 - companyId: {}, userId: {}", companyId, userId);
        createDto.setPostId(postId);
        // 하위 생성
        int sub = companyPostDao.insertCompanyPost(createDto);
        if (sub == 0 || createDto.getCompanyPostId() == null) {
            log.error("CompanyPostService - 하위 게시글 생성 실패 - companyId: {}", companyId);
            throw new IllegalStateException("하위 게시글 생성 실패");
        }
        Long companyPostId = createDto.getCompanyPostId();

        // 파일 저장 (targetId = companyPostId)
        try {
            savePostImages(companyPostId, files, false);
            log.info("CompanyPostService - 게시글 생성 성공 - postId: {}, companyPostId: {}, companyId: {}", postId, companyPostId, companyId);
            return companyPostId;
        } catch (Exception e) {
            safeDeleteAllFiles(TargetType.INTERIOR_POST, companyPostId);
            log.error("CompanyPostService - 파일 저장 실패 - companyPostId: {}", companyPostId, e);
            throw new IllegalStateException("파일 저장 실패", e);
        }
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

    // 업체 상세페이지의 게시글 탭의 리스트
    @Transactional
    @Override
    public List<CompanyPostPreviewDto> listByCompanyId(Long companyId) {
        List<CompanyPostPreviewDto> posts = companyPostDao.getCompanyPostPreview(companyId);
        if (posts.isEmpty()) {
//            throw new IllegalStateException("게시글 리스트가 없습니다. companyId: " + companyId);
            return posts;
        }

        List<Long> companyPostIds = new ArrayList<>(posts.size());
        for (CompanyPostPreviewDto dto : posts) {
            companyPostIds.add(dto.getCompanyPostId());
        }

        Map<Long, FileDto> map = fileService.getThumbnailList(TargetType.INTERIOR_POST, companyPostIds);
        for (CompanyPostPreviewDto dto : posts) {
            dto.setThumbnail(map.get(dto.getCompanyPostId()));
        }

        return posts;
    }

    @Transactional
    @Override
    public CompanyPostDetailView getPostDetail(Long companyPostId) {
        CompanyPostDetailSplitDto post = companyPostDao.getPostAndCompanyPostById(companyPostId);

        if (post == null) {
            log.warn("CompanyPostService - 게시글 상세 정보 없음 - companyPostId: {}", companyPostId);
            throw new IllegalStateException("게시글이 없습니다. companyPostId: " + companyPostId);
        }

        Long companyId = post.getCompanyId();
        CompanyPostBasicInfoDto company = companyPostDao.getCompanyBasicInfoById(companyId);

        if (company == null) {
            log.warn("CompanyPostService - 회사 정보 없음 - companyId: {}", companyId);
            throw new IllegalStateException("회사 정보가 없습니다. companyId: " + companyId);
        }

        // 조회수 증가
        companyPostDao.increaseHit(companyPostId);

        List<FileDto> images;
        try {
            images = fileService.getFileList(TargetType.INTERIOR_POST, companyPostId);
        } catch (Exception e) {
            log.warn("CompanyPostService - 이미지 조회 실패(관용) - companyPostId: {}", companyPostId, e);
            images = Collections.emptyList();
        }

        // 게시글 좋아요, 스크랩 추후 로직 추가 (본인 인증 필요)

        CompanyPostDetailView view = new CompanyPostDetailView();
        view.setPost(post);
        view.setCompany(company);
        view.setImages(images);

        return view;
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

    // 파일이 문제없으면 리스트로 반환
    private static List<MultipartFile> nonEmptyFiles(List<MultipartFile> files) {
        List<MultipartFile> list = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    list.add(file);
                }
            }
        }
        return list;
    }


    // 게시글 생성 삭제시 이미지 처리 allDelete으로 선택
    private void savePostImages(Long companyPostId, List<MultipartFile> files, boolean allDelete) {
        List<MultipartFile> list = nonEmptyFiles(files);

        if (list.isEmpty()) {
            log.warn("CompanyPostService - 유효한 이미지가 없습니다. - companyPostId: {}", companyPostId);
            throw new IllegalArgumentException("이미지가 저장되지 않았습니다.");
        }

        // 이미지 모두 삭제
        if (allDelete) {
            log.info("CompanyPostService - 이미지 모두 삭제 - companyPostId: {}", companyPostId);
            fileService.deleteAllByTargetId(TargetType.INTERIOR_POST, companyPostId);
        }

        // 첫장 썸네일
        fileService.saveFile(TargetType.INTERIOR_POST, companyPostId, list.get(0), true);
        for (int i = 1; i < list.size(); i++) {
            fileService.saveFile(TargetType.INTERIOR_POST, companyPostId, list.get(i), false);
        }
        log.info("CompanyPostService - 게시글 이미지 저장 성공 - companyPostId: {}", companyPostId);
    }


    // 빈칸 확인
    private static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    // 보상 삭제
    private void safeDeleteAllFiles(TargetType targetType, Long targetId) {
        try {
            fileService.deleteAllByTargetId(targetType, targetId);
        } catch (Exception failed) {
            log.warn("CompanyPostService - 파일 보상 삭제 실패 - companyPostId: {}", targetId, failed);
        }
    }
}
