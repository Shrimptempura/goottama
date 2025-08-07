<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
    <title>관리자 에러페이지</title>
</head>
<body>
    <div style="border: 1px solid red; padding: 20px; background-color: #ffe6e6;">
        <h1>🚨 관리자 시스템 오류</h1>

        <c:if test="${not empty errorTitle}">
            <h2>${errorTitle}</h2>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p>${errorMessage}</p>
        </c:if>

        <hr/>

        <h3>요청 상세 정보</h3>
        <ul>
            <c:if test="${not empty requestURI}">
                <li><strong>요청 주소:</strong> <code>${requestURI}</code></li>
            </c:if>

            <c:if test="${not empty targetType}">
                <li><strong>전달된 targetType:</strong> <code>${targetType}</code></li>
            </c:if>

            <c:if test="${not empty targetId}">
                <li><strong>전달된 targetId:</strong> <code>${targetId}</code></li>
            </c:if>

            <li><strong>발생 시각:</strong> <%= new java.util.Date() %></li>
        </ul>
    </div>
</body>
</html>