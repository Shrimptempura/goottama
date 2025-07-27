<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>	
<meta charset="UTF-8">
<title>주문 내역</title>
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background: #f5f5f5;
}

.main-content {
    max-width: 1000px;
    margin: 20px auto;
    padding: 0 20px;
}

.order-container {
    background: white;
    border-radius: 8px;
    margin-bottom: 15px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    overflow: hidden;
}

.order-header {
    background: #007bff;
    color: white;
    padding: 12px 15px;
    font-size: 14px;
}

.order-header h3 {
    margin: 0 0 8px 0;
    font-size: 16px;
}

.order-header p {
    margin: 3px 0;
    font-size: 13px;
    opacity: 0.9;
}

.order-products {
    padding: 15px;
}

.order-products h4 {
    margin: 0 0 10px 0;
    font-size: 14px;
    color: #333;
}

.product-item {
    display: flex;
    align-items: center;
    padding: 8px 0;
    border-bottom: 1px solid #eee;
}

.product-item:last-child {
    border-bottom: none;
}

.product-image {
    width: 60px;
    height: 60px;
    margin-right: 12px;
    border-radius: 4px;
    object-fit: cover;
}

.product-info {
    flex: 1;
}

.product-info h5 {
    margin: 0 0 5px 0;
    font-size: 14px;
    color: #333;
    font-weight: 500;
}

.product-info p {
    margin: 2px 0;
    font-size: 12px;
    color: #666;
}

.empty-cart {
    text-align: center;
    padding: 40px 20px;
    background: white;
    border-radius: 8px;
}

.empty-cart h3 {
    margin: 0 0 10px 0;
    color: #666;
    font-size: 18px;
}

.empty-cart a {
    display: inline-block;
    padding: 10px 20px;
    background: #28a745;
    color: white;
    text-decoration: none;
    border-radius: 4px;
    font-size: 14px;
    margin-top: 10px;
}

.debug-info {
    background: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 4px;
    padding: 10px;
    margin-bottom: 20px;
    font-size: 12px;
}

.debug-info h4 {
    margin: 0 0 8px 0;
    font-size: 14px;
}

.header-flex {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.total-price {
    font-weight: bold;
    font-size: 15px;
}

.page-header {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
}

.back-link {
    text-align: center;
    margin-top: 30px;
}

.back-link a {
    color: #007bff;
    text-decoration: none;
    font-size: 14px;
}

/* 모바일 대응 */
@media (max-width: 768px) {
    .main-content {
        padding: 0 10px;
    }
    
    .order-header {
        padding: 10px 12px;
    }
    
    .order-products {
        padding: 12px;
    }
    
    .product-image {
        width: 50px;
        height: 50px;
    }
    
    .product-info h5 {
        font-size: 13px;
    }
    
    .product-info p {
        font-size: 11px;
    }
}
</style>
</head>
<body>

<div class="main-content">
    <h2 class="page-header">주문 내역</h2>

    <!-- 디버깅 정보 (개발용) -->
    <div class="debug-info">
        <h4>디버깅 정보</h4>
        <p>사용자 ID: ${user_id} | 주문 개수: ${userOrders.size()}개 | 상품맵 크기: ${orderProductsMap.size()}</p>
    </div>

    <c:choose>
        <c:when test="${not empty userOrders}">
            <c:forEach var="order" items="${userOrders}">
                <div class="order-container">
                    <div class="order-header">
                        <div class="header-flex">
                            <div>
                                <h3>#${order.orderId}</h3>
                                <p><fmt:formatDate value="${order.orderDate}" pattern="MM/dd HH:mm"/> | ${order.orderStatus}</p>
                            </div>
                            <div class="total-price">
                                ₩<fmt:formatNumber value="${order.orderTotalprice}" pattern="#,###"/>
                            </div>
                        </div>
                    </div>
                    
                    <div class="order-products">
                        <h4>주문 상품 (${orderProductsMap[order.orderId].size()}개)</h4>
                        <c:set var="orderProducts" value="${orderProductsMap[order.orderId]}" />
                        
                        <c:choose>
                            <c:when test="${not empty orderProducts}">
                                <c:forEach var="product" items="${orderProducts}">
                                    <div class="product-item">
                                        <c:choose>
                                            <c:when test="${not empty product.product_imgurl}">
                                                <img class="product-image" src="/static/uploads/shop/${product.product_imgurl}" alt="상품 이미지">
                                            </c:when>
                                            <c:otherwise>
                                                <img class="product-image" src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                                            </c:otherwise>
                                        </c:choose>
                                        
                                        <div class="product-info">
                                            <h5>${product.product_name}</h5>
                                            <p>${product.op_quantity}개 × ₩<fmt:formatNumber value="${product.op_price}" pattern="#,###"/> = ₩<fmt:formatNumber value="${product.op_totalprice}" pattern="#,###"/></p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p style="color: #999; font-size: 13px;">상품 정보를 불러올 수 없습니다.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="empty-cart">
                <h3>주문 내역이 없습니다</h3>
                <p>첫 주문을 시작해보세요!</p>
                <a href="home">쇼핑하러 가기</a>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="back-link">
        <a href="home">← 쇼핑 계속하기</a>
    </div>
</div>

</body>
</html>