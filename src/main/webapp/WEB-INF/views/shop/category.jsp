<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

		<div class="card-container">
			<c:forEach items="${list }" var="product">
				<div class="card">
			        <img src="${product.image}" alt="${product.name}">
			        <div class="card-body">
			          <div class="card-title">${product.name}제목</div>
			          <div class="card-price">${product.price}원</div>
			          <a href="#" class="card-button">장바구니</a>
			        </div>
		     	</div>
			</c:forEach>
		</div>
		
     </div>
 	
</div>

</body>
</html>