<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script src="static/js/admin/toast.js"></script>
<link rel="stylesheet" href="static/css/admin/toast.css">
<title>Notice</title>
</head>
<body>
    <div id="toast"></div>

    <%-- 페이지 이름 + 기본정보 --%>
    <div class="page-info-bar">
        <h1>Notice</h1>
        <div class="info">
            전체 글 : ${totRowCnt} <br>
            현재 페이지 / 전체 페이지 : ${searchVO.page } / ${searchVO.totPage }
        </div>
    </div>

    <%-- 검색바 --%>
    <form action="admin/notice_list" method="post">
        <div class="search-form">
            <label>제목 <input type="text" name="noticeTitle" value="${noticeTitle}" /></label>
            <label>내용 <input type="text" name="noticeContent" value="${noticeContent}" /></label>
            <label>시작일 <input type="date" name="noticeDateStart" value="${noticeDateStart}" /></label>
            <label>종료일 <input type="date" name="noticeDateEnd" value="${noticeDateEnd}" /></label>
            <input type="submit" value="검색" />
        </div>
    </form>

    <%-- Pagination UI --%>
    <div class="pagination-wrapper">
    	<div class="pagination-controls">
    		<!-- 처음 / 이전 -->
    		<c:set var="prevPage" value="${searchVO.page - 1}" />
    		<c:if test="${prevPage < 1}">
    		    <c:set var="prevPage" value="1" />
    		</c:if>
    		<a class="nav <c:if test='${searchVO.page == 1}'>disabled</c:if>'" href="list?page=1">처음</a>
    		<a class="nav <c:if test='${searchVO.page == 1}'>disabled</c:if>'" href="list?page=${prevPage}">이전</a>

    		<!-- 구분 공간 -->
    		<span class="spacer"></span>

    		<!-- 숫자 버튼 -->
    		<c:forEach begin="${searchVO.pageStart}" end="${searchVO.pageEnd}" var="i">
    			<c:choose>
    				<c:when test="${i eq searchVO.page}">
    					<span class="current">${i}</span>
    				</c:when>
    				<c:otherwise>
    					<a href="list?page=${i}" class="otherpages">${i}</a>
    				</c:otherwise>
    			</c:choose>
    		</c:forEach>

    		<!-- 구분 공간 -->
    		<span class="spacer"></span>

    		<!-- 다음 / 맨끝 -->
    		<c:set var="nextPage" value="${searchVO.page + 1}" />
    		<c:if test="${nextPage > searchVO.totPage}">
    		    <c:set var="nextPage" value="${searchVO.totPage}" />
    		</c:if>
    		<a class="nav <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" href="list?page=${nextPage}">다음</a>
    		<a class="nav <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" href="list?page=${searchVO.totPage}">맨끝</a>
    	</div>
    </div>

    <%-- 공지 출력 --%>
    <table class="notice-table">
        <c:forEach items="${notices}" var="notice">
        <tr>
            <a href="">
                <td>${notice.noticesTitle}
                    <c:if test="${notice.noticesFilePath ne null}">
                        <i title="${notice.noticesFilePath }" class="fa-regular fa-floppy-disk"></i>
                    </c:if>
                </td>
            </a>
            <td>${notice.noticesCreatedAt}</td>
        </tr>
        </c:forEach>
    </table>

    <%-- 글쓰기 버튼 --%>
    <button type="button" onclick="location.href='admin/notices/notice_write'">공지 작성</button>
</body>
</html>