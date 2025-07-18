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

.img{
	width: 150px;
	height: 150px;
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
		
	
		<div class="card-container">
			<c:forEach items="${list }" var="product">
				<div class="card">
					<div>${product.product_id }</div>
					
					<c:choose>
						<c:when test="${empty product.product_imgDto or empty product.product_imgDto.product_imgurl}">
							<img class="img" src="/static/uploads/shop/noimages.png" alt="기본 이미지" style="width:150px;">
						</c:when>
						<c:otherwise>
      						<img class="img" src="/static/uploads/shop/${product.product_imgDto.product_imgurl}" alt="상품 이미지" style="width:150px;">
    					</c:otherwise>
					</c:choose>
					<%--   <div>${product.product_imgurl }</div> --%>
			        <%-- <img src="${product.product_image}" alt="${product.name}">
 --%>			    <div class="card-body">
 					<a href="product_detail?product_id=${product.product_id }">${product.product_name}</a> <br />	
			        <a href="product_detail?product_id=${product.product_id }" class="card-price">${product.product_price}원</a> <br />
					
			        </div>
		     	</div>
			</c:forEach>
			
			
			
			
		</div>
		
 	
</div>

</body>
</html>