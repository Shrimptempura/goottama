<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>상품 문의 작성</title>

</head>
<body>
<h2>product_inquiry_update_view</h2>


<div>

<form action="product_inquiry_update">

	${product_inquiry.pinquiry_id }
	<br />
		${product_inquiry.user_id }
		${product_inquiry.user_nickname }
		${product_inquiry.pinquiry_date }
		${product_inquiry.pinquiry_status }
	<br />
	
	<input type="hidden" name="product_id" value="${product_inquiry.product_id }"/>
	<input type="hidden" name="pinquiry_id" value="${product_inquiry.pinquiry_id }"/>
	<input name="pinquiry_content" type="text" value="${product_inquiry.pinquiry_content }" style="width: 300px; height: 200px"/>

	<br />
	<input type="submit" value="수정" />

</form>	


</div>

</body>
 <%@ include file="./shoplist.jsp" %>
</html>