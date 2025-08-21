<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/findLoginId_view.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

<main>
<div class="container">
    <h3>아이디 찾기</h3>

    <form:form modelAttribute="findLoginIdDto" action="/find_loginId" method="post">
        <label for="name">이름</label>
        <form:input path="name"/>
        <form:errors path="name" cssClass="error-msg"/> <br />

        <label for="email">이메일</label>
        <form:input path="email"/>
        <form:errors path="email" cssClass="error-msg"/> <br />

        <input type="submit" value="아이디 찾기" />
        
        <c:if test="${not empty loginId }">
        <p class="p">조회하신 아이디는 <strong>${loginId }</strong>입니다.</p>
  	    </c:if>

   		<c:if test="${not empty id_error }">
        <p class="p">${id_error }</p>
    	</c:if>
    </form:form>
    <a class="a" href="/login_view">로그인 하기</a>
</div>
</main>

<%@ include file="../common/footer.jsp" %>
</body>
</html>