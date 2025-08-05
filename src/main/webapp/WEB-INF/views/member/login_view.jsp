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
<h3>login view</h3>

<form action="/authenticate" method="post">
    ID : <input type="text" name="loginId" /><br />
    PW : <input type="password" name="pw" /><br />

    <c:if test="${not empty login_error }"><p>${login_error }</p></c:if>

    <input type="submit" value="로그인" />
</form>

<a href="/findLoginId_view">아이디 찾기</a>
<a href="/findPw_view">비밀번호 찾기</a>
<a href="/join_view">회원가입</a>

</body>
</html>