<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<style>
.main-container{
  	display: flex;			
}

.container{
	width: 300px;
	margin: 0px auto;
	position: absolute;
	left: 70%;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	border-style: solid;
	border-width: medium;
	border-color: pink;
}

.main-img{
	width: 400px;
	height: 400px;
	background-color: #fff; /* 배경색 (흰색 권장) */
    border: 2px solid #ccc; /* 테두리 */
    border-radius: 10px;  /* 모서리 둥글게 */
    padding: 15px;       /* 내부 여백 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 */
    box-sizing: border-box; /* 패딩이 박스 크기에 포함되도록 */
}

.imgcontainer {
	display: flex;
    position: absolute;
    left: 40%;           /* 필요에 따라 조정 */
    width: 500px;        /* 원하는 크기 지정 */
    gap: 10%;   
}

.thumbnail-column {
    display: flex;
    flex-direction: column;
    gap: 10px;
    max-height: 400px;
    overflow-y: auto;
    position: absolute;
    left: -30%;
    width: 110px;
    margin-right: 15px; /* 메인 이미지와 간격 */
    margin: 0px 0px 0px 0px;
    padding: 10px;       /* 내부 여백 */
    background-color: #fff; /* 배경색 (흰색 권장) */
    border: 2px solid #ccc; /* 테두리 */
    border-radius: 10px;  /* 모서리 둥글게 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 */
    box-sizing: border-box; /* 패딩이 박스 크기에 포함되도록 */
}

.thumbnail {
    width: 80px;
    height: 60px;
    border: 1px solid #ccc;
    cursor: pointer;
}

.title{
	font-size: 20px; 
}

.discountrate{
	color: red;
}

.buttons{
	position: relative;
	left: 20%;
}

.bottombar{
	position: absolute;
	left: 30%;
	top:60%;
    border: 2px solid #ccc; /* 테두리 */
    border-radius: 10px;  /* 모서리 둥글게 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 */
    box-sizing: border-box; /* 패딩이 박스 크기에 포함되도록 */
}

.bottombar a{
    margin: 0 20px;
    text-decoration: none;
    color: #333;
    font-weight: bold;
}

.bottombar a{
	margin: 0px 70px 0px 0px;
}

.productinfo{
	width: 600px;
	position: absolute;
	top:70%;
	left:30%;
	margin: 0 auto;
	padding: 50px;       /* 내부 여백 */
	background-color: #fff; /* 배경색 (흰색 권장) */
    border: 2px solid #ccc; /* 테두리 */
    border-radius: 10px;  /* 모서리 둥글게 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 */
    box-sizing: border-box; /* 패딩이 박스 크기에 포함되도록 */
}

.productinfo2{
	width: 600px;
	position: absolute;
	top:100%;
	left:30%;
	margin: 0 auto;
	padding: 50px;       /* 내부 여백 */
	background-color: #fff; /* 배경색 (흰색 권장) */
    border: 2px solid #ccc; /* 테두리 */
    border-radius: 10px;  /* 모서리 둥글게 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 */
    box-sizing: border-box; /* 패딩이 박스 크기에 포함되도록 */
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
		alert("장바구니에 담았습니다.");
		location.href="cart_write?user_id=13&product_id=${product.product_id }&cart_quantity="+count;
	}
</script>

<body>
<h2>product_detail</h2>
<p>user_id: user1</p>

	
		<div class="main-container">
			<div class="productcontainer">
				<div class="imgcontainer">
					<!-- 왼쪽 썸네일 리스트 (두 번째부터) -->
					<div class="thumbnail-column">
					    <c:forEach var="sub_img" items="${product.product_imgDtoList}">
					    	<img class="thumbnail" src="/static/uploads/shop/${sub_img.product_imgurl}" alt="썸네일 이미지" onclick="changeMainImage(this)"/>
					    </c:forEach>
					</div>
					<c:if test="${not empty product.product_imgDtoList}">
	        			<img id="mainImage" class="main-img" src="/static/uploads/shop/${product.product_imgDtoList[0].product_imgurl}" alt="대표 이미지" />
	    			</c:if>
				</div>
				<div class="bottombar">
					<ul>
						<a href="#">상품정보</a>
					    <a href="#">리뷰</a>
						<a href="#">문의</a> 
						<a href="#">배송/환불</a> 
					</ul>	
				</div>	
			</div>	
				
			<div class="container">		
				<p>${product.product_id }</p>	
				<p class="mallname">${product.product_mall_name }</p>
				<p class="title">${product.product_name }</p>
				<p class="discountrate">${product.product_discountrate * 100}%</p>
				<p class="saleprice">${product.product_price * product.product_discountrate}</p>
				<p class="price">${product.product_price }</p>
				<div class="buttons">
					<button type="button" onclick="changeCount(-1)">-</button>
						<span id="count">1</span>
					<button type="button" onclick="changeCount(1)">+</button> <br />
					<button onclick="showAlert()">장바구니 담기</button>
					<a href="order"><input type="submit" value="주문하기" /></a>
				</div>	
			</div>
		</div>
		
	
		<div class="productinfo">
			<p>상품 정보제공고시</p>
			<div>제조국: ${product.product_madein }</div>
			<div>출시 일자: ${product.product_release }</div>
			<div>AS책임자 전화번호: ${product.product_as_manager_phone }</div>
			<div>제품 종류: ${product.product_type}</div>
			<div>제품 색상: ${product.product_color}</div>
		</div>
		
		
</body>
</html>