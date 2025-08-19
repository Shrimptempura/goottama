<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 재설정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/resetPw_view.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

<div class="container">
    <h3>비밀번호 재설정</h3>

    <form:form modelAttribute="resetPwDto" action="/resetPw" method="post">
        <label for="resetPw">비밀번호</label>
        <form:password path="resetPw" required="required"/>
        <form:errors path="resetPw" cssClass="error-msg"/> <br />

        <label for="resetPw2">비밀번호 확인</label>
        <form:password path="resetPw2" required="required"/>
        <form:errors path="resetPw2" cssClass="error-msg"/> <br />

        <c:if test="${not empty pw_error}">
            <p>${pw_error}</p>
        </c:if>

        <input type="submit" value="변경하기"/>
    </form:form>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>