<%@page import="org.mariadb.jdbc.client.Context"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>

.main-container{
  	display: flex;
  	align-items: flex-start;
}

.category{
 	width: 200px;
 	flex-shrink: 0; /* 작아지지 않도록 */
	display: flex;
	flex-direction: column;
}

.category a{
	margin-left: 50px;
	padding: 10px;
}
.card-container{
 	flex-grow: 1;
	display: flex;
	flex-wrap: wrap;
	gap:20px;
}
.card {
  width: calc(25% - 20px); /* 4칸 (100% / 4 - gap) */
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
  padding: 10px;
  box-sizing: border-box;
}
</style>


</head>
<body>
<h2>category</h2>


<div>
	<p>하나의 단락입니다.</p>
	<img src="" alt="" />	
	
	
	<div class="main-container">
	
		<div class="category">
			<a href="#">가구</a>
			<a href="#">가전,디지털</a>
			<a href="#">주방용품</a>
			<a href="#">식품</a>
			<a href="#">데코,식물</a>
			<a href="#">조명</a>
			<a href="#">수납정리</a>
			<a href="#">생활용품</a>
		</div>
		<img src="<c:url value='/uploads/shop/1752627941146_img.PNG' />" alt="상품 이미지" />
		<img src="<c:url value='/uploads/shop/1752572314446_9%EC%A3%BC%EC%B0%A8%20%EC%8B%9C%ED%80%80%EC%8A%A4%20%EC%BF%BC%EB%A6%AC.PNG' />" />
		<!-- ✅ 항상 웹 루트 기준 URL 사용 + contextPath 앞에 붙이기 -->
		<img src="<c:url value='/uploads/shop/1752572314446_9주차 시퀀스 쿼리.PNG' />" />
		<img src="src/main/resources/static/uploads/shop/1752572314446_9주차 시퀀스 쿼리.PNG" alt="" />
		<img src="/uploads/shop/1752572314446_9주차 시퀀스 쿼리.PNG" alt="" />
		
		<div class="card-container">
			<c:forEach items="${list }" var="product">
				<div class="card">
					<img src="/uploads/shop/${product.product_imgDto.product_imgurl}" alt="상품 이미지" style="width:150px;">
					<%--   <div>${product.product_imgurl }</div> --%>
			        <%-- <img src="${product.product_image}" alt="${product.name}">
 --%>			        <div class="card-body">
			          <div class="card-title">${product.product_name}제목</div>
			          <div class="card-price">${product.product_price}원</div>
			          <a href="#" class="card-button">장바구니</a>
			        </div>
		     	</div>
			</c:forEach>
			
			
			
			
		</div>
		
 	
</div>

</body>
</html>