<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/findPw_view.css" />
</head>
<body>
<div class="container">
    <form:form modelAttribute="findPwDto" action="/findPw" method="post">
        <h3>비밀번호 찾기</h3>

        <label for="loginId">아이디</label>
        <form:input path="loginId"/>
        <form:errors path="loginId" cssClass="error-msg"/> <br />

        <label for="email">이메일</label>
        <form:input path="email"/>
        <form:errors path="email" cssClass="error-msg"/> <br />

        <input type="submit" value="인증 이메일 보내기"/>

        <c:if test="${not empty email_error}">
            <p>${email_error}</p>
        </c:if>
    </form:form>
    <a href="/login_view">로그인 하기</a>
</div>
</body>
</html>