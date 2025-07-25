<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</form>
</body>
</html>