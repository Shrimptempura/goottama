<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>아이디 찾기</h3>

<form action="/find_loginId" method="post">
    이름 : <input type="text" name="name" value="${findLoginIdDto.name }"/><br />
    이메일 : <input type="text" name="email" value="${findLoginIdDto.email }" /><br />
    <input type="submit" value="아이디 찾기" />
</form>

<c:if test="${not empty loginId }">
	<p>조회하신 아이디는 <strong>${loginId }</strong>입니다.</p>
</c:if>

<c:if test="${not empty id_error }">
	<p>${id_error }</p>
</c:if>

<a href="/login_view">로그인 하기</a>

</body>
</html>