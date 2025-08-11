<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이메일 문의</title>
</head>
<body>

<h3>이메일 문의하기</h3>

<form action="/sendInquiry" method="post">
	제목:<input type="text" name="subject" required="required"/> <br />
	이메일 : <input type="text" name="email" required="required"/> <br />
	내용: <textarea rows="" cols="" name="message" required="required"></textarea> <br />
	<input type="submit" value="보내기" />
</form>


</body>
</html>