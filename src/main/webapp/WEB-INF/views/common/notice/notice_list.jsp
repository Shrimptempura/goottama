<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
        <title>전체 공지</title>
    </head>
    <body>
    <%@ include file="../header_navigation_bar.jsp" %>
    <h1>전체 공지</h1>
        <div class="info">
            전체 글 : ${searchVO.totRow} <br>
            현재 페이지 / 전체 페이지 : ${searchVO.page } / ${searchVO.totPage }
        </div>
        <table class="notice-table">
            <tr>
               <td>제목</td>
               <td>날자</td>
            </tr>
            <c:forEach items="${list}" var="notice">
                <tr>
                    <td>
                        <a href="/notice/notice_detail?notices_id=${notice.noticesId}">
                            ${notice.noticesTitle}
                        </a>
                    </td>
                    <td>
                    <fmt:formatDate value="${notice.noticesCreatedAt}" pattern="yyyy-MM-dd"/>
                    </td>
                    </tr>
            </c:forEach>
        </table>

        <c:set var="prevPage" value="${searchVO.page - 1}" />
        <c:if test="${prevPage < 1}">
            <c:set var="prevPage" value="1" />
        </c:if>

        <c:set var="nextPage" value="${searchVO.page + 1}" />
        <c:if test="${nextPage > searchVO.totPage}">
            <c:set var="nextPage" value="${searchVO.totPage}" />
        </c:if>

        <div class="pagination-wrapper">
            <div class="pagination-controls">
                <a href="/notice/notice_list?page=1" class="nav first page-btn <c:if test='${searchVO.page == 1}'>disabled</c:if>'" >처음</a>
                <a href="/notice/notice_list?page=${prevPage}" class="nav prev page-btn <c:if test='${searchVO.page == 1}'>disabled</c:if>'" >이전</a>

                <span class="spacer"></span>
                <c:forEach begin="${searchVO.pageStart}" end="${searchVO.pageEnd}" var="i">
                    <c:choose>
                        <c:when test="${i eq searchVO.page}">
                            <span class="current">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="/notice/notice_list?page=${i}" class="otherpages page-btn">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <span class="spacer"></span>
                <a href="/notice/notice_list?page=${nextPage}" class="nav next page-btn <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" >다음</a>
                <a href="/notice/notice_list?page=${searchVO.totPage}" class="nav last page-btn <c:if test='${searchVO.page == searchVO.totPage}'>disabled</c:if>'" >맨끝</a>
            </div>
        </div>
        <%@ include file="../footer.jsp" %>
</html>