<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Notice_page</title>
</head>
<body>
    <h1>Notice</h1>

    <%-- 검색바 --%>
    <form action="/admin/notices/notice_list" method="post" id="noticeSearchForm" class="admin-search-form">
        <div class="search-form">
            <span class="form-label">제목</span>
            <input type="text" name="noticeTitle" placeholder="제목" value="${noticeSearchDTO.noticeTitle}" class="form-input">

            <span class="form-label">내용</span>
            <input type="text" name="noticeContent" placeholder="내용" value="${noticeSearchDTO.noticeContent}" class="form-input">

            <span class="form-label">시작일</span>
            <input type="date" name="noticeDateStart" value="${noticeSearchDTO.noticeDateStart}" class="form-input">

            <span class="form-label">종료일</span>
            <input type="date" name="noticeDateEnd" value="${noticeSearchDTO.noticeDateEnd}" class="form-input">

            <input type="hidden" name="page" value="${searchVO.page}">

            <span class="search-button-container">
                <input type="submit" value="검색" />
            </span>
        </div>
    </form>

    <%-- 페이징 및 공지 출력 --%>
    <div id="noticeListContainer">
        <jsp:include page="notice_list.jsp" />
    </div>

    <%-- 글쓰기 버튼 --%>
    <button type="button" onclick="location.href='/admin/notices/notice_write_view'">공지 작성</button>
</body>
</html>