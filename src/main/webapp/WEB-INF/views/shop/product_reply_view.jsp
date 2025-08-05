<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>product_reply_view</title>

</head>
<body>
<div class="best-container">
    <h2 class="best-title">product_reply_view</h2>
 
 	<div>
 		<p>${product_inquiry.pinquiry_content }</p>
 		<p>${product_inquiry.pinquiry_date }</p>
 		<p>${product_inquiry.user_nickname } </p>
 		<textarea > 답글을 작성하시오</textarea>
 	
 	</div>
 
</div>
</body>
</html>