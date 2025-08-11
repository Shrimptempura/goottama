package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyDao;
import com.ama.don.interior.dev.DevFindTarget;
import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
import com.ama.don.interior.dto.company.CompanyInsertDto;
import com.ama.don.interior.dto.company.CompanyUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyDao companyDao;

    @Mock
    private CompanyAuthService companyAuthService;

    @InjectMocks
    private CompanyServiceImpl companyServiceImpl;

    // 주의
    @Mock
    private FileService fileService;

    @DisplayName("업체 이름 중복 확인")
    @Test
    void shouldThrowException_whenCompanyNameIsDuplicate() {
        CompanyCreateDto detail = new CompanyCreateDto();
        detail.setCompanyName("중복된 업체명");

        when(companyAuthService.getLoginUserId()).thenReturn(100L);
        when(companyDao.isDuplicateCompanyName("중복된 업체명")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            companyServiceImpl.createCompany(detail, new CompanyCreateLocationDto(), Mockito.mock(MultipartFile.class));
        });
    }

    @DisplayName("업체 생성 성공")
    @Test
    void shouldSucceed_whenCreateCompany() {
        Long userId = 100L;
        CompanyCreateDto detail = new CompanyCreateDto();
        detail.setCompanyDetailId(200L);
        detail.setCompanyName("테스트 업체");
        detail.setCompanyAddr("테스트 주소 구로구");

        CompanyCreateLocationDto location = new CompanyCreateLocationDto();
        location.setLocationId(300L);
        location.setLocationAddr("테스트 주소 구로구");

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);

        try (MockedStatic<DevFindTarget> mockStatic = Mockito.mockStatic(DevFindTarget.class)) {
            mockStatic.when(DevFindTarget::getUserId).thenReturn(userId);

            // 중복 통과
            when(companyDao.isDuplicateCompanyName("테스트 업체")).thenReturn(false);

            // 테스트 대상이므로 set지정 불가
            // companyId 추가, insertCompany void -> doAnswer()
            doAnswer(invocation -> {
                CompanyInsertDto dto = invocation.getArgument(0);
                dto.setCompanyId(1000L);
                return null;
            }).when(companyDao).insertCompany(any(CompanyInsertDto.class));

            // 실행
            companyServiceImpl.createCompany(detail, location, mockFile);

            verify(companyDao).insertCompanyDetail(detail);
            verify(companyDao).insertLocation(location);
            verify(companyDao).insertCompany(any(CompanyInsertDto.class));
            verify(fileService).saveFile(TargetType.INTERIOR, 1000L, mockFile, true);
        }
    }

    @DisplayName("업체 생성시 이미지 누락 확인")
    @Test
    void shouldThrowException_whenCreateCompany_withoutImage() {
        CompanyCreateDto detail = new CompanyCreateDto();
        detail.setCompanyDetailId(200L);
        detail.setCompanyName("통과하는 이름");
        Long userId = 100L;

        CompanyCreateLocationDto location = new CompanyCreateLocationDto();
        location.setLocationId(300L);

        when(companyAuthService.getLoginUserId()).thenReturn(userId);
        when(companyDao.isDuplicateCompanyName("통과하는 이름")).thenReturn(false);

        doAnswer(invocation -> {
            CompanyInsertDto dto = invocation.getArgument(0);
            dto.setCompanyId(1000L);
            return null;
        }).when(companyDao).insertCompany(any(CompanyInsertDto.class));

        assertThrows(IllegalStateException.class, () -> {
            companyServiceImpl.createCompany(detail, location, null);
        });

        verify(companyDao).insertCompanyDetail(detail);
        verify(companyDao).insertLocation(location);
        verify(companyDao).insertCompany(any(CompanyInsertDto.class));
        verify(fileService, never()).saveFile(any(TargetType.class), anyLong(), any(MultipartFile.class), anyBoolean());
    }

    @DisplayName("업체 수정 성공 + 사진")
    @Test
    void shouldSucceed_whenUpdateCompany_withImage() {
        Long companyId = 1000L;

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyName("바뀐 이름");
        dto.setCompanyId(companyId);

        when(companyAuthService.requireMyCompanyId()).thenReturn(companyId);
        when(companyDao.getCompanyNameById(companyId)).thenReturn("기존 이름");
        when(companyDao.isDuplicateCompanyName("바뀐 이름")).thenReturn(false);
        when(companyDao.updateCompanyDetail(dto)).thenReturn(1);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);

        companyServiceImpl.updateCompany(dto, mockFile);

        verify(companyDao).updateCompanyDetail(dto);
        verify(fileService).deleteThumbnail(TargetType.INTERIOR, companyId);
        verify(fileService).saveFile(TargetType.INTERIOR, companyId, mockFile, true);
    }

    @DisplayName("업체 수정 성공, 이미지는 교체 안함")
    @Test
    void shouldSucceed_whenUpdateCompany_withoutImage() {
        Long companyId = 1000L;

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyName("바뀐 이름");

        when(companyAuthService.requireMyCompanyId()).thenReturn(companyId);
        when(companyDao.getCompanyNameById(companyId)).thenReturn("기존 이름");
        when(companyDao.isDuplicateCompanyName("바뀐 이름")).thenReturn(false);
        when(companyDao.updateCompanyDetail(dto)).thenReturn(1);

        companyServiceImpl.updateCompany(dto, null);

        verify(companyDao).updateCompanyDetail(dto);
        verify(fileService, never()).deleteThumbnail(any(TargetType.class), anyLong());
        verify(fileService, never()).saveFile(any(TargetType.class), anyLong(), any(MultipartFile.class), anyBoolean());

    }

    @DisplayName("updateDto가 null이면 예외 발생")
    @Test
    void shouldThrowException_whenUpdateDto_withNull() {
        Long companyId = 1000L;

        when(companyAuthService.requireMyCompanyId()).thenReturn(companyId);

        assertThrows(IllegalArgumentException.class, () -> {
            companyServiceImpl.updateCompany(null, null);
        });

        verifyNoInteractions(companyDao);
    }

    @DisplayName("companyId가 null이면 예외 발생")
    @Test
    void shouldThrowException_whenCompanyId_withNull() {
        when(companyAuthService.requireMyCompanyId())
                .thenThrow(new IllegalStateException("업체 정보 조회 실패"));

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyName("업체 이름입니다.");

        assertThrows(IllegalStateException.class, () -> {
            companyServiceImpl.updateCompany(dto, null);
        });

        verifyNoInteractions(companyDao);
    }

    @DisplayName("업체 이름이 null이면 예외 발생")
    @Test
    void shouldThrowException_whenCompanyName_withNull() {
        Long companyId = 1000L;

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyId(1000L);
        dto.setCompanyName(null);

        when(companyAuthService.requireMyCompanyId()).thenReturn(companyId);

        assertThrows(IllegalStateException.class, () -> {
            companyServiceImpl.updateCompany(dto, null);
        });

        verify(companyDao, never()).updateCompanyDetail(dto);
        verify(fileService, never()).deleteThumbnail(any(TargetType.class), anyLong());
        verify(fileService, never()).saveFile(any(TargetType.class), anyLong(), any(MultipartFile.class), anyBoolean());

        verifyNoInteractions(fileService);
    }

    @DisplayName("업체 수정 실패 - 업체 이름 중복")
    @Test
    void shouldThrowException_whenUpdateCompanyDuplicated() {
        Long companyId = 1000L;

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyName("이미 있는 이름");

        when(companyAuthService.requireMyCompanyId()).thenReturn(companyId);

        when(companyDao.getCompanyNameById(companyId)).thenReturn("기존 이름");
        when(companyDao.isDuplicateCompanyName("이미 있는 이름")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            companyServiceImpl.updateCompany(dto, null);
        });

        verify(companyDao, never()).updateCompanyDetail(any(CompanyUpdateDto.class));
        verify(fileService, never()).deleteFile(anyLong());
        verify(fileService, never()).saveFile(any(TargetType.class), anyLong(), any(MultipartFile.class), anyBoolean());
    }

}