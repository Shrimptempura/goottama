package com.ama.don.admin.controller;

import com.ama.don.admin.dto.noticeDTO.NoticeSearchDTO;
import com.ama.don.admin.dto.noticeDTO.NoticesDto;
import com.ama.don.admin.service.noticeService.*;
import com.ama.don.admin.utils.SearchVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * 공지사항 관련 HTTP 요청을 처리하는 컨트롤러.<br/>
 * 클라이언트의 요청을 받아 서비스 계층으로 전달하고,
 * 서비스 처리 결과를 바탕으로 적절한 뷰(View)를 반환함.<br/>
 * 공지 목록 조회, 상세 조회, 작성, 수정, 삭제 기능 제공함.
 */
@Controller
public class AdminNoticeController {

    private final GetNoticeListService getNoticeListService;
    private final GetNoticeDetail getNoticeDetail;
    private final WriteNotice writeNotice;
    private final NoticeModify noticeModify;
    private final  NoticeDelete noticeDelete;
    public AdminNoticeController(GetNoticeListService getNoticeListService, GetNoticeDetail getNoticeDetail, WriteNotice writeNotice,
                                 NoticeModify noticeModify, NoticeDelete noticeDelete) {
        this.getNoticeListService = getNoticeListService;
        this.getNoticeDetail = getNoticeDetail;
        this.writeNotice = writeNotice;
        this.noticeModify = noticeModify;
        this.noticeDelete = noticeDelete;
    }

