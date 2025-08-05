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
        MultipartFile file = new MockMultipartFile("file", originalFileName, "image/png", content);

        // uploadBaseDir를 value로 수동 지정해서 필요
        // spy(): fileSystem이라 사용, 외부 api 호출막을때
        FileServiceImpl service = Mockito.spy(new FileServiceImpl(fileDao));
        ReflectionTestUtils.setField(service, "uploadBaseDir", "/abcdefg");

        // doNothing: 실제 실행 x
        doNothing().when(file).transferTo(any(File.class));

        service.saveFile(1L, TargetType.INTERIOR, 10L, file);

        verify(fileDao).create(any(FileDto.class));
    }

    @DisplayName("파일이 빈 경우 예외")
    @Test
    void saveFile_emptyFile_exception() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                fileService.saveFile(1L, TargetType.INTERIOR, 10L, file));
    }

    @DisplayName("파일 정상 조회")
    @Test
    void getFileList_suceess() {

    }

    @DisplayName("파일 삭제 성공")
    @Test
    void deleteFile_success() {

    }

    @DisplayName("없는 파일 경우 삭제 예외")
    @Test
    void deleteFile_notExistFile_exception() {

    }

}