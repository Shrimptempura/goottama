package com.ama.don.admin.dto;

import com.ama.don.admin.temp.tFileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 공지사항 정보를 담는 데이터 전송 객체(DTO).<br/>
 * 데이터베이스의 `notices` 테이블과 매핑됨.<br/>
 * 공지사항의 제목, 내용, 고정 여부, 작성일 등과 함께<br/>
 * 연결된 첨부파일 목록까지 포함하여 전송 및 처리함.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticesDto {
    private int notices_id;
    private String notices_title;

    /**
     * 공지사항의 상단 고정 여부.<br/>
     * `true`이면 상단에 고정되어 표시됨.
     */
    private boolean notices_is_pinned;
    private Timestamp notices_created_at;

    /**
     * 공지사항의 본문 내용.<br/>
     * TUI 에디터로 작성된 HTML 형태의 내용이 포함됨.
     */
    private String notices_content;

    /**
     * 해당 공지사항에 첨부된 파일들의 목록.<br/>
     * {@link com.ama.don.admin.dto.FileDto} 객체들의 리스트로 구성됨.
     */
    private List<tFileDto> attachedFiles;
}
