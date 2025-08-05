<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>Report Page</title>
</head>
<body>
    <div id="toast"></div>

        <h1>Report</h1>

        <%-- 검색바 --%>
        <form action="/admin/reports/report_list" method="post" id="reportSearchForm" >
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
        <div id="reportListContainer">
            <jsp:include page="report_list.jsp" />
        </div>
</body>
</html>