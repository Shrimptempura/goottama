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

    // 검색 폼 제출
    form.addEventListener("submit", async function (e) {
        e.preventDefault();
        const formData = new FormData(form);
        formData.set("page", 1); // 검색 시 1페이지로

        const response = await fetch("/admin/notices/notice_list", {
            method: "POST",
            body: formData,
        });
        const html = await response.text();
        container.innerHTML = html;

        bindPageButtons(); // 페이지 버튼에 이벤트 다시 연결
    });

    // 페이지 버튼 클릭 핸들러 연결 함수
    function bindPageButtons() {
        const buttons = container.querySelectorAll(".page-btn");
        buttons.forEach((btn) => {
            btn.addEventListener("click", async function () {
                const page = btn.dataset.page;
                const formData = new FormData(form);
                formData.set("page", page);

                const response = await fetch("/admin/notices/notice_list", {
                    method: "POST",
                    body: formData,
                });
                const html = await response.text();
                container.innerHTML = html;

                bindPageButtons(); // 다시 바인딩 필요
            });
        });
    }

    bindPageButtons(); // 초기 바인딩
});
</script>
</head>
<body>
    <div id="toast"></div>

    <h1>Notice</h1>

    <%-- 검색바 --%>
    <form action="admin/notice_list" method="post" id="noticeSearchForm" >
        <div class="search-form">
            <label>제목 <input type="text" name="noticeTitle" placeholder="제목"></label>
            <label>내용 <input type="text" name="noticeContent" placeholder="내용"></label>
            <label>시작일 <input type="date" name="noticeDateStart"></label>
            <label>종료일 <input type="date" name="noticeDateEnd"></label>
            <input type="submit" value="검색" />
        </div>
    </form>

    <%-- 페이징 --%>
    <div id="noticeListContainer">
        <jsp:include page="notice_list.jsp" />
    </div>

    <%-- 글쓰기 버튼 --%>
    <button type="button" onclick="location.href='admin/notices/notice_write'">공지 작성</button>
</body>
</html>