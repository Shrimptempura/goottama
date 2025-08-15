<!-- log_page.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>Log Viewer</title>
</head>
<body>
    <h1>Log Viewer</h1>

    <%-- 검색바 --%>
        <form action="/admin/logs/log_list" method="post" id="logSearchForm">
            <div class="search-form">
                <label>활동 유형
                    <input type="text" name="user_activity_type" placeholder="활동 유형" value="${userActivitySearchDTO.user_activity_type}">
                    <div>
                    <c:forEach var="type" items="${activityType}">
                        ${type},
                    </c:forEach>
                    </div>
                </label>
                <label>타겟 타입
                    <input type="text" name="user_activity_target" placeholder="대상" value="${userActivitySearchDTO.user_activity_target}">
                    <div>
                     <c:forEach var="type" items="${activityTargetType}">
                        ${type},
                     </c:forEach>
                     </div>
                </label>
                <label>유저 아이디
                    <input type="text" name="user_id" placeholder="세부 내용" value="${userActivitySearchDTO.user_id}">
                </label>
                <label>타겟 아이디
                    <input type="text" name="user_id" placeholder="세부 내용" value="${userActivitySearchDTO.user_activity_target_id}">
                </label>
                <label>시작일
                    <input type="date" name="user_activity_time_start" value="${userActivitySearchDTO.user_activity_time_start}">
                </label>
                <label>종료일
                    <input type="date" name="user_activity_time_end" value="${userActivitySearchDTO.user_activity_time_end}">
                </label>
                <label>세부 내용
                    <input type="text" name="user_activity_details" placeholder="세부 내용" value="${userActivitySearchDTO.user_activity_details}">
                </label>
                <input type="hidden" name="page" value="${searchVO.page}">
                <input type="submit" value="검색">
            </div>
        </form>

        <%-- 페이징 및 공지 출력 --%>
        <div id="logListContainer">
            <jsp:include page="log_list.jsp" />
        </div>
</body>
</html>