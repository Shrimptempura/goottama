<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>

<!--font awesome-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<meta charset="UTF-8">
<title>상품 찜 목록</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: #f8f8f8;
    }
    h2 {
        text-align: center;
        margin-top: 20px;
    }
    .like-container {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
        justify-content: center;
        padding: 20px;
    }
    .like-card {
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        width: 220px;
        padding: 15px;
        text-align: left; /* center에서 left로 변경 */
        transition: transform 0.2s;
    }
    .like-card:hover {
        transform: translateY(-5px);
    }
    .like-card img {
        width: 100%;
        height: 200px;
        object-fit: cover;
        border-radius: 8px;
        margin-bottom: 10px;
		
    }
    .product-name {
        font-size: 16px;
        font-weight: bold;
        margin: 5px 0;
    }
    .like-date {
        font-size: 12px;
        color: #777;
    }
	
	.like-card a {
	    text-decoration: none; /* 밑줄 제거 */
	    color: black; /* 글자 색 검은색 */
	}

	.like-card a:hover {
	    color: black; /* 호버 시에도 검은색 유지 */
	}
</style>
</head>
<body>

<h2>상품 찜 목록</h2>

<div class="like-container">
    <c:forEach var="product_like" items="${product_like}">
        <div class="like-card">
	        <img src="/static/uploads/shop/${product_like.product_imgurl}" alt="${product_like.product_name}" />
            <p>쇼핑몰 이름: ${product_like.product_mall_name}</p>
			<div class="product-name">${product_like.product_name}</div>
			<p><fmt:formatNumber value="${product_like.product_discountrate*100}" pattern="#"/>%할인</p> 
			<p class="sale-price">상품 가격: ₩<fmt:formatNumber value="${product_like.product_price * product_like.product_discountrate}" pattern="#,###"/></p>
			<div class="like-date">
               <p>등록일: <fmt:formatDate value="${product_like.plike_date}" pattern="yyyy-MM-dd" /> </p>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>