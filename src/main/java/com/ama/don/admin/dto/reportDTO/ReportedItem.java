package com.ama.don.admin.dto.reportDTO;

import java.time.LocalDateTime;

/**
 * 신고 조회 시 화면 출력을 위한 wrapper 클래스의 인터페이스
 */
public interface ReportedItem {
    /**
     * 신고 대상의 고유 ID, targetType
     * @return String
     */
    String getId();

    /**
     * 신고 대상 유형, targetType
     * @return String
     */
    String getType();

    /**
     * 제목, 유저는 로그인 아이디, 댓글도 작성자 아이디
     * @return String
     */
    String getTitle();

    /**
     * 내용, 유저는 null
     * @return String or null
     */
    String getContent();

    /**
     * 작성자 ID, 유저는 null
     * @return String or null
     */
    String getAuthorId();

    /**
     * 작성자(유저) 닉네임
     * @return String
     */
    String getAuthorNickname();

    /**
     * 작성일, 유저는 가입일
     * @return LocalDateTime
     */
    LocalDateTime getCreatedAt();
}