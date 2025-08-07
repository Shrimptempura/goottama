<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>resetPwView</p>

<form action="/resetPw" method="post">

비밀번호 : <input type="password" name="resetPw" required="required"/>
비밀번호 확인 : <input type="password" name="resetPw2" required="required"/>
<input type="submit" value="변경하기"/>
</form>

</body>
</html>