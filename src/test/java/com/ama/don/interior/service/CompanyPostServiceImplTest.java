package com.ama.don.interior.service;

import com.ama.don.common.dao.PostDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.dto.PostDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyCommentDao;
import com.ama.don.interior.dao.CompanyPostDao;
import com.ama.don.interior.dto.post.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyPostServiceImplTest {

    @InjectMocks
    private CompanyPostServiceImpl postImpl;

    @Mock
    private CompanyAuthService companyAuthService;
    @Mock
    private CompanyPostDao companyPostDao;
    @Mock
    private FileService fileService;
    @Mock
    private PostDao postDao;
    @Mock
    private CompanyCommentService companyCommentService;

    @DisplayName("게시글 생성 성공 - 상위 -> 하위 생성, 최소 이미지 1장 썸네일 저장")
    @Test
    void shouldSucceed_whenCreatePost_withLeastImage() {
        Long userId = 100L;
        Long companyId = 200L;

        PostDto poly = new PostDto();
        poly.setUser_id(userId);
        poly.setTargetType(TargetType.INTERIOR_POST);
        poly.setTargetId(companyId);

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        // 상위 postId
        doAnswer(invocationOnMock -> {
            PostDto post = invocationOnMock.getArgument(0);
            post.setPost_id(300L);
            return 1;
        }).when(postDao).insertPolyPostForCompany(any(PostDto.class));

        // 하위 companyPostId
        doAnswer(invocationOnMock -> {
            CompanyPostCreateDto createDto = invocationOnMock.getArgument(0);
            createDto.setCompanyPostId(400L);
            return 1;
        }).when(companyPostDao).insertCompanyPost(any(CompanyPostCreateDto.class));

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);

        Long resultCompanyPostId = postImpl.createCompanyPost(dto, List.of(mockFile));

        assertEquals(400L, resultCompanyPostId);
        verify(postDao).insertPolyPostForCompany(any(PostDto.class));
        verify(companyPostDao).insertCompanyPost(any(CompanyPostCreateDto.class));

        verify(fileService).saveFile(TargetType.INTERIOR_POST, resultCompanyPostId, mockFile, true);
    }

    @DisplayName("게시글 생성 실패 - 본인 업체가 아님(권한없음)")
    @Test
    void shouldThrowException_whenCreatePost_notOwner() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
                );

        verifyNoInteractions(postDao, companyPostDao, fileService);
    }

    @DisplayName("게시글 생성 실패 - 게시글 제목 또는 내용 누락")
    @Test
    void shouldThrowException_whenCreatePost_withTitleOrContentIsBlank() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent(null);

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
                );

        verifyNoInteractions(postDao, companyPostDao, fileService);

        verify(companyAuthService).getLoginUserId();
        verify(companyAuthService).isOwner(companyId);
    }
    
    @DisplayName("게시글 생성 실패 - 상위 게시글 생성 실패")
    @Test
    void shouldThrowException_whenPolyPostCreatedFail() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        // 상위 생성 실패
        when(postDao.insertPolyPostForCompany(any(PostDto.class))).thenReturn(0);

        assertThrows(IllegalStateException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
                );

        verifyNoInteractions(companyPostDao, fileService);
    }

    @DisplayName("게시글 생성 실패 - 상위 게시글 생성 실패, postId null")
    @Test
    void shouldThrowException_whenPolyPostCreatedFail_withPostIdNull() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        // postId 누락
        when(postDao.insertPolyPostForCompany(any(PostDto.class))).thenReturn(1);

        assertThrows(IllegalStateException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
        );

        verifyNoInteractions(companyPostDao, fileService);
    }

    @DisplayName("게시글 생성 실패 - 하위 게시글 생성 실패")
    @Test
    void shouldThrowException_whenCompanyPostCreatedFail() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        doAnswer(invocationOnMock -> {
            PostDto post = invocationOnMock.getArgument(0);
            post.setPost_id(300L);
            return 1;
        }).when(postDao).insertPolyPostForCompany(any(PostDto.class));

        // 하위 생성 실패
        when(companyPostDao.insertCompanyPost(any(CompanyPostCreateDto.class))).thenReturn(0);

        assertThrows(IllegalStateException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
                );

        verifyNoInteractions(fileService);
    }

    @DisplayName("게시글 생성 실패 - 하위 게시글 생성 실패, companyPostId null")
    @Test
    void shouldThrowException_whenCompanyPostCreatedFail_withCompanyPostIdNull() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        doAnswer(invocationOnMock -> {
            PostDto post = invocationOnMock.getArgument(0);
            post.setPost_id(300L);
            return 1;
        }).when(postDao).insertPolyPostForCompany(any(PostDto.class));

        // companyPostId 누락
        when(companyPostDao.insertCompanyPost(any(CompanyPostCreateDto.class))).thenReturn(1);

        assertThrows(IllegalStateException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
        );

        verifyNoInteractions(fileService);
    }

    @DisplayName("게시글 생성 실패 - 파일 저장 실패, catch 보상 삭제 정리")
    @Test
    void shouldThrowException_whenFileSaveFail_catchDeletedAll() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        // 상위 postId
        doAnswer(invocationOnMock -> {
            PostDto post = invocationOnMock.getArgument(0);
            post.setPost_id(300L);
            return 1;
        }).when(postDao).insertPolyPostForCompany(any(PostDto.class));

        // 하위 companyPostId
        doAnswer(invocationOnMock -> {
            CompanyPostCreateDto createDto = invocationOnMock.getArgument(0);
            createDto.setCompanyPostId(400L);
            return 1;
        }).when(companyPostDao).insertCompanyPost(any(CompanyPostCreateDto.class));

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);

        doThrow(IllegalStateException.class).when(fileService)
                .saveFile(any(TargetType.class), anyLong(), any(MultipartFile.class), anyBoolean());

        assertThrows(IllegalStateException.class, () ->
                postImpl.createCompanyPost(dto, List.of(mockFile))
                );

        verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_POST, 400L);
    }

    @DisplayName("게시글 생성 실패 - 최소 1장 이미지")
    @Test
    void shouldThrowException_whenCreatePost_withLeastImagedFail() {
        Long userId = 100L;
        Long companyId = 200L;

        CompanyPostCreateDto dto = new CompanyPostCreateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyPostTitle("제목");
        dto.setCompanyPostContent("내용");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        // 상위 postId
        doAnswer(invocationOnMock -> {
            PostDto post = invocationOnMock.getArgument(0);
            post.setPost_id(300L);
            return 1;
        }).when(postDao).insertPolyPostForCompany(any(PostDto.class));

        // 하위 companyPostId
        doAnswer(invocationOnMock -> {
            CompanyPostCreateDto createDto = invocationOnMock.getArgument(0);
            createDto.setCompanyPostId(400L);
            return 1;
        }).when(companyPostDao).insertCompanyPost(any(CompanyPostCreateDto.class));

        assertThrows(IllegalStateException.class, () ->
                postImpl.createCompanyPost(dto, List.of())
        );

        verify(fileService, never()).saveFile(any(TargetType.class), anyLong(), any(MultipartFile.class), anyBoolean());
    }

    @DisplayName("게시글 수정 뷰 성공")
    @Test
    void shouldSucceed_whenGetPostEditView() {
        Long companyId = 200L;
        Long companyPostId = 300L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyId(companyId);
        split.setCompanyPostId(companyPostId);
        split.setCompanyPostTitle("제목");

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        CompanyPostUpdateDto view = postImpl.getEditView(companyPostId);
        
        assertEquals(companyPostId, view.getCompanyPostId());
        assertEquals("제목", view.getCompanyPostTitle());

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyAuthService).isOwner(companyId);
    }

    @DisplayName("수정 뷰 조회 실패 - companyPostId is null")
    @Test
    void shouldThrowException_whenGetPostEditView_idIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                postImpl.getEditView(null)
                );

        verifyNoInteractions(companyPostDao, companyAuthService);
    }

    @DisplayName("수정 뷰 조회 실패 - post is null")
    @Test
    void shouldThrowException_whenGetPostEditView_postIsNull() {
        Long companyPostId = 300L;

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () ->
                postImpl.getEditView(companyPostId)
                );

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verifyNoInteractions(companyAuthService);
    }

    @DisplayName("수정 뷰 조회 실패 - 권한 없음")
    @Test
    void shouldThrowException_whenGetPostEditView_notOwner() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyPostId(companyPostId);
        split.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () ->
                postImpl.getEditView(companyPostId)
                );
    }
    
    @DisplayName("수정 성공 - 제목/내용만 수정")
    @Test
    void shouldSucceed_whenUpdatePost_onlyTitleAndContent() {
        Long companyPostId = 300L;
        Long companyId = 200L;
        Long postId = 400L;

        CompanyPostUpdateDto dto = new CompanyPostUpdateDto();
        dto.setCompanyPostId(companyPostId);
        dto.setCompanyPostTitle("수정된 제목");
        dto.setCompanyPostContent("수정된 내용");

        CompanyPostDetailSplitDto  origin = new CompanyPostDetailSplitDto();
        origin.setCompanyPostId(companyPostId);
        origin.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(origin);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);
        when(companyPostDao.updatePost(any(CompanyPostUpdateDto.class))).thenReturn(1);

        postImpl.updatePost(dto, List.of());

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyAuthService).isOwner(companyId);
        verify(companyPostDao).updatePost(dto);

        verify(fileService, never()).saveFile(any(), anyLong(), any(), anyBoolean());
    }

    @DisplayName("수정 성공 - 새파일 업로드 수정(삭제 + 생성)")
    @Test
    void shouldSucceed_whenUpdatePost_withNewFile() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostUpdateDto dto = new CompanyPostUpdateDto();
        dto.setCompanyPostId(companyPostId);
        dto.setCompanyPostTitle("제목22");
        dto.setCompanyPostContent("내용22");

        CompanyPostDetailSplitDto  origin = new CompanyPostDetailSplitDto();
        origin.setCompanyPostId(companyPostId);
        origin.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(origin);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);
        when(companyPostDao.updatePost(any(CompanyPostUpdateDto.class))).thenReturn(1);

        MultipartFile mF1 = Mockito.mock(MultipartFile.class);
        MultipartFile mF2 = Mockito.mock(MultipartFile.class);

        when(mF1.isEmpty()).thenReturn(false);
        when(mF2.isEmpty()).thenReturn(false);

        postImpl.updatePost(dto, List.of(mF1, mF2));

        InOrder inOrder = inOrder(fileService, companyPostDao);
        inOrder.verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_POST, companyPostId);
        inOrder.verify(fileService).saveFile(TargetType.INTERIOR_POST, companyPostId, mF1, true);
        inOrder.verify(fileService).saveFile(TargetType.INTERIOR_POST, companyPostId, mF2, false);
        inOrder.verify(companyPostDao).updatePost(dto);
    }

    @DisplayName("수정 실패 - 제목/내용 누락")
    @Test
    void shouldThrowException_whenUpdatePost_withTitleOrContentIsBlank() {
        Long companyPostId = 300L;

        CompanyPostUpdateDto dto = new CompanyPostUpdateDto();
        dto.setCompanyPostId(companyPostId);
        dto.setCompanyPostTitle(null);
        dto.setCompanyPostContent(null);

        assertThrows(IllegalArgumentException.class, () ->
                postImpl.updatePost(dto, List.of())
                );
    }

    @DisplayName("수정 실패 - 권한 없음")
    @Test
    void shouldThrowException_whenUpdatePost_notOwner() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostUpdateDto dto = new CompanyPostUpdateDto();
        dto.setCompanyPostId(companyPostId);
        dto.setCompanyPostContent("내용22");
        dto.setCompanyPostTitle("제목22");

        CompanyPostDetailSplitDto  origin = new CompanyPostDetailSplitDto();
        origin.setCompanyPostId(companyPostId);
        origin.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(origin);

        // 0
        when(companyAuthService.isOwner(companyId)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () ->
                postImpl.updatePost(dto, List.of())
                );

        verify(companyPostDao, never()).updatePost(any());
        verifyNoInteractions(fileService);
    }

    @DisplayName("수정 실패 - 보상 삭제 정리 확인")
    @Test
    void shouldThrowException_whenUpdatePost_catchDeletedAll() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostUpdateDto dto = new CompanyPostUpdateDto();
        dto.setCompanyPostId(companyPostId);
        dto.setCompanyPostTitle("제목22");
        dto.setCompanyPostContent("내용22");

        CompanyPostDetailSplitDto  origin = new CompanyPostDetailSplitDto();
        origin.setCompanyPostId(companyPostId);
        origin.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(origin);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);

        MultipartFile mF1 = Mockito.mock(MultipartFile.class);
        when(mF1.isEmpty()).thenReturn(false);

        doThrow(IllegalStateException.class).when(fileService)
                .saveFile(any(), anyLong(), any(), anyBoolean());

        assertThrows(IllegalStateException.class, () ->
                postImpl.updatePost(dto, List.of(mF1))
                );

        // try -> save, catch -> save
        InOrder inOrder = inOrder(fileService, companyPostDao);
        inOrder.verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_POST, companyPostId);
        inOrder.verify(fileService).saveFile(TargetType.INTERIOR_POST, companyPostId, mF1, true);
        inOrder.verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_POST, companyPostId);

        verify(companyPostDao, never()).updatePost(any());
    }

    @DisplayName("게시글 삭제 성공 - (댓글 -> 하위게시글 -> 상위 게시글 -> 파일)")
    @Test
    void shouldSucceed_whenDeletePost() {
        Long companyPostId = 300L;
        Long companyId = 200L;
        Long postId = 400L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyId(companyId);
        split.setCompanyPostId(companyPostId);
        split.setPostId(postId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);
        when(companyPostDao.deleteCompanyPostById(companyPostId)).thenReturn(1);
        when(companyPostDao.deletePolyPostById(postId)).thenReturn(1);

        // return 값 companyId;
        Long result = postImpl.deletePost(companyPostId);
        assertEquals(result, companyId);

        InOrder inOrder = inOrder(fileService, companyPostDao, companyCommentService);
        inOrder.verify(companyCommentService).deleteAllByPost(companyPostId);
        inOrder.verify(companyPostDao).deleteCompanyPostById(companyPostId);
        inOrder.verify(companyPostDao).deletePolyPostById(postId);
    }

    @DisplayName("게시글 삭제 실패 - companyPostId is null")
    @Test
    void shouldThrowException_whenDeletePost_idIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                postImpl.deletePost(null)
                );

        verifyNoInteractions(companyPostDao, companyAuthService, companyCommentService, fileService);
    }

    @DisplayName("게시글 삭제 실패 - post is null")
    @Test
    void shouldThrowException_whenDeletePost_postIsNull() {
        Long companyPostId = 300L;

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () ->
                postImpl.deletePost(companyPostId)
                );

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verifyNoInteractions(companyAuthService, companyCommentService, fileService);
    }

    @DisplayName("게시글 삭제 실패 - 권한 없음")
    @Test
    void shouldThrowException_whenDeletePost_notOwner() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyPostId(companyPostId);
        split.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(false);      // 0

        assertThrows(AccessDeniedException.class, () ->
                postImpl.deletePost(companyPostId)
                );

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyAuthService).isOwner(companyId);
        verify(companyPostDao, never()).deleteCompanyPostById(anyLong());
        verify(companyPostDao, never()).deletePolyPostById(anyLong());
        verifyNoInteractions(companyCommentService, fileService);
    }
    
    @DisplayName("게시글 삭제 실패 - 하위 게시글 삭제 실패")
    @Test
    void shouldThrowException_whenDeletePost_childDeletedFail() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyId(companyId);
        split.setCompanyPostId(companyPostId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);
        when(companyPostDao.deleteCompanyPostById(companyPostId)).thenReturn(0);

        assertThrows(IllegalStateException.class, () ->
                postImpl.deletePost(companyPostId)
                );


        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyAuthService).isOwner(companyId);
        verify(companyCommentService).deleteAllByPost(anyLong());
        verify(companyPostDao).deleteCompanyPostById(companyPostId);

        verify(companyPostDao, never()).deletePolyPostById(anyLong());
        verifyNoInteractions(fileService);
    }

    @DisplayName("게시글 삭제 실패 - 상위 게시글 삭제 실패")
    @Test
    void shouldThrowException_whenDeletePost_parentDeletedFail() {
        Long companyPostId = 300L;
        Long companyId = 200L;
        Long postId = 400L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyId(companyId);
        split.setCompanyPostId(companyPostId);
        split.setPostId(postId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);
        when(companyPostDao.deleteCompanyPostById(companyPostId)).thenReturn(1);
        when(companyPostDao.deletePolyPostById(postId)).thenReturn(0);

        assertThrows(IllegalStateException.class, () ->
                postImpl.deletePost(companyPostId)
        );

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyAuthService).isOwner(companyId);
        verify(companyCommentService).deleteAllByPost(anyLong());
        verify(companyPostDao).deleteCompanyPostById(companyPostId);
        verify(companyPostDao).deletePolyPostById(anyLong());

        verifyNoInteractions(fileService);
    }

    @DisplayName("게시글 삭제시 보상 삭제 정리 확인")
    @Test
    void shouldThrowException_whenDeletePost_catchDeletedAll() {
        Long companyPostId = 300L;
        Long companyId = 200L;
        Long postId = 400L;

        CompanyPostDetailSplitDto split = new CompanyPostDetailSplitDto();
        split.setCompanyId(companyId);
        split.setCompanyPostId(companyPostId);
        split.setPostId(postId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(split);
        when(companyAuthService.isOwner(companyId)).thenReturn(true);
        when(companyPostDao.deleteCompanyPostById(companyPostId)).thenReturn(1);
        when(companyPostDao.deletePolyPostById(postId)).thenReturn(1);

        doThrow(IllegalStateException.class).when(fileService)
                .deleteAllByTargetId(any(), anyLong());

        // return companyId
        Long result = postImpl.deletePost(companyPostId);
        assertEquals(result, companyId);

        InOrder inOrder = inOrder(fileService, companyPostDao, companyCommentService, companyAuthService);
        inOrder.verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        inOrder.verify(companyAuthService).isOwner(companyId);
        inOrder.verify(companyCommentService).deleteAllByPost(companyPostId);
        inOrder.verify(companyPostDao).deleteCompanyPostById(companyPostId);
        inOrder.verify(companyPostDao).deletePolyPostById(postId);

        // try + 보상 삭제 정리
        verify(fileService, times(2)).deleteAllByTargetId(any(), anyLong());
    }

    @DisplayName("상세조회 성공 - 조회수 + 이미지확인")
    @Test
    void shouldSucceed_whenGetPostDetail() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostDetailSplitDto post = new CompanyPostDetailSplitDto();
        post.setCompanyPostId(companyPostId);
        post.setCompanyId(companyId);

        CompanyPostBasicInfoDto company = new CompanyPostBasicInfoDto();
        company.setCompanyId(companyId);

        // multipart x
        FileDto image1 = new FileDto();
        FileDto image2 = new FileDto();
        List<FileDto> images = List.of(image1, image2);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(post);
        when(companyPostDao.getCompanyBasicInfoById(companyId)).thenReturn(company);
//        when(companyPostDao.increaseHit(companyPostId)).thenReturn(1);
        when(fileService.getFileList(TargetType.INTERIOR_POST, companyPostId)).thenReturn(images);

        CompanyPostDetailView view = postImpl.getPostDetail(companyPostId);

        assertSame(post, view.getPost());
        assertSame(company, view.getCompany());
        assertSame(images, view.getImages());

        InOrder inOrder = inOrder(companyPostDao, fileService);
        inOrder.verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        inOrder.verify(companyPostDao).getCompanyBasicInfoById(companyId);
        inOrder.verify(companyPostDao).increaseHit(companyPostId);
        inOrder.verify(fileService).getFileList(TargetType.INTERIOR_POST, companyPostId);
    }

    @DisplayName("상세조회 실패 - 게시글 없음")
    @Test
    void shouldThrowException_whenGetPostDetail_postIsNull() {
        Long companyPostId = 300L;

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () ->
                postImpl.getPostDetail(companyPostId)
                );

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyPostDao, never()).getCompanyBasicInfoById(anyLong());
        verify(companyPostDao, never()).increaseHit(anyLong());
        verifyNoInteractions(fileService);
    }

    @DisplayName("상세조회 실패 - 회사정보 없음")
    @Test
    void shouldThrowException_whenGetPostDetail_companyIsNull() {
        Long companyPostId = 300L;
        Long companyId = 200L;

        CompanyPostDetailSplitDto post = new CompanyPostDetailSplitDto();
        post.setCompanyPostId(companyPostId);
        post.setCompanyId(companyId);

        when(companyPostDao.getPostAndCompanyPostById(companyPostId)).thenReturn(post);
        when(companyPostDao.getCompanyBasicInfoById(companyId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () ->
                postImpl.getPostDetail(companyPostId)
                );

        verify(companyPostDao).getPostAndCompanyPostById(companyPostId);
        verify(companyPostDao).getCompanyBasicInfoById(companyId);
        verify(companyPostDao, never()).increaseHit(anyLong());
        verifyNoInteractions(fileService);
    }



}