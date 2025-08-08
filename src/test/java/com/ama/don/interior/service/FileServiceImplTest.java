package com.ama.don.interior.service;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileDao fileDao;

    // Mock으로 만든 객체를 주입하는 실제 테스트 대상을 명시
    @InjectMocks
    private FileServiceImpl fileService;

    @DisplayName("파일 정상 업로드")
    @Test
    void saveFile_success() throws IOException {
        String originalFileName = "test.png";
        byte[] content = "test content".getBytes();
        // 이거 4개는 필수
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn(originalFileName);
        // lenient: 불필요 스텁 무시

        // uploadBaseDir를 value로 수동 지정해서 필요
        // spy(): fileSystem이라 사용, 외부 api 호출막을때
        FileServiceImpl service = Mockito.spy(new FileServiceImpl(fileDao));
        ReflectionTestUtils.setField(service, "uploadBaseDir", "/abcdefg");

        // doNothing: 실제 실행해도 아무일도 일어나지 않음
        doNothing().when(mockFile).transferTo(any(File.class));

        service.saveFile(TargetType.INTERIOR, 10L, mockFile, true);

        verify(fileDao).create(any(FileDto.class));
    }

    @DisplayName("파일이 빈 경우 예외")
    @Test
    void saveFile_emptyFile_exception() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                fileService.saveFile(TargetType.INTERIOR, 10L, mockFile, any()));
    }

    @DisplayName("파일 정상 조회")
    @Test
    void getFileList_suceess() {
        FileDto dummy = new FileDto();
        dummy.setFile_id(100L);
        dummy.setFile_name("test.png");

        when(fileDao.findByTargetId(TargetType.INTERIOR, 10L)).thenReturn(List.of(dummy));

        List<FileDto> result = fileService.getFileList(TargetType.INTERIOR, 10L);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFile_id()).isEqualTo(100L);
    }

    @DisplayName("파일 삭제 성공")
    @Test
    void deleteFile_success() {
        // FileServiceImpl에서 path, name 사용
        FileDto dummy = new FileDto();
        dummy.setFile_path("/abcdefg");
        dummy.setFile_name("test.png");

        when(fileDao.interiorFindById(100L)).thenReturn(dummy);
        when(fileDao.interiorDeletedById(100L)).thenReturn(1);

        fileService.deleteFile(100L);

        verify(fileDao).interiorFindById(100L);
        verify(fileDao).interiorDeletedById(100L);
    }

    @DisplayName("없는 파일 경우 삭제 예외")
    @Test
    void deleteFile_notExistFile_exception() {
        when(fileDao.interiorFindById(500L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> fileService.deleteFile(500L));
    }

}