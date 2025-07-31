<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/findPw" method="post" >
비밀번호 찾기 <br />
아이디 : <input type="text" name="loginId" />
이메일 : <input type="text" name="email" />
<input type="submit" value="인증 이메일 보내기" />
<c:if test="${not empty email_error }"><p>${email_error }</p></c:if>
</form>
</body>
</html>