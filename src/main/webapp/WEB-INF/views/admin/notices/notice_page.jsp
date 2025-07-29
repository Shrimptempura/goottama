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
    <button type="button" onclick="location.href='./notices/notice_write_view'">공지 작성</button>
</body>
</html>