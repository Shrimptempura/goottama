<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Log Viewer</title>
</head>
<body>
    <h1>Log Viewer</h1>

    <%-- 검색바 --%>
        <form action="/admin/logs/log_list" method="post" id="logSearchForm" class="admin-search-form">
            <div class="search-form">
                <span class="form-label">활동 유형</span>
                <div class="form-input">
                    <input type="text" name="user_activity_type" placeholder="활동 유형" value="${userActivitySearchDTO.user_activity_type}">
                    <div class="input-description">
                    <c:forEach var="type" items="${activityType}">
                        ∎${type}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </c:forEach>
                    </div>
                </div>

                <span class="form-label">타겟 타입</span>
                <div class="form-input">
                    <input type="text" name="user_activity_target" placeholder="대상" value="${userActivitySearchDTO.user_activity_target}">
                    <div class="input-description">
                     <c:forEach var="type" items="${activityTargetType}">
                        ∎${type}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     </c:forEach>
                     </div>
                </div>

                <span class="form-label">유저 아이디</span>
                <input type="text" name="user_id" placeholder="유저 아이디" value="${userActivitySearchDTO.user_id}" class="form-input">

                <span class="form-label">타겟 아이디</span>
                <input type="text" name="user_activity_target_id" placeholder="타겟 아이디" value="${userActivitySearchDTO.user_activity_target_id}" class="form-input">

                <span class="form-label">시작일</span>
                <input type="date" name="user_activity_time_start" value="${userActivitySearchDTO.user_activity_time_start}" class="form-input">

                <span class="form-label">종료일</span>
                <input type="date" name="user_activity_time_end" value="${userActivitySearchDTO.user_activity_time_end}" class="form-input">

                <span class="form-label">세부 내용</span>
                <input type="text" name="user_activity_details" placeholder="세부 내용" value="${userActivitySearchDTO.user_activity_details}" class="form-input">

                <input type="hidden" name="page" value="${searchVO.page}">

                <span class="search-button-container">
                    <input type="submit" value="검색">
                </span>
            </div>
        </form>

        <%-- 페이징 및 공지 출력 --%>
        <div id="logListContainer">
            <jsp:include page="log_list.jsp" />
        </div>
</body>
</html>