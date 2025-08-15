package com.ama.don.interior.service;

import com.ama.don.common.dto.ReviewDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.common.service.ReviewService;
import com.ama.don.interior.dao.CompanyReviewDao;
import com.ama.don.interior.dto.review.CompanyReviewCreateDto;
import com.ama.don.interior.dto.review.CompanyReviewUpdateDto;
import com.ama.don.interior.dto.review.CompanyScoreAdjustDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyReviewServiceImplTest {

    @InjectMocks
    private CompanyReviewServiceImpl cRServiceImpl;

    @Mock
    private CompanyAuthService companyAuthService;

    @Mock
    private FileService fileService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private CompanyReviewDao companyReviewDao;

    @DisplayName("첫 리뷰작성이면 score 테이블 생성")
    @Test
    void shouldSucceed_whenFirstReview_createScoreTable() {
        Long companyId = 1000L;
        Long userId = 200L;
        Long reviewId = 300L;

        CompanyReviewCreateDto dto = new CompanyReviewCreateDto();
        dto.setCompanyId(companyId);
        dto.setReviewContent("리뷰내용임");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(reviewService.insertPolyReview(any(ReviewDto.class))).thenReturn(reviewId);
        when(companyReviewDao.insertCompanyReview(dto)).thenReturn(1);
        when(companyReviewDao.isExistScoreTable(companyId)).thenReturn(false);
        when(companyReviewDao.createScoreTable(dto)).thenReturn(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        List<MultipartFile> list = List.of(mockFile);

        Long implReviewId = cRServiceImpl.createReview(dto, list);
        assertEquals(reviewId, implReviewId);

        verify(companyReviewDao).createScoreTable(dto);
        verify(companyReviewDao, never()).addScoreOnCreate(dto);
        verify(companyReviewDao, never()).averageOnCreate(dto);
    }

    @DisplayName("첫 리뷰가 아니면 합산/평균 업데이트 구하기")
    @Test
    void shouldSucceed_whenNotFirstReview_updateScoreTable() {
        Long companyId = 1000L;
        Long userId = 200L;
        Long reviewId = 300L;

        CompanyReviewCreateDto dto = new CompanyReviewCreateDto();
        dto.setCompanyId(companyId);
        dto.setReviewContent("리뷰내용임");

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(reviewService.insertPolyReview(any(ReviewDto.class))).thenReturn(reviewId);
        when(companyReviewDao.insertCompanyReview(dto)).thenReturn(1);
        when(companyReviewDao.isExistScoreTable(companyId)).thenReturn(true);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        List<MultipartFile> list = List.of(mockFile);

        Long implReviewId = cRServiceImpl.createReview(dto, list);
        assertEquals(reviewId, implReviewId);

        verify(companyReviewDao, never()).createScoreTable(dto);
        verify(companyReviewDao).addScoreOnCreate(dto);
        verify(companyReviewDao).averageOnCreate(dto);
    }

    @DisplayName("리뷰 생성시 companyId가 null이면 예외")
    @Test
    void shouldThrowException_whenCompanyIdIsNull() {
        CompanyReviewCreateDto dto = new CompanyReviewCreateDto();

        assertThrows(IllegalArgumentException.class, () -> {
            cRServiceImpl.createReview(dto, new ArrayList<>());
        });

        verifyNoInteractions(reviewService);
        verifyNoInteractions(fileService);
    }

    @DisplayName("리뷰 수정 성공")
    @Test
    void shouldSucceed_whenUpdateReview() {
        Long companyId = 1000L;
        Long userId = 200L;
        Long reviewId = 300L;

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyReviewDao.existByReviewIdAndUserId(reviewId, userId)).thenReturn(true);

        CompanyReviewUpdateDto origin = new CompanyReviewUpdateDto();
        origin.setCompanyId(companyId);
        origin.setPriceRate(3);
        origin.setCommunicationRate(3);
        origin.setScheduleRate(3);
        origin.setResultRate(3);

        when(companyReviewDao.getEditView(reviewId)).thenReturn(origin);
        when(companyReviewDao.updatePolyReview(any(ReviewDto.class))).thenReturn(1);
        when(companyReviewDao.updateCompanyReview(any(CompanyReviewUpdateDto.class))).thenReturn(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        List<MultipartFile> list = List.of(mockFile);

        CompanyReviewUpdateDto updated = new CompanyReviewUpdateDto();
        updated.setReviewId(reviewId);
        updated.setCompanyId(companyId);
        updated.setPriceRate(5);
        updated.setCommunicationRate(5);
        updated.setScheduleRate(5);
        updated.setResultRate(5);

        cRServiceImpl.updateReview(updated, list);

        verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_REVIEW, reviewId);
        verify(fileService).saveFile(TargetType.INTERIOR_REVIEW, reviewId, mockFile, true);
        verify(companyReviewDao).updatePolyReview(any(ReviewDto.class));
        verify(companyReviewDao).updateCompanyReview(any(CompanyReviewUpdateDto.class));
        verify(companyReviewDao).adjustScoreOnEdit(any(CompanyScoreAdjustDto.class));
        verify(companyReviewDao).updateAverageScores(any(CompanyScoreAdjustDto.class));
    }

    @DisplayName("상위 리뷰의 확인불가로 예외처리")
    @Test
    void shouldThrowException_whenPolyReviewIsNotExist() {
        Long userId = 200L;
        Long reviewId = 300L;

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyReviewDao.existByReviewIdAndUserId(reviewId, userId)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            cRServiceImpl.updateReview(new CompanyReviewUpdateDto(), new ArrayList<>());
        });

        verify(companyReviewDao, never()).updatePolyReview(any(ReviewDto.class));
        verifyNoInteractions(fileService);
    }

    @DisplayName("삭제 할때 리뷰의 수가 0이되면 점수 0으로 초기화")
    @Test
    void shouldZeroReset_WhenReviewCountIsZero() {
        Long companyId = 1000L;
        Long userId = 200L;
        Long reviewId = 300L;

        CompanyReviewUpdateDto origin = new CompanyReviewUpdateDto();
        origin.setCompanyId(companyId);

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyReviewDao.existByReviewIdAndUserId(reviewId, userId)).thenReturn(true);
        when(companyReviewDao.getEditView(reviewId)).thenReturn(origin);
        when(companyReviewDao.countByCompanyId(companyId)).thenReturn(0);

        cRServiceImpl.deleteReview(reviewId);

        verify(companyReviewDao).softDeleteCompanyReview(reviewId);
        verify(companyReviewDao).softDeletePolyReview(reviewId, userId);
        verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_REVIEW, reviewId);
        verify(companyReviewDao).resetScoresIfOne(companyId);
        verify(companyReviewDao, never()).adjustScoreOnEdit(any(CompanyScoreAdjustDto.class));
    }
    
    @DisplayName("삭제 할때 리뷰가 있으면 합산/평균 재계산")
    @Test
    void shouldRecalculate_WhenReviewIsExist() {
        Long companyId = 1000L;
        Long userId = 200L;
        Long reviewId = 300L;

        CompanyReviewUpdateDto origin = new CompanyReviewUpdateDto();
        origin.setCompanyId(companyId);

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyReviewDao.existByReviewIdAndUserId(reviewId, userId)).thenReturn(true);
        when(companyReviewDao.getEditView(reviewId)).thenReturn(origin);
        when(companyReviewDao.countByCompanyId(companyId)).thenReturn(2);

        cRServiceImpl.deleteReview(reviewId);

        verify(companyReviewDao).softDeleteCompanyReview(reviewId);
        verify(companyReviewDao).softDeletePolyReview(reviewId, userId);
        verify(fileService).deleteAllByTargetId(TargetType.INTERIOR_REVIEW, reviewId);
        verify(companyReviewDao).adjustSumOnDelete(any(CompanyScoreAdjustDto.class));
        verify(companyReviewDao).updateAverageScores(any(CompanyScoreAdjustDto.class));
        verify(companyReviewDao, never()).resetScoresIfOne(companyId);
    }


}