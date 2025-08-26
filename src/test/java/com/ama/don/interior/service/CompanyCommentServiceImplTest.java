package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyCommentDao;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.comment.CompanyCommentCreateDto;
import com.ama.don.interior.dto.comment.CompanyCommentDto;
import com.ama.don.interior.dto.comment.CompanyCommentTreeDto;
import com.ama.don.interior.dto.comment.CompanyCommentUpdateDto;
import com.ama.don.interior.dto.post.CompanyPostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Nested
    @DisplayName("댓글 작성 실패 케이스")
    class AddCommentFailCases {

        @DisplayName("실패 - 미로그인")
        @Test
        void notLogIn() {
            Long companyPostId = 200L;
            String content = "내용2";

            when(companyAuthService.getLoginUserId()).thenReturn(null);

            assertThrows(AccessDeniedException.class, () ->
                    commentService.addComment(companyPostId, null, content)
            );
        }

        @DisplayName("실패 - 게시글 없음")
        @Test
        void postNotFound() {
            Long companyPostId = 200L;
            Long userId = 300L;
            String content = "내용";

            when(companyAuthService.getLoginUserId()).thenReturn(userId);
            when(companyPostDao.findById(companyPostId)).thenReturn(null);

            assertThrows(IllegalStateException.class, () ->
                    commentService.addComment(companyPostId, null, content)
                    );
            verify(companyAuthService).getLoginUserId();
            verifyNoInteractions(companyCommentDao);
        }

        @DisplayName("실패 - 댓글 공백")
        @Test
        void commentBlank() {
            Long companyPostId = 200L;

            assertThrows(IllegalArgumentException.class, () ->
                    commentService.addComment(companyPostId, null, "   \u200B  ")
                    );

            verifyNoInteractions(companyCommentDao, companyPostDao, companyAuthService);
        }

        @DisplayName("실패 - 부모 댓글 삭제됨")
        @Test
        void parentDeleted() {
            Long companyPostId = 200L;
            Long userId = 300L;

            when(companyAuthService.getLoginUserId()).thenReturn(userId);
            when(companyPostDao.findById(companyPostId)).thenReturn(new CompanyPostDto());

            CompanyCommentDto parent = new CompanyCommentDto();
            parent.setCommentId(100L);
            parent.setParentCommentId(null);
            parent.setTargetId(companyPostId);
            parent.setTargetType(TargetType.INTERIOR_POST);
            parent.setDeleted(true);

            when(companyCommentDao.findById(100L)).thenReturn(parent);

            assertThrows(IllegalArgumentException.class, () ->
                    commentService.addComment(companyPostId, 100L, "내용2")
                    );

            verify(companyCommentDao, never()).insertCompanyComment(any(CompanyCommentCreateDto.class));
        }

        @DisplayName("실패 - 디비 결과가 1이 아님")
        @Test
        void notUpdatedDB() {
            Long userId = 100L;
            Long companyPostId = 200L;

            when(companyAuthService.getLoginUserId()).thenReturn(userId);
            when(companyPostDao.findById(companyPostId)).thenReturn(new CompanyPostDto());
            when(companyCommentDao.insertCompanyComment(any(CompanyCommentCreateDto.class))).thenReturn(0);

            assertThrows(IllegalStateException.class, () ->
                    commentService.addComment(companyPostId, null, "abcd")
                    );
        }
    }

    @DisplayName("댓글 수정 성공")
    @Test
    void shouldSucceed_whenEditComment() {
        Long commentId = 100L;
        Long userId = 200L;
        Long companyPostId = 300L;
        String origin = "옛날댓글";
        String expected = "수정댓글";

        CompanyCommentDto dto = new CompanyCommentDto();
        dto.setCommentId(commentId);
        dto.setCommentContent(origin);
        dto.setUserId(userId);
        dto.setDeleted(false);

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyCommentDao.findById(commentId)).thenReturn(dto);

        CompanyCommentUpdateDto updateDto = new CompanyCommentUpdateDto();
        updateDto.setCommentId(commentId);
        updateDto.setCommentContent(expected);
        updateDto.setUserId(userId);

        when(companyCommentDao.updateCompanyComment(any(CompanyCommentUpdateDto.class))).thenReturn(1);

        commentService.updateMyComment(commentId, expected);

        ArgumentCaptor<CompanyCommentUpdateDto> captor = ArgumentCaptor.forClass(CompanyCommentUpdateDto.class);
        verify(companyCommentDao).updateCompanyComment(captor.capture());

        CompanyCommentUpdateDto saved = captor.getValue();
        assertThat(saved.getCommentId()).isEqualTo(commentId);
        assertThat(saved.getCommentContent()).isEqualTo(expected);
        assertThat(saved.getUserId()).isEqualTo(userId);
    }

    @Nested
    @DisplayName("댓글 수정 실패 케이스")
    class UpdateCommentFailCases {

        @DisplayName("실패 - 비 로그인 상태")
        @Test
        void notLogIn() {
            Long commentId = 100L;

            when(companyAuthService.getLoginUserId()).thenReturn(null);

            assertThrows(AccessDeniedException.class, () ->
                    commentService.updateMyComment(commentId, "내용")
                    );

            verifyNoInteractions(companyCommentDao);
        }

        @DisplayName("실패 - 타인댓글 수정시도")
        @Test
        void notOwnerComment() {
            Long commentId = 100L;
            Long userId = 200L;

            when(companyAuthService.getLoginUserId()).thenReturn(userId);

            CompanyCommentDto dto = new CompanyCommentDto();
            dto.setCommentId(commentId);
            dto.setUserId(userId + 1);
            dto.setDeleted(false);

            when(companyCommentDao.findById(commentId)).thenReturn(dto);

            assertThrows(AccessDeniedException.class, () ->
                    commentService.updateMyComment(commentId, "수정댓글")
                    );

            verify(companyCommentDao, never()).updateCompanyComment(any(CompanyCommentUpdateDto.class));
        }

        @DisplayName("실패 - 삭제된 댓글 수정시도")
        @Test
        void deletedComment() {
            Long commentId = 100L;
            Long userId = 200L;

            CompanyCommentDto dto = new CompanyCommentDto();
            dto.setCommentId(commentId);
            dto.setUserId(userId);
            dto.setDeleted(true);

            when(companyAuthService.getLoginUserId()).thenReturn(userId);
            when(companyCommentDao.findById(commentId)).thenReturn(dto);

            assertThrows(IllegalStateException.class, () ->
                    commentService.updateMyComment(commentId, "수정댓글")
                    );

            verify(companyCommentDao, never()).updateCompanyComment(any(CompanyCommentUpdateDto.class));
        }

        @DisplayName("실패 - DB 업데이트 결과없음")
        @Test
        void notUpdatedDB() {
            Long commentId = 100L;
            Long userId = 200L;

            when(companyAuthService.getLoginUserId()).thenReturn(userId);

            CompanyCommentDto dto = new CompanyCommentDto();
            dto.setCommentId(commentId);
            dto.setUserId(userId);
            dto.setDeleted(false);

            when(companyCommentDao.findById(commentId)).thenReturn(dto);
            when(companyCommentDao.updateCompanyComment(any(CompanyCommentUpdateDto.class))).thenReturn(0);

            assertThrows(IllegalStateException.class, () ->
                    commentService.updateMyComment(commentId, "수정댓글")
                    );
        }
    }

    @DisplayName("댓글 삭제 성공 - 소프트 삭제")
    @Test
    void shouldSucceed_whenDeleteComment() {
        Long commentId = 100L;
        Long userId = 200L;

        when(companyAuthService.getLoginUserId()).thenReturn(userId);

        CompanyCommentDto dto = new CompanyCommentDto();
        dto.setCommentId(commentId);
        dto.setUserId(userId);
        dto.setDeleted(false);

        when(companyCommentDao.findById(commentId)).thenReturn(dto);
        when(companyCommentDao.softDeleteCompanyComment(commentId, userId)).thenReturn(1);

        commentService.deleteMyComment(commentId);

        verify(companyAuthService).getLoginUserId();
        verify(companyCommentDao).findById(commentId);
        verify(companyCommentDao).softDeleteCompanyComment(commentId, userId);
    }

    @Nested
    @DisplayName("댓글 삭제 실패 케이스")
    class DeleteCommentFailCases {

        @DisplayName("실패 - 본인댓글이 아님")
        @Test
        void notMyComment() {
            Long commentId = 100L;
            Long userId = 200L;

            when(companyAuthService.getLoginUserId()).thenReturn(userId);

            CompanyCommentDto dto = new CompanyCommentDto();
            dto.setCommentId(commentId);
            dto.setUserId(userId + 1);
            dto.setDeleted(false);

            when(companyCommentDao.findById(commentId)).thenReturn(dto);

            assertThrows(AccessDeniedException.class, () ->
                    commentService.deleteMyComment(commentId)
            );

            verify(companyAuthService).getLoginUserId();
            verify(companyCommentDao).findById(commentId);
            verify(companyCommentDao, never()).softDeleteCompanyComment(anyLong(), anyLong());
        }

        @DisplayName("실패 - DB 업데이트 1이 아님")
        @Test
        void notUpdatedDB() {
            Long commentId = 100L;
            Long userId = 200L;

            when(companyAuthService.getLoginUserId()).thenReturn(userId);

            CompanyCommentDto dto = new CompanyCommentDto();
            dto.setCommentId(commentId);
            dto.setUserId(userId);
            dto.setDeleted(false);

            when(companyCommentDao.findById(commentId)).thenReturn(dto);
            when(companyCommentDao.softDeleteCompanyComment(commentId, userId)).thenReturn(0);

            assertThrows(IllegalStateException.class, () ->
                    commentService.deleteMyComment(commentId)
            );

            verify(companyAuthService).getLoginUserId();
            verify(companyCommentDao).findById(commentId);
            verify(companyCommentDao).softDeleteCompanyComment(commentId, userId);
        }
    }

    @Nested
    @DisplayName("게시글내 댓글 전체 삭제")
    class DeleteAllCommentsByPost {

        @DisplayName("성공 - 게시글 댓글 전체 삭제")
        @Test
        void shouldSucceed_allDeletedCommentByPost() {
            Long companyPostId = 100L;

            when(companyCommentDao.softDeleteCommentsByPostId(companyPostId, TargetType.INTERIOR_POST)).thenReturn(1);

            commentService.deleteAllByPost(companyPostId);

            verify(companyCommentDao).softDeleteCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        }

        @DisplayName("실패 - companyPostId가 <= 0이면 dao 호출 없음")
        @Test
        void shouldFail_postNotFound() {
            assertThrows(IllegalArgumentException.class, () ->
                    commentService.deleteAllByPost(null)
                    );
            verifyNoInteractions(companyCommentDao);
        }
    }

    @Nested
    @DisplayName("getCommentDetail - 단건조회")
    class GetCommentDetail {

        @DisplayName("성공 - 단건 조회 반환")
        @Test
        void shouldSucceed_getCommentDetail() {
            Long commentId = 100L;
            Long userId = 200L;

            CompanyCommentDto dto = new CompanyCommentDto();
            dto.setCommentId(commentId);
            dto.setUserId(userId);
            dto.setDeleted(false);

            when(companyCommentDao.findById(commentId)).thenReturn(dto);

            CompanyCommentDto result = commentService.getCommentDetail(commentId);
            assertThat(result).isEqualTo(dto);
        }

        @DisplayName("실패 - 댓글 없음")
        @Test
        void shouldFail_commentNotFound() {
            Long commentId = 100L;

            when(companyCommentDao.findById(commentId)).thenReturn(null);

            assertThrows(IllegalStateException.class, () ->
                    commentService.getCommentDetail(commentId)
                    );

            verify(companyCommentDao).findById(commentId);
        }
    }

    @Nested
    @DisplayName("listComments - 댓글 리스트 조회")
    class ListComments {

        @DisplayName("성공 -리스트 목록 반환")
        @Test
        void shouldSucceed_listComments() {
            Long companyPostId = 100L;

            CompanyCommentTreeDto dto1 = new CompanyCommentTreeDto();
            dto1.setCommentId(100L);
            dto1.setUserId(200L);
            dto1.setDeleted(false);

            CompanyCommentTreeDto dto2 = new CompanyCommentTreeDto();
            dto2.setCommentId(200L);
            dto2.setUserId(300L);
            dto2.setDeleted(false);

            when(companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST))
                    .thenReturn(List.of(dto1, dto2));

            List<CompanyCommentTreeDto> result = commentService.listComments(companyPostId);

            assertThat(result).hasSize(2);

            verify(companyCommentDao).findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        }

        @DisplayName("성공 - 댓글안달린 게시글")
        @Test
        void shouldSucceed_listComments_noComment() {
            Long companyPostId = 100L;

            when(companyCommentDao.findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST))
                    .thenReturn(List.of());

            List<CompanyCommentTreeDto> result = commentService.listComments(companyPostId);

            assertThat(result).hasSize(0);

            verify(companyCommentDao).findCommentsByPostId(companyPostId, TargetType.INTERIOR_POST);
        }


    }

}