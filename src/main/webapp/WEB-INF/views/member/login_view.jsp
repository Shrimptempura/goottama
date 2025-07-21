<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>login view</h3>

<form action="/login" method="post">
    ID : <input type="text" name="loginId" /><br />
    PW : <input type="password" name="pw" /><br />
    <input type="submit" value="로그인" />
</form>

</body>
</html>