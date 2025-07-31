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

<p>checkPwCodeView</p>

<form action="/checkPwCode" method="post">
	<p>이메일로 받은 인증코드를 입력하세요.</p>
	<input type="text" name="inputCode" required="required" />
	<input type="submit" value="인증하기" />
</form>
<c:if test="${not empty pwCode_error }"><p>${pwCode_error }</p></c:if>

</body>
</html>