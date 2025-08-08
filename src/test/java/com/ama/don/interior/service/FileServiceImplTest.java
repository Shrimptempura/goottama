package com.ama.don.interior.service;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileDao fileDao;

    // Mock으로 만든 객체를 주입하는 실제 테스트 대상을 명시
    @InjectMocks
    private FileServiceImpl fileService;

    // 임시 디렉토리
    @org.junit.jupiter.api.io.TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(tempDir.resolve("interior"));
        ReflectionTestUtils.setField(fileService, "uploadBaseDir", tempDir.toString());
    }

    @DisplayName("파일 정상 업로드")
    @Test
    void shouldSucceed_whenTrySaveFile() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("testabc.png");
        // lenient: 불필요 스텁 무시

        // 검사값이라 donAnswer로 값 지정
        doAnswer(invocationOnMock -> {
            File file = invocationOnMock.getArgument(0);
            Files.writeString(file.toPath(), "www");
            return null;
        }).when(mockFile).transferTo(any(File.class));

        fileService.saveFile(TargetType.INTERIOR, 10L, mockFile, true);

        verify(fileDao).interCreate(any(FileDto.class));
    }

    @DisplayName("파일이 빈 경우 예외")
    @Test
    void shouldThrowException_whenTrySaveFile_withEmptyFile() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                fileService.saveFile(TargetType.INTERIOR, 10L, mockFile, true));

        // fileDao 접근 여부 확인
        verifyNoInteractions(fileDao);
    }

    @DisplayName("파일 정상 조회")
    @Test
    void shouldSucceed_whenGetFileList() {
        FileDto dummy = new FileDto();
        dummy.setFile_id(100L);
        dummy.setFile_name("testabc.png");

        when(fileDao.interFindByTarget(TargetType.INTERIOR, 10L)).thenReturn(List.of(dummy));

        List<FileDto> result = fileService.getFileList(TargetType.INTERIOR, 10L);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        verify(fileDao).interFindByTarget(TargetType.INTERIOR, 10L);
    }

    @DisplayName("파일 삭제 성공")
    @Test
    void shouldSucceed_whenDeleteFile() {
        // FileServiceImpl에서 path, name 사용
        FileDto dummy = new FileDto();
        dummy.setFile_path("/abcdefg");
        dummy.setFile_name("test.png");

        when(fileDao.interFindById(100L)).thenReturn(dummy);
        when(fileDao.interDeletedById(100L)).thenReturn(1);

        fileService.deleteFile(100L);

        verify(fileDao).interFindById(100L);
        verify(fileDao).interDeletedById(100L);
    }

    @DisplayName("없는 파일 경우 삭제 예외")
    @Test
    void shouldThrowException_whenDeleteFile_withNotExistFile() {
        when(fileDao.interFindById(500L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> fileService.deleteFile(500L));

        verifyNoInteractions(fileDao);      // fileDao 접근 여부 확인
    }

}