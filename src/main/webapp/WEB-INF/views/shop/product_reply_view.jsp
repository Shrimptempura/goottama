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
 
 	<form action="product_reply_write">
 		<div>
 			<input type="hidden" name="pinquiry_id" value="${product_inquiry.pinquiry_id }"/>
 			<input type="hidden" name="product_id" value="${product_inquiry.product_id }"/>
 			<p>${product_inquiry.product_id }</p>
 			<p>문의 내용:${product_inquiry.pinquiry_content }</p>
 			<p>${product_inquiry.pinquiry_date }</p>
 			<p>${product_inquiry.user_nickname } </p>
 			<input type="text" name="preply_content" style="width:300px; height: 200px;" />
		</div>
 		
 		<button type="submit" class="btn btn-primary">답글</button>
 	</form>
 
 	
 				
</div>
</body>
</html>