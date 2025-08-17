package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyCommentDao;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.comment.CompanyCommentCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyCommentServiceImpl implements CompanyCommentService {

    private final CompanyAuthService companyAuthService;
    private final CompanyPostDao companyPostDao;
    private final CompanyCommentDao companyCommentDao;

    @Transactional
    @Override
    public Long addComment(Long companyPostId, Long parentCommentId, String commentContent) {
        // Dao: CompanyCommentDao.insertCompanyComment(CompanyCommentCreateDto)

        if (companyPostId == null || companyPostId <= 0) {
            log.warn("CompanyCommentService - 유효하지 않은 companyPostId - companyPostId: {}", companyPostId);
            throw new IllegalArgumentException("companyPostId는 1 이상이어야 합니다.");
        }

        log.info("CompanyCommentService - 댓글 작성 시작 - companyPostId: {}", companyPostId);

        // 댓글 검증
        String content = commentContentCheck(companyPostId, commentContent);

        // 인증
        Long userId = companyAuthService.getLoginUserId();
        if (userId == null) {
            log.warn("CompanyCommentService - 로그인 필요 - companyPostId: {}", companyPostId);
            throw new AccessDeniedException("로그인 필요");
        }

        // 댓글 작성중 게시글 삭제 확인
        if (companyPostDao.findById(companyPostId) == null) {
            log.error("CompanyCommentService - 게시글이 존재 하지 않습니다. - companyPostId: {}", companyPostId);
            throw new IllegalStateException("게시글이 존재하지 않습니다. companyPostId: " + companyPostId);
        }

        // 대댓글 확인
        Long rootParentId = parentCommentCheck(companyPostId, parentCommentId);

        CompanyCommentCreateDto createDto = new CompanyCommentCreateDto();
        createDto.setUserId(userId);
        createDto.setTargetId(companyPostId);
        createDto.setTargetType(TargetType.INTERIOR_POST);
        createDto.setParentCommentId(rootParentId);
        createDto.setCommentContent(content);

        int inserted = companyCommentDao.insertCompanyComment(createDto);
        if (inserted != 1) {
            log.error("CompanyCommentService - 댓글 작성 실패 - companyPostId: {}", companyPostId);
            throw new IllegalStateException("댓글 작성 실패");
        }

        log.info("CompanyCommentService - 댓글 작성 성공 - companyPostId: {}", companyPostId);
        return createDto.getCommentId();
    }

    // 댓글 단건 조회
    @Override
    public CompanyCommentDto getCommentDetail(Long commentId) {
        return null;
    }

    // 댓글 리스트 전체 조회
    @Override
    public List<CompanyCommentTreeDto> listComments(Long companyPostId) {
        if (companyPostId == null || companyPostId <= 0) {
            log.warn("CompanyCommentService - 유효하지 않은 게시글 아이디 입니다. - companyPostId: {}", companyPostId);
            throw new IllegalArgumentException("companyPostId는 1 이상이어야 합니다.");
        }

        List<CompanyCommentTreeDto> comments = companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }

        return comments;
    }

    @Override
    public void updateMyComment(Long commentId, String commentContent) {

    }

    @Override
    public void deleteMyComment(Long commentId) {

    }

    @Override
    public void deleteAllByPost(Long companyPostId) {

    }

    // 댓글 내용 검증
    private String commentContentCheck(Long companyPostId, String commentContent) {
        if (commentContent == null) {
            log.warn("CompanyCommentService - 댓글 없음 - companyPostId: {}", companyPostId);
            throw new IllegalArgumentException("댓글 없음");
        }

        String content = commentContent.strip().replace("\u200B", "");
        if (content.isBlank()) {
            log.warn("CompanyCommentService - 댓글이 공백만 존재 - companyPostId: {}", companyPostId);
            throw new IllegalArgumentException("공백만 있는 댓글은 허용하지 않습니다.");
        }

        final int MAX_LENGTH = 1000;
        if (content.length() > MAX_LENGTH) {
            log.warn("CompanyCommentService - 댓글 길이가 1000자를 넘습니다. - companyPostId: {}, contentLength: {}", companyPostId, content.length());
            throw new IllegalArgumentException("댓글은 1000자 제한입니다.");
        }
        return content;
    }

    // 대댓글 확인
    private Long parentCommentCheck(Long companyPostId, Long parentCommentId) {
        if (parentCommentId == null || parentCommentId <= 0) {
            return null;
        }

        CompanyCommentDto parent = companyCommentDao.findById(parentCommentId);
        if (parent == null) {
            log.warn("CompanyCommentService - 부모 댓글 없음 - parentCommentId: {}", parentCommentId);
            throw new IllegalArgumentException("부모 댓글이 존재하지 않습니다. parentCommentId: " + parentCommentId);
        }

        if (!companyPostId.equals(parent.getTargetId())) {
            throw new IllegalArgumentException("부모 댓글의 대상이 게시글아이디와 일치하지 않습니다. companyPostId: " + companyPostId + ", parentCommentId: " + parentCommentId);
        }

        if (parent.getTargetType() != TargetType.INTERIOR_POST) {
            throw new IllegalArgumentException("부모 댓글의 타입이 일치 하지 않습니다. parent.targetType: " + parent.getTargetType());
        }

        if (Boolean.TRUE.equals(parent.getDeleted())) {
            throw new IllegalArgumentException("삭제된 댓글에는 대댓글을 달 수 없습니다.");
        }

        Long checkId = (parent.getParentCommentId() == null)
                ? parent.getCommentId() : parent.getParentCommentId();

        return checkId;
    }


}
