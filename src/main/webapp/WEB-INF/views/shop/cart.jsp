<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet" />
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
<style>


.body{
 font-family: "Pretendard Variable","Noto Sans KR",  "Apple SD Gothic Neo", 
 			"맑은 고딕", "Malgun Gothic", sans-serif;
}

.container{
	position:relative;
	left:30%;
	flex-grow: 1;
	display: flex;
	flex-wrap: wrap;
	gap:20px;
  	width: calc(25% - 20px); /* 4칸 (100% / 4 - gap) */
 	box-shadow: 0 2px 6px rgba(0,0,0,0.50);
 	padding: 10px;
 	box-sizing: border-box;
}

.delete{
	position:relative;
	left:90%;
}

.productname{
	text-decoration: none;
	color: black;
}

.img{
	width: 100px;
	height: 150px;
}

.buttons{
	position: relative;
	left: 40%;
}

.price{
	position: relative;
	left: 40%;
	font-family : ;
	font-weight: bold;
}


.todaydeliver{
	color: right: ;;

}
</style>
	
	
</head>
<body>

<script>
	let count = 1;
							
	function changeCount(value){
	count+=value;
	if (count < 1 ) count=1;
	document.getElementById("count").innerText = count;
	}
	
</script>

<h2>cart</h2>

<div>
	
	
	<a href="shop/cart">cart select</a>
	
	
	
	
	
	<%-- 	
 	 <div>
 		<p>cart: ${cart.cart_id }</p>
		<p>cart: ${cart.user_id }</p> 		
		<p>cart: ${cart.product_id }</p> 		
		<p>cart: ${cart.product_id }</p>		
 	
 	</div>
 --%>
 
 <p>유저 이름:${cart_list.user_id }</p>
 
 
			
			<!-- 주문 상품 -->
			<c:forEach items="${cart }" var="cart_list" >
				<div class="container">
					<button class="delete">X</button>	<!-- 삭제버튼 -->
					<br />
					
					<p class="mallname">${cart_list.productDto.product_mall_name }</p>
					<a class="productname" href="#">${cart_list.productDto.product_name }</a>
					<%-- <p>cart_id:${cart_list.cart_id }</p>	 --%>				<%-- <p>상품 아이디:${cart_list.product_id }</p> --%>
					<%-- <p>장바구니 등록일:${cart_list.cart_date }</p> <br /> --%>
					<c:choose>
						<c:when test="${empty cart_list.product_imgDto or empty cart_list.product_imgDto.product_imgurl}">
							<img class="img" src="/static/uploads/shop/noimages.png" alt="기본 이미지" style="width:150px;">
						</c:when>
						<c:otherwise>
      						<img class="img" src="/static/uploads/shop/${cart_list.product_imgDto.product_imgurl}" alt="상품 이미지" style="width:150px;">
    					</c:otherwise>
					</c:choose>
	
					<div class="buttons">
						<button type="button" onclick="changeCount(-1)">-</button>
							<span id="count">${cart_list.cart_quantity }</span>
							
						<button type="button" onclick="changeCount(1)">+</button> <br />
				
					</div>
					<p></p>
					
					
					<br />
					<div class="todaydeliver">
					<c:choose>
						<c:when test="${cart_list.productDto.product_istoday == 'Y'}">
				     	   <p>당일배송 상품입니다.</p>
					    </c:when>
					    <c:otherwise>
						    <p>당일배송 상품이 아닙니다.</p>
					    </c:otherwise>
					</c:choose>
					</div>
					
					
					<br />
					<p class="price">${cart_list.productDto.product_price }원</p>
				</div>	
				
				
			
			</c:forEach>
 					
 		
			
			
			
			
		</div>
			
			
		
		
		
		

</body>
</html>