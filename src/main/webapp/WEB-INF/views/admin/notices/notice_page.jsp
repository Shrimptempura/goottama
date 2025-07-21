<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<!-- <script src="static/js/admin/toast.js"></script> -->
<!-- <link rel="stylesheet" href="static/css/admin/toast.css"> -->
<title>Notice_page</title>
<script>
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("noticeSearchForm");
    const container = document.getElementById("noticeListContainer");

    // 초기 로드 시점에도 페이지 버튼에 이벤트 바인딩
    bindPageButtons();

    // 검색 폼 제출 (AJAX)
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const formData = new FormData(form);
        formData.set("page", 1); // 검색 시 항상 1페이지로

        await fetchNoticeList(formData);
    });

    // 공지 목록을 비동기적으로 가져오는 함수
    async function fetchNoticeList(formData) {
        try {
            const response = await fetch("/admin/notices/notice_list", {
                method: "POST",
                body: formData,
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const html = await response.text();
            container.innerHTML = html;

            bindPageButtons(); // 새롭게 로드된 HTML의 페이지 버튼에 이벤트 다시 연결
        } catch (error) {
            console.error("공지 목록을 불러오는 중 오류 발생:", error);
            alert("공지 목록을 불러오는 데 실패했습니다. 잠시 후 다시 시도해주세요.");
        }
    }

    // 페이지 버튼 클릭 핸들러 연결 함수
    function bindPageButtons() {
        // .pagination-controls 내의 모든 <a> 태그에 이벤트를 연결
        const buttons = container.querySelectorAll(".pagination-controls a");
        buttons.forEach((btn) => {
            if (!btn.classList.contains('disabled')) { // 비활성화된 버튼은 클릭 방지
                btn.onclick = async function (e) {
                    e.preventDefault();
                    const page = btn.dataset.page;
                    if (!page) return; // data-page가 없는 경우 방지

                    const formData = new FormData(form);
                    formData.set("page", page); // 숨겨진 페이지 input 값을 직접 업데이트하는 대신 formData에 설정

                    await fetchNoticeList(formData);
                };
            }
        });
    }
});
</script>
</head>
<body>
    <div id="toast"></div>

    <h1>Notice</h1>

    <%-- 검색바 --%>
    <form action="/admin/notices/notice_list" method="post" id="noticeSearchForm" >
        <div class="search-form">
            <label>제목 <input type="text" name="noticeTitle" placeholder="제목" value="${noticeSearchVO.noticeTitle}"></label>
            <label>내용 <input type="text" name="noticeContent" placeholder="내용" value="${noticeSearchVO.noticeContent}"></label>
            <label>시작일 <input type="date" name="noticeDateStart" value="${noticeSearchVO.noticeDateStart}"></label>
            <label>종료일 <input type="date" name="noticeDateEnd" value="${noticeSearchVO.noticeDateEnd}"></label>
            <input type="hidden" name="page" value="${searchVO.page}">
            <input type="submit" value="검색" />
        </div>
    </form>

    <%-- 페이징 및 공지 출력 --%>
    <div id="noticeListContainer">
        <jsp:include page="notice_list.jsp" />
    </div>

    <%-- 글쓰기 버튼 --%>
    <button type="button" onclick="location.href='./notice_write_view'">공지 작성</button>
</body>
</html>