package com.ama.don.interior.service;

import com.ama.don.common.enums.TargetType;
import com.ama.don.interior.dao.CompanyDao;
import com.ama.don.interior.dto.company.CompanyCreateDto;
import com.ama.don.interior.dto.company.CompanyCreateLocationDto;
import com.ama.don.interior.dto.company.CompanyInsertDto;
import com.ama.don.interior.dto.company.CompanyUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyDao companyDao;

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

        when(companyDao.isDuplicateCompanyName("중복된 업체명")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            companyServiceImpl.createCompany(1L, detail, new CompanyCreateLocationDto(), Mockito.mock(MultipartFile.class));
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
        detail.setCompanyField("테스트 필드");
        detail.setCompanyLicense("테스트 라이센스");
        detail.setCompanyAs("테스트 AS");
        detail.setCompanyCareer("테스트 커리어");
        detail.setCompanyIntro("테스트 소개글");

        CompanyCreateLocationDto location = new CompanyCreateLocationDto();
        location.setLocationId(300L);
        location.setLocationAddr("테스트 주소 구로구");

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);

        // 중복 통과
        when(companyDao.isDuplicateCompanyName("테스트 업체")).thenReturn(false);

        // 테스트 대상이므로 set(x)
        // companyId 추가, insertCompany void -> doAnswer()
        doAnswer(invocation -> {
            CompanyInsertDto dto = invocation.getArgument(0);
            dto.setCompanyId(1000L);
            return null;
        }).when(companyDao).insertCompany(any(CompanyInsertDto.class));
        // 실행
        companyServiceImpl.createCompany(userId, detail, location, mockFile);

        verify(companyDao).insertCompanyDetail(detail);
        verify(companyDao).insertLocation(location);
        verify(companyDao).insertCompany(any(CompanyInsertDto.class));
        verify(fileService).saveFile(TargetType.INTERIOR, 1000L, mockFile);
    }

    @DisplayName("업체 생성시 이미지 누락 확인")
    @Test
    void shouldThrowException_whenCreateCompany_withoutImage() {
        CompanyCreateDto detail = new CompanyCreateDto();
        detail.setCompanyDetailId(200L);
        detail.setCompanyName("통과하는 이름");

        CompanyCreateLocationDto location = new CompanyCreateLocationDto();
        location.setLocationId(300L);

        when(companyDao.isDuplicateCompanyName("통과하는 이름")).thenReturn(false);

        doAnswer(invocation -> {
            CompanyInsertDto dto = invocation.getArgument(0);
            dto.setCompanyId(1000L);
            return null;
        }).when(companyDao).insertCompany(any(CompanyInsertDto.class));

        assertThrows(IllegalArgumentException.class, () -> {
            companyServiceImpl.createCompany(1L, detail, location, null);
        });
    }

    @DisplayName("업체 수정 성공 + 사진")
    @Test
    void shouldSucceed_whenUpdateCompany_withImage() {
        Long companyId = 1000L;

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyName("바뀐 이름");
        dto.setCompanyId(companyId);

        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(companyDao.updateCompanyDetail(dto)).thenReturn(1);

        companyServiceImpl.updateCompany(dto,companyId, mockFile);

        verify(companyDao).updateCompanyDetail(dto);
        verify(fileService).deleteFile(companyId);
        verify(fileService).saveFile(TargetType.INTERIOR, companyId, mockFile);
    }

    @DisplayName("업체 수정 성공, 이미지는 교체 안함")
    @Test
    void shouldSucceed_whenUpdateCompany_withoutImage() {
        Long companyId = 1000L;

        CompanyUpdateDto dto = new CompanyUpdateDto();
        dto.setCompanyId(companyId);
        dto.setCompanyName();
    }

    
}