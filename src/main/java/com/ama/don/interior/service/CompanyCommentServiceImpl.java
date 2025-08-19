package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyCommentDao;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.comment.CompanyCommentCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;
import com.ama.don.interior.dto.comment.CompanyCommentUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (commentId == null || commentId <= 0) {
            log.warn("CompanyCommentService - 유효하지 않은 댓글아이디입니다 - commentId: {}", commentId);
            throw new IllegalArgumentException("commentId는 1 이상이어야 합니다.");
        }

        CompanyCommentDto comment = companyCommentDao.findById(commentId);
        if (comment == null) {
            throw new IllegalStateException("댓글이 존재하지 않습니다. commentId: " + commentId);
        }

        return comment;
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

    // 댓글 수정
    @Transactional
    @Override
    public void updateMyComment(Long commentId, String commentContent) {
        if (commentId == null || commentId <= 0) {
            log.warn("CompanyCommentService - 유효하지 않은 아이디 - commentId: {}", commentId);
            throw new IllegalArgumentException("commentId는 1 이상이어야 합니다.");
        }

        String content = commentContentCheck(commentId, commentContent);
        Long userId = companyAuthService.getLoginUserId();

        if (userId == null) {
            log.warn("CompanyCommentService - 비 로그인 상태 - commentId: {}", commentId);
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        CompanyCommentDto comment = companyCommentDao.findById(commentId);

        if (!userId.equals(comment.getUserId())) {
            throw new AccessDeniedException("본인의 댓글만 수정 가능합니다.");
        }

        if (Boolean.TRUE.equals(comment.getDeleted())) {
            throw new IllegalStateException("삭제된 댓글은 수정 불가능 합니다.");
        }

        CompanyCommentUpdateDto updateDto = new CompanyCommentUpdateDto();
        updateDto.setCommentId(commentId);
        updateDto.setCommentContent(content);
        updateDto.setUserId(userId);

        int updated = companyCommentDao.updateCompanyComment(updateDto);
        if (updated != 1) {
            log.error("CompanyCommentService - 댓글 수정 실패 - commentId: {}, userId: {}", commentId, userId);
            throw new IllegalStateException("댓글 수정 실패");
        }

        log.info("CompanyCommentService - 댓글 수정 성공 - commentId: {}, userId: {}", commentId, userId);
    }

    // 댓글 단건 삭제
    @Transactional
    @Override
    public void deleteMyComment(Long commentId) {
        if (commentId == null || commentId <= 0) {
            log.warn("CompanyCommentService - 유효하지 않은 아이디 - commentId: {}", commentId);
            throw new IllegalArgumentException("commentId는 1 이상이어야 합니다.");
        }

        Long userId = companyAuthService.getLoginUserId();
        if (userId == null) {
            log.warn("CompanyCommentService - 비 로그인 상태 - commentId: {}", commentId);
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        CompanyCommentDto comment = companyCommentDao.findById(commentId);

        if (!userId.equals(comment.getUserId())) {
            throw new AccessDeniedException("본인의 댓글만 삭제 가능합니다.");
        }

        if (Boolean.TRUE.equals(comment.getDeleted())) {
            log.info("CompanyCommentService - 이미 삭제된 댓글 - commentId: {}, userId: {}", commentId, userId);
            return;
        }

        // 소프트 삭제
        int deleted = companyCommentDao.softDeleteCompanyComment(commentId, userId);
        if (deleted != 1) {
            log.error("CompanyCommentService - 댓글 삭제 실패 - commentId: {}, userId: {}", commentId, userId);
            throw new IllegalStateException("댓글 삭제 실패");
        }

        log.info("CompanyCommentService - 댓글 삭제 성공 - commentId: {}, userId: {}", commentId, userId);
    }

    // 게시글의 모든 댓글 삭제
    @Transactional
    @Override
    public void deleteAllByPost(Long companyPostId) {
        if (companyPostId == null || companyPostId <= 0) {
            log.warn("CompanyCommentService - 유효하지 않은 아이디 - companyPostId: {}", companyPostId);
            throw new IllegalArgumentException("companyPostId 1 이상이어야 합니다.");
        }

        // 전체 행
        int allDeleted = companyCommentDao.softDeleteCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        log.info("CompanyCommentService - 게시글의 전체 댓글 삭제 - companyPostId: {}, allDeleted: {}", companyPostId, allDeleted);
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
