<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>베스트 상품</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        padding: 20px;
    }
    .review-list {
        max-width: 900px;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        gap: 20px;
    }
    .review-item {
        display: flex;
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        overflow: hidden;
        transition: transform 0.2s ease-in-out;
    }
    .review-item:hover {
        transform: translateY(-3px);
    }
    .review-image {
        flex: 0 0 200px;
    }
    .review-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    .review-content {
        flex: 1;
        padding: 15px;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }
    .review-header {
        display: flex;
        justify-content: space-between;
        font-size: 0.85rem;
        color: #777;
        margin-bottom: 8px;
    }
    .review-title {
        font-size: 1.2rem;
        font-weight: bold;
        margin-bottom: 6px;
        color: #333;
    }
    .review-product {
        font-size: 0.9rem;
        color: #666;
        margin-bottom: 8px;
    }
    .review-text {
        font-size: 0.95rem;
        color: #555;
        line-height: 1.4;
    }
</style>


</head>
<body>

   <h2>shop_review_detail</h2>

	<div class="review-list">
	    <c:forEach var="review" items="${review}">
	        <div class="review-item">
	            <div class="review-image">
	                <img src="/static/uploads/shop/${review.product_imgurl}" alt="상품 이미지">
	            </div>
	            <div class="review-content">
	                <!-- 상단: 리뷰 ID와 작성자 -->
	                <div class="review-header">
	                    <span>리뷰 ID: ${review.review_id}</span>
	                    <span>작성자: ${review.user_nickname}</span>
	                </div>
	
	                <!-- 제목 -->
	                <div class="review-title">리뷰제목: ${review.review_title}</div>
	
	                <!-- 상품 정보 -->
	                <div class="review-product">
	                    상품 ID: ${review.product_id} | 상품명: ${review.product_name}
	                </div>
	
	                <!-- 리뷰 내용 -->
	                <div class="review-text">리뷰내용: ${review.review_content}</div>
	            </div>
	        </div>
	    </c:forEach>
	</div>
	
	
</html>