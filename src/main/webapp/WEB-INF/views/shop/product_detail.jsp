<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<style>
.main-container{
  	display: flex;
  	gap: 20px;
  	padding: 20px;
  	max-width: 1200px;
  	margin: 0 auto;
}

.left-section{
	flex: 1;
	display: flex;
	flex-direction: column;
}

.right-section{
	width: 350px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	border: 2px solid pink;
	padding: 20px;
	height: fit-content;
	position: sticky;
	top: 20px;
}

.imgcontainer {
	display: flex;
	gap: 20px;
	margin-bottom: 30px;
}

.thumbnail-column {
    display: flex;
    flex-direction: column;
    gap: 10px;
    width: 100px;
}

.thumbnail {
    width: 80px;
    height: 80px;
    border: 2px solid #ccc;
    border-radius: 8px;
    cursor: pointer;
    object-fit: cover;
}

.thumbnail:hover {
    border-color: #007bff;
}

.main-img{
	width: 500px;
	height: 500px;
	background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 10px;
    padding: 15px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    box-sizing: border-box;
    object-fit: cover;
}

.bottombar{
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin-bottom: 30px;
}

.bottombar ul{
	list-style: none;
	padding: 0;
	margin: 0;
	display: flex;
	gap: 40px;
}

.bottombar a{
    text-decoration: none;
    color: #333;
    font-weight: bold;
    padding: 10px 15px;
    border-radius: 5px;
    transition: background-color 0.3s;
}

.bottombar a:hover{
	background-color: #f8f9fa;
}

.productinfo{
	width: 100%;
	padding: 30px;
	background-color: #fff;
    border: 2px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.productinfo h3{
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 18px;
}

.productinfo div{
	margin-bottom: 10px;
	padding: 8px 0;
	border-bottom: 1px solid #eee;
}

.title{
	font-size: 24px; 
	font-weight: bold;
	margin-bottom: 15px;
}

.mallname{
	color: #666;
	font-size: 16px;
	margin-bottom: 10px;
}

.discountrate{
	color: red;
	font-weight: bold;
	font-size: 18px;
	margin-bottom: 5px;
}

.original-price{
	text-decoration: line-through;
	color: #999;
	font-size: 16px;
	margin-bottom: 5px;
}

.sale-price{
	color: #ff4444;
	font-weight: bold;
	font-size: 24px;
	margin-bottom: 20px;
}

.quantity-controls{
	display: flex;
	align-items: center;
	gap: 10px;
	margin-bottom: 20px;
}

.quantity-controls button{
	width: 30px;
	height: 30px;
	border: 1px solid #ddd;
	background: #f8f9fa;
	cursor: pointer;
	border-radius: 3px;
}

.quantity-controls span{
	min-width: 30px;
	text-align: center;
	font-weight: bold;
}

.action-buttons{
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.action-buttons button{
	padding: 12px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-weight: bold;
	font-size: 16px;
}

.cart-btn{
	background: #28a745;
	color: white;
}

.order-btn{
	background: #007bff;
	color: white;
}

.action-buttons button:hover{
	opacity: 0.9;
}
</style>

<script>
    function changeMainImage(thumbnailElement) {
        const newSrc = thumbnailElement.getAttribute("src");
        document.getElementById("mainImage").setAttribute("src", newSrc);
    }
	
    let count = 1;
	function changeCount(value){
		count+=value;
		if (count < 1 ) count=1;
		 document.getElementById("count").innerText = count;
	}
	function showAlert(){
	    // 세션에서 user_id 가져오기, 없으면 기본값 2
	    let userId = '${sessionScope.user_id}';
	    if (!userId || userId.trim() === '' || userId === 'null') {
	        userId = '2'; // 기본값으로 2 사용
	    }
	    
	    alert("장바구니에 담았습니다.");
	    location.href = "cart_write?user_id=" + userId + "&product_id=${product.product_id}&cart_quantity=" + count;
	}
</script>

<body>
<h2>product_detail</h2>

<div class="main-container">
	<!-- 왼쪽 섹션: 이미지, 하단바, 상품정보 -->
	<div class="left-section">
		<!-- 이미지 영역 -->
		<div class="imgcontainer">
			<!-- 왼쪽 썸네일 리스트 -->
			<div class="thumbnail-column">
			    <c:forEach var="sub_img" items="${product.product_imgDtoList}">
			    	<img class="thumbnail" src="/static/uploads/shop/${sub_img.product_imgurl}" alt="썸네일 이미지" onclick="changeMainImage(this)"/>
			    </c:forEach>
			</div>
			
			<!-- 메인 이미지 -->
			<div class="main-image-container">
				<c:if test="${not empty product.product_imgDtoList}">
	        		<img id="mainImage" class="main-img" src="/static/uploads/shop/${product.product_imgDtoList[0].product_imgurl}" alt="대표 이미지" />
	    		</c:if>
			</div>
		</div>
		
		<!-- 하단바 (이미지 아래) -->
		<div class="bottombar">
			<ul>
				<li><a href="#">상품정보</a></li>
			    <li><a href="#">리뷰</a></li>
				<li><a href="#">문의</a></li> 
				<li><a href="#">배송/환불</a></li> 
			</ul>	
		</div>
		
		<!-- 상품 상세 정보 (하단바 아래) -->
		<div class="productinfo">
			<h3>상품 정보제공고시</h3>
			<div><strong>제조국:</strong> ${product.product_madein }</div>
			<div><strong>출시 일자:</strong> <fmt:formatDate value="${product.product_release}" pattern="yyyy-MM-dd"/></div>
			<div><strong>AS책임자 전화번호:</strong> ${product.product_as_manager_phone }</div>
			<div><strong>제품 종류:</strong> ${product.product_type}</div>
			<div><strong>제품 색상:</strong> ${product.product_color}</div>
		</div>
	</div>
	
	<!-- 오른쪽 섹션: 상품 정보 및 구매 옵션 -->	
	<div class="right-section">		
		<!-- <p style="color: #666; font-size: 14px;">상품번호: ${product.product_id }</p>	 -->
		<p class="mallname">${product.product_mall_name }</p>
		<h1 class="title">${product.product_name }</h1>
		
		<!-- 할인 정보 -->
		<c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
		
		<c:choose>
			<c:when test="${hasDiscount}">
				<!-- 할인이 있는 경우 -->
				<c:set var="discountPercent" value="${product.product_discountrate * 100}" />
				<c:set var="discountAmount" value="${product.product_price * product.product_discountrate}" />
				<c:set var="salePrice" value="${product.product_price - discountAmount}" />
				
				<p class="discountrate"><fmt:formatNumber value="${discountPercent}" pattern="#"/>% 할인</p>
				<p class="original-price">정가: ₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></p>
				<p class="sale-price">할인가: ₩<fmt:formatNumber value="${salePrice}" pattern="#,###"/></p>
			</c:when>
			<c:otherwise>
				<!-- 할인이 없는 경우 -->
				<p class="sale-price">가격: ₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></p>
			</c:otherwise>
		</c:choose>
		
		<!-- 수량 선택 -->
		<div class="quantity-controls">
			<span>수량:</span>
			<button type="button" onclick="changeCount(-1)">-</button>
			<span id="count">1</span>
			<button type="button" onclick="changeCount(1)">+</button>
		</div>
		
		<!-- 구매 버튼들 -->
		<div class="action-buttons">
			<button class="cart-btn" onclick="showAlert()">장바구니 담기</button>
			<button class="order-btn" onclick="location.href='order'">바로 주문하기</button>
		</div>
	</div>
</div>

</body>
</html>