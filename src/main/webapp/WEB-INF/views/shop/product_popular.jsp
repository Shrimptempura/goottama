
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>인기상품</title>
<style>
body {
    margin: 0;
    padding: 20px;
    font-family: Arial, sans-serif;
}

.container {
    max-width: 1200px;
    margin: 0 auto;
}

h2 {
    text-align: center;
    color: #333;
    margin-bottom: 30px;
}

/* 4개씩 가로 배치 */
.product-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    margin-bottom: 40px;
}

.product-card {
    background: white;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 15px;
    text-align: center;
    cursor: pointer;
    transition: transform 0.3s;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.product-image {
    width: 100%;
    height: 200px;
    background-color: #f5f5f5;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.product-image img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
}

.product-name {
    font-size: 14px;
    font-weight: bold;
    margin-bottom: 8px;
    height: 40px;
    overflow: hidden;
}

.product-mall {
    font-size: 12px;
    color: #666;
    margin-bottom: 8px;
}

.product-price {
    margin-bottom: 10px;
}

.original-price {
    text-decoration: line-through;
    color: #999;
    font-size: 12px;
}

.current-price {
    color: #333;
    font-weight: bold;
    font-size: 16px;
}

.discount-rate {
    color: #ff4444;
    font-weight: bold;
    font-size: 12px;
}

/* 반응형 */
@media (max-width: 768px) {
    .product-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 480px) {
    .product-grid {
        grid-template-columns: 1fr;
    }
}
</style>
</head>
<body>

<div class="container">
    <h2>🔥 인기상품</h2>
    
    <div class="product-grid">
        <c:forEach var="product" items="${product_popular}" varStatus="status">
            <div class="product-card" onclick="location.href='/shop/product_detail?product_id=${product.product_id}'">
                
                <!-- 상품 이미지 -->
                <div class="product-image">
                    <c:choose>
                        <c:when test="${not empty product.product_imgurl}">
                            <img src="/static/uploads/shop/${product.product_imgurl}" alt="${product.product_name}">
                        </c:when>
                        <c:otherwise>
                            <img src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- 상품 정보 -->
                <div class="product-mall">${product.product_mall_name}</div>
                <div class="product-name">${product.product_name}</div>
                
                <!-- 가격 정보 -->
                <div class="product-price">
                    <c:set var="hasDiscount" value="${product.product_discountrate != null and product.product_discountrate > 0}" />
                    <c:choose>
                        <c:when test="${hasDiscount}">
                            <div class="discount-rate"><fmt:formatNumber value="${product.product_discountrate * 100}" pattern="#"/>% 할인</div>
                            <div class="original-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></div>
                            <div class="current-price">₩<fmt:formatNumber value="${product.product_price * (1 - product.product_discountrate)}" pattern="#,###"/></div>
                        </c:when>
                        <c:otherwise>
                            <div class="current-price">₩<fmt:formatNumber value="${product.product_price}" pattern="#,###"/></div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
            </div>
        </c:forEach>
    </div>
    
    <!-- 상품이 없는 경우 -->
    <c:if test="${empty product_popular}">
        <div style="text-align: center; padding: 50px; color: #666;">
            <p>인기상품이 없습니다.</p>
            <a href="/shop/home">홈으로 돌아가기</a>
        </div>
    </c:if>
    
</div>

</body>
</html>