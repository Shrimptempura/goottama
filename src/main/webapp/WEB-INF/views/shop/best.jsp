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
    .best-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 20px;
    }
    
    .best-title {
        text-align: center;
        font-size: 2.5rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 30px;
        border-bottom: 3px solid #007bff;
        padding-bottom: 15px;
    }
    
    .product-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        gap: 20px;
        margin-top: 20px;
    }
    
    .product-card {
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        overflow: hidden;
        transition: all 0.3s ease;
        position: relative;
    }
    
    .product-card:hover {
        transform: translateY(-8px);
        box-shadow: 0 8px 25px rgba(0,0,0,0.15);
    }
    
    .product-image {
        width: 100%;
        height: 200px;
        background: #f8f9fa;
        display: flex;
        align-items: center;
        justify-content: center;
        position: relative;
        overflow: hidden;
    }
    
    .product-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
    
    .no-image {
        color: #6c757d;
        font-size: 1.1rem;
    }
    
    .best-badge {
        position: absolute;
        top: 10px;
        left: 10px;
        background: linear-gradient(45deg, #ff6b6b, #ff8e53);
        color: white;
        padding: 5px 12px;
        border-radius: 20px;
        font-size: 0.8rem;
        font-weight: bold;
        z-index: 1;
    }
    
    .product-info {
        padding: 20px;
    }
    
    .product-name {
        font-size: 1.2rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 8px;
        line-height: 1.4;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
    
    .product-mall {
        color: #007bff;
        font-size: 0.9rem;
        margin-bottom: 12px;
        padding: 4px 8px;
        background: #e3f2fd;
        border-radius: 15px;
        display: inline-block;
    }
    
    .price-section {
        margin-top: 15px;
    }
    
    .discount-rate {
        color: #e74c3c;
        font-weight: bold;
        font-size: 1.1rem;
        margin-right: 8px;
    }
    
    .product-price {
        font-size: 1.3rem;
        font-weight: bold;
        color: #333;
    }
    
    .original-price {
        color: #999;
        text-decoration: line-through;
        font-size: 0.9rem;
        margin-left: 8px;
    }
    
    .review-info {
        margin-top: 10px;
        padding: 8px 0;
        border-top: 1px solid #eee;
        color: #666;
        font-size: 0.9rem;
    }
    
    .review-count {
        color: #007bff;
        font-weight: bold;
    }
    
    .product-card-link {
        text-decoration: none;
        color: inherit;
        display: block;
    }
    
    .product-card-link:hover {
        text-decoration: none;
        color: inherit;
    }
    
    @media (max-width: 768px) {
        .product-grid {
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 15px;
        }
        
        .best-container {
            padding: 15px;
        }
        
        .best-title {
            font-size: 2rem;
        }
    }
</style>
</head>
<body>
<div class="best-container">
    <h2 class="best-title">🏆 베스트 상품</h2>
    
    <div class="product-grid">
        <c:forEach var="product" items="${product_list}" varStatus="status">
            <a href="product_detail?product_id=${product.product_id}" class="product-card-link">
                <div class="product-card">
                    <!-- 베스트 순위 배지 -->
                    <c:if test="${status.index < 3}">
                        <div class="best-badge">
                            <c:choose>
                                <c:when test="${status.index == 0}">🥇 1위</c:when>
                                <c:when test="${status.index == 1}">🥈 2위</c:when>
                                <c:when test="${status.index == 2}">🥉 3위</c:when>
                            </c:choose>
                        </div>
                    </c:if>
                    
                    <!-- 상품 이미지 -->
                    <div class="product-image">
                        <c:choose>
                            <c:when test="${not empty product.product_imgurl}">
                                <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                            </c:when>
                            <c:otherwise>
                                <span class="no-image">이미지 없음</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <!-- 상품 정보 -->
                    <div class="product-info">
                        <div class="product-name">${product.product_name}</div>
                        <div class="product-mall">${product.product_mall_name}</div>
                        
                        <div class="price-section">
                            <c:if test="${product.product_discountrate > 0}">
                                <span class="discount-rate">${product.product_discountrate}%</span>
                                <span class="original-price">
                                    <fmt:formatNumber value="${product.product_price / (1 - product.product_discountrate/100)}" 
                                                    pattern="#,###"/>원       
                                </span>
                            </c:if>
                            <div class="product-price">
                            	<fmt:formatNumber value="${product.product_price*(1-product.product_discountrate)}" pattern="#,###"/>원
                            </div>
                        </div>
                        
                        <!-- 리뷰 정보 -->
                        <c:if test="${not empty product.review_count}">
                            <div class="review-info">
                                ⭐ 리뷰 <span class="review-count">${product.review_count}</span>개
                            </div>
                        </c:if>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
    
    <!-- 상품이 없을 경우 -->
    <c:if test="${empty product_list}">
        <div style="text-align: center; padding: 50px; color: #666;">
            <h3>베스트 상품이 없습니다.</h3>
            <p>아직 등록된 상품이 없거나 리뷰가 없는 상태입니다.</p>
        </div>
    </c:if>
</div>
</body>
 <%@ include file="./shoplist.jsp" %>
</html>