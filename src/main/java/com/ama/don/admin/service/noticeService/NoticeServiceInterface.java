package com.ama.don.admin.service.noticeService;

import org.springframework.ui.Model;

/**
 * 공지사항 관련 비즈니스 로직을 수행하는 서비스 인터페이스.<br/>
 * 각 공지사항 서비스 구현체는 이 인터페이스를 상속받아 `execute` 메서드를 구현함.<br/>
 * 컨트롤러와 데이터베이스 접근 계층(DAO) 사이에서 비즈니스 규칙 및 데이터 처리를 담당함.
 */
public interface NoticeServiceInterface {

    /**
     * 특정 공지사항 관련 비즈니스 로직을 실행하는 메서드.<br/>
     * `Model` 객체를 통해 필요한 데이터를 입력받고,<br/>
     * 처리 결과를 모델에 담아 반환하는 공통 인터페이스 역할을 함.<br/>
     * (예: 공지 목록 조회, 공지 작성, 공지 수정, 공지 삭제 등)
     *
     * @param model Spring UI Model. 서비스 실행에 필요한 데이터를 포함하거나,<br/>
     * 실행 결과를 담아 반환하는 데 사용됨.
     */
    public void execute(Model model);
}