    /**
     * 공지사항 목록 페이지로 처음 접속하거나, 페이지네이션 및 검색 조건이 적용된 상태로
     * 전체 공지사항 목록을 로드하기 위한 GET 요청을 처리함.<br/>
     *
     * 이 메서드는 클라이언트로부터 전달받은 검색 및 페이지네이션 관련 데이터를 초기화하고
     * 모델에 추가한 뒤, 서비스 계층을 통해 실제 공지사항 목록을 조회하여 뷰로 전달함.
     *
     * @param model Spring UI Model. 뷰로 데이터를 전달하는 데 사용됨.<br/>
     * - `searchVO`: 페이지네이션(현재 페이지, 페이지당 항목 수 등) 관련 정보가 담긴 객체.<br/>
     * - `noticeSearchDTO`: 공지사항 검색 조건(예: 제목 검색어, 내용 검색어 등)이 담긴 객체.
     * @param searchVO 클라이언트로부터 전달되는 {@link com.ama.don.admin.utils.SearchVO} 객체.<br/>

     * 페이지네이션 정보를 담고 있으며, 요청에 포함되지 않은 경우 기본값으로 초기화됨.
     * @param noticeSearchDTO 클라이언트로부터 전달되는 {@link NoticeSearchDTO} 객체.<br/>
     * 공지사항 목록에 적용할 검색 조건을 담고 있으며, 요청에 포함되지 않은 경우 기본값으로 초기화됨.
     * @return 공지사항 목록을 표시할 뷰의 경로 ("admin/notices/notice_page")를 반환함.
     */
    @GetMapping("admin/notices/notice_page")
    public String noticePage(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute NoticeSearchDTO noticeSearchDTO){
        // 초기화
        if (searchVO == null) {
            searchVO = new SearchVO();
        }
        if (noticeSearchDTO == null) {
            noticeSearchDTO = new NoticeSearchDTO();
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("noticeSearchDTO", noticeSearchDTO);

        getNoticeListService.execute(model);
        return "admin/notices/notice_page";
    }

    /**
     * 공지 검색을 위한 HTTP POST 요청 처리함.<br/>
     * 클라이언트로부터 전달된 검색 조건과 페이지네이션 정보를 받아, 조건에 맞는 공지 목록을 조회 후 반환함.
     *
     * @param model Spring UI Model. 뷰로 데이터 전달에 사용됨.<br/>
     * - `searchVO`: 페이지네이션 관련 정보 담김.<br/>
     * - `noticeSearchVO`: 공지 검색 조건(제목, 내용 등) 담김.
     * @param searchVO 클라이언트에서 전달되는 {@link com.ama.don.admin.utils.SearchVO} 객체.<br/>
     * 페이지네이션 정보를 담고 있음.
     * @param noticeSearchDTO 클라이언트에서 전달되는 {@link NoticeSearchDTO} 객체.<br/>
     * 공지사항 목록에 적용될 검색 조건 담고 있음.
     * @return 공지 목록의 일부만 반환하는 뷰의 경로 ("admin/notices/notice_list") 반환됨.<br/>
     * 이는 비동기 검색(예: Ajax) 결과로 사용될 수 있음.
     */
    @PostMapping("/admin/notices/notice_list")
    public String noticeList(Model model,
                             @ModelAttribute SearchVO searchVO,
                             @ModelAttribute NoticeSearchDTO noticeSearchDTO) {
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("noticeSearchDTO", noticeSearchDTO);
        getNoticeListService.execute(model);

        return "admin/notices/notice_list";
    }

    /**
     * 공지 조회
     * @param model
     * @param noticesId
     * @return
     */
    @RequestMapping("/admin/notices/notice_detail")
    public String noticeDetail(Model model, @RequestParam("notices_id") String noticesId){
        model.addAttribute("noticesId", noticesId);
        getNoticeDetail.execute(model);
        return "admin/notices/notice_detail";
    }

    @GetMapping("/admin/notices/notice_data_modal")
    public String noticeDataModal(Model model, @RequestParam("notices_id") String noticesId){
        model.addAttribute("noticesId", noticesId);
        getNoticeDetail.execute(model);
        return "admin/notices/notice_data_modal";
    }

    /**
     * 공지 작성 화면 출력
     * @return 선택 한 공지사항 내용
     */
    @RequestMapping("/admin/notices/notice_write_view")
    public String writeView(){
        return "admin/notices/notice_write_view";
    }

    /**
     * 새로운 공지사항을 작성하고 데이터베이스에 저장하는 HTTP POST 요청을 처리함
     * 본문 내용, 제목, 고정 여부 및 첨부된 파일들을 받아 처리하며,
     * 처리 결과에 따라 성공 또는 실패 메시지를 로깅하고 페이지를 리다이렉트함
     *
     * @param model Spring UI Model. 서비스 계층으로 데이터를 전달하고 결과를 반환받는 데 사용 <br/>
     * - `mtfRequest`: 파일 업로드를 처리하기 위한 MultipartHttpServletRequest 객체.<br/>
     * - `newNotice`: 클라이언트로부터 받은 제목, 내용, 고정 여부 등이 담긴 NoticesDto 객체.<br/>
     * @param mtfRequest 파일 업로드 처리를 위한 {@link org.springframework.web.multipart.MultipartHttpServletRequest}.
     * 클라이언트가 전송한 파일 데이터를 포함함.
     * @param title      새로운 공지사항의 제목 (클라이언트로부터의 요청 파라미터).
     * @param content    새로운 공지사항의 내용 (HTML 형태로 TUI 에디터 내용을 포함).
     * @param isPinned   공지사항의 상단 고정 여부 (true: 고정, false: 일반). 기본값은 false
     * @return 공지사항 작성 처리 후, 상세 페이지 또는 목록 페이지로의 리다이렉트 URL을 반환합니다.
     * @throws RuntimeException 서비스 계층에서 공지사항 본문 저장 또는 첨부파일 저장/처리 중 오류 발생 시 발생
     */
    @PostMapping("/admin/notices/notice_write")
    public String noticeWrite(Model model, MultipartHttpServletRequest mtfRequest,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam(value = "isPinned", defaultValue = "false") boolean isPinned){
        model.addAttribute("mtfRequest", mtfRequest);

        NoticesDto newNotice = new NoticesDto();
        newNotice.setNotices_title(title);
        newNotice.setNotices_content(content);
        newNotice.setNotices_is_pinned(isPinned);
        // newNotice.setNotices_file_path(null);
        model.addAttribute("newNotice", newNotice);
        writeNotice.execute(model);
        Boolean result = (Boolean) model.asMap().get("writeResult");
        String message = result ? "write_success" : "write_failure";
        System.out.println(">>> "+ message);
        return "redirect:notice_page";
    }

    /**
     * 공지사항 수정 화면을 반환함.
     * @param model
     * @param noticesId
     * @return
     */
    @RequestMapping("/admin/notices/notice_modify_view")
    public String noticeModifyView(Model model, @RequestParam("notices_id") String noticesId){
        model.addAttribute("noticesId", noticesId);
        getNoticeDetail.execute(model);
        return "admin/notices/notice_modify_view";
    }

    /**
     * 기존 공지사항을 수정하고 데이터베이스에 반영하는 HTTP POST 요청을 처리함.<br/>
     * 수정된 제목, 내용, 고정 여부와 함께 기존 첨부파일 삭제 및 새로운 파일 추가 처리를 포함함.<br/>
     *
     * @param model Spring UI Model. 서비스 계층으로 데이터 전달 및 결과 반환에 사용됨.<br/>
     * - `modifiedNotice`: 클라이언트로부터 받은 수정된 공지사항 정보(제목, 내용, 고정 여부 등) 담김.<br/>
     * - `mtfRequest`: 새로운 첨부 파일 업로드를 처리하기 위한 MultipartHttpServletRequest 객체.<br/>
     * - `deleteFileIds`: 삭제할 기존 첨부 파일들의 ID 목록 담김.
     * @param mtfRequest 파일 업로드 처리를 위한 {@link org.springframework.web.multipart.MultipartHttpServletRequest}.<br/>
     * 클라이언트가 전송한 새로운 파일 데이터를 포함함.
     * @param noticesId 수정할 공지사항의 고유 ID.
     * @param title 수정된 공지사항의 제목.
     * @param content 수정된 공지사항의 내용 (HTML 형태로 TUI 에디터 내용 포함).
     * @param isPinned 공지사항의 상단 고정 여부 (true: 고정, false: 일반). 기본값은 false임.
     * @param deleteFileIds 삭제할 기존 첨부파일의 {@link java.util.List} 형태 ID 목록.<br/>
     * 체크박스 선택 시 값이 전달되며, 선택하지 않으면 null이 됨.
     * @return 공지사항 수정 처리 후, 해당 공지사항의 상세 페이지로 리다이렉트되는 URL 반환됨.<br/>
     * - 성공/실패 메시지는 콘솔에 출력됨 (추후 Toast 알림으로 대체 예정).
     */
    @RequestMapping("/admin/notices/notice_modify")
    public String noticeModify(Model model,
                               MultipartHttpServletRequest mtfRequest,
                               @RequestParam("notices_id") int noticesId,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam(value = "isPinned", defaultValue = "false") boolean isPinned,
                               @RequestParam(value = "deleteFileIds", required = false) List<Long> deleteFileIds){
        NoticesDto modifiedNotice = new NoticesDto();
        modifiedNotice.setNotices_id(noticesId);
        modifiedNotice.setNotices_title(title);
        modifiedNotice.setNotices_content(content);
        modifiedNotice.setNotices_is_pinned(isPinned);

        model.addAttribute("modifiedNotice", modifiedNotice);
        model.addAttribute("deleteFileIds", deleteFileIds);
        model.addAttribute("mtfRequest", mtfRequest);
        noticeModify.execute(model);
        Boolean result = (Boolean) model.asMap().get("modifyResult");
        String message = result ? "modify_success" : "modify_failure";
        System.out.println(">>> "+ message);
        return "redirect:notice_detail?notices_id="+noticesId;
    }

    /**
     * 공지사항 삭제를 위한 HTTP 요청 처리함.<br/>
     * 요청 파라미터에서 공지 ID를 추출하여 해당 공지 및 관련된 모든 첨부파일, TUI 에디터 이미지를 삭제함.<br/>
     *
     * @param model Spring UI Model. 서비스 계층으로 데이터를 전달하고 결과를 반환받는 데 사용됨.<br/>
     * - `request`: 삭제할 공지 ID를 포함하는 HttpServletRequest 객체.
     * @param request HTTP 요청 객체. 삭제할 공지사항의 `notices_id`를 파라미터로 포함함.
     * @return 공지사항 삭제 처리 후, 공지 목록 페이지로 리다이렉트되는 URL 반환됨.<br/>
     * - 성공/실패 메시지는 콘솔에 출력됨 (추후 Toast 알림으로 대체 예정).
     */
    @RequestMapping("/admin/notices/notice_delete")
    public String noticeDelete(Model model, HttpServletRequest request){
        model.addAttribute("request", request);
        noticeDelete.execute(model);
        Boolean result = (Boolean) model.asMap().get("deleteResult");
        String message = result ? "delete_success" : "delete_failure";
        System.out.println(">>> "+message);
        return "redirect:notice_page";
    }
}
