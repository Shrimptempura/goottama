package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyCommentDao;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.comment.CompanyCommentCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.post.CompanyPostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyCommentServiceImplTest {

    @InjectMocks
    CompanyCommentServiceImpl commentService;

    @Mock
    CompanyAuthService companyAuthService;
    @Mock
    CompanyPostDao companyPostDao;
    @Mock
    CompanyCommentDao companyCommentDao;

    @DisplayName("댓글 작성 성공 - 단건 댓글(루트 댓글)")
    @Test
    void shouldSucceed_addComment_root() {
        Long companyPostId = 200L;
        Long userId = 300L;
        String content = "배고파요 졸려요\u200B   ";
        String expectedContent = "배고파요 졸려요";

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyPostDao.findById(companyPostId)).thenReturn(new CompanyPostDto());

        doAnswer(invocationOnMock -> {
            CompanyCommentCreateDto dto = invocationOnMock.getArgument(0);
            dto.setCommentId(500L);
            return 1;
        }).when(companyCommentDao).insertCompanyComment(any(CompanyCommentCreateDto.class));

        Long result = commentService.addComment(companyPostId, null, content);
        assertEquals(result, 500L);

        ArgumentCaptor<CompanyCommentCreateDto> captor = ArgumentCaptor.forClass(CompanyCommentCreateDto.class);
        verify(companyCommentDao).insertCompanyComment(captor.capture());

        CompanyCommentCreateDto saved = captor.getValue();
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getTargetId()).isEqualTo(companyPostId);
        assertThat(saved.getTargetType()).isEqualTo(TargetType.INTERIOR_POST);
        assertThat(saved.getParentCommentId()).isEqualTo(null);
        assertThat(saved.getCommentContent()).isEqualTo(expectedContent);

        InOrder inOrder = inOrder(companyAuthService, companyPostDao, companyCommentDao);

        inOrder.verify(companyAuthService).getLoginUserId();
        inOrder.verify(companyPostDao).findById(companyPostId);
        inOrder.verify(companyCommentDao).insertCompanyComment(any(CompanyCommentCreateDto.class));
    }

    @DisplayName("댓글 작성 성공 - 대댓글")
    @Test
    void shouldSucceed_addComment_reply() {
        Long companyPostId = 200L;
        Long userId = 300L;
        Long postId = 500L;
        Long parentId = 100L;
        String content = "대댓글임\u200B   ";
        String expectedContent = "대댓글임";

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyPostDao.findById(companyPostId)).thenReturn(new CompanyPostDto());

        CompanyCommentDto parent = new CompanyCommentDto();
        parent.setCommentId(parentId);
        parent.setParentCommentId(null);
        parent.setTargetId(companyPostId);
        parent.setTargetType(TargetType.INTERIOR_POST);
        parent.setDeleted(false);

        when(companyCommentDao.findById(parentId)).thenReturn(parent);

        doAnswer(invocationOnMock -> {
            CompanyCommentCreateDto dto = invocationOnMock.getArgument(0);
            dto.setCommentId(postId);
            return 1;
        }).when(companyCommentDao).insertCompanyComment(any(CompanyCommentCreateDto.class));

        Long result = commentService.addComment(companyPostId, parentId, content);
        assertEquals(result, 500L);

        ArgumentCaptor<CompanyCommentCreateDto> captor = ArgumentCaptor.forClass(CompanyCommentCreateDto.class);
        verify(companyCommentDao).insertCompanyComment(captor.capture());

        CompanyCommentCreateDto saved = captor.getValue();
        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getTargetId()).isEqualTo(companyPostId);
        assertThat(saved.getTargetType()).isEqualTo(TargetType.INTERIOR_POST);
        assertThat(saved.getParentCommentId()).isEqualTo(parentId);
        assertThat(saved.getCommentContent()).isEqualTo(expectedContent);

        InOrder inOrder = inOrder(companyAuthService, companyPostDao, companyCommentDao);

        inOrder.verify(companyAuthService).getLoginUserId();
        inOrder.verify(companyPostDao).findById(companyPostId);
        inOrder.verify(companyCommentDao).findById(parentId);
        inOrder.verify(companyCommentDao).insertCompanyComment(any(CompanyCommentCreateDto.class));
    }

}