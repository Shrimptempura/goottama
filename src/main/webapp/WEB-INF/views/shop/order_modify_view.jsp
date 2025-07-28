<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="subheader.jsp" %>
    <meta charset="UTF-8">
    <title>주문 수정</title>
    <style>
        .modify-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .order-info {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .product-list {
            margin-bottom: 30px;
        }
        
        .product-item {
            display: flex;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            margin-right: 15px;
        }
        
        .delivery-form {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        
        .btn-group {
            text-align: center;
            margin-top: 20px;
        }
        
        .btn {
            padding: 10px 20px;
            margin: 0 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        
        .btn:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <div class="modify-container">
        <h2>주문 수정</h2>
        
        <!-- 주문 기본 정보 -->
        <c:if test="${not empty orderDetail}">
            <div class="order-info">
                <h4>주문 정보</h4>
                <p><strong>주문번호:</strong> ${orderDetail.orderId}</p>
                <p><strong>주문일:</strong> <fmt:formatDate value="${orderDetail.orderDate}" pattern="yyyy-MM-dd HH:mm"/></p>
                <p><strong>총 금액:</strong> ₩<fmt:formatNumber value="${orderDetail.totalAmount}" pattern="#,###"/></p>
            </div>
        </c:if>
        
        <!-- 주문 상품 목록 -->
        <div class="product-list">
            <h4>주문 상품</h4>
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
                    <p>주문 상품 정보를 불러올 수 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
        
        <!-- 배송지 수정 폼 -->
        <div class="delivery-form">
            <h4>배송지 정보 수정</h4>
            <form action="order_modify_process" method="post">
                <input type="hidden" name="order_id" value="${orderDetail.orderId}">
                <input type="hidden" name="user_id" value="${user_id}">
                
                <div class="form-group">
                    <label for="deliver_name">받는 사람:</label>
                    <input type="text" id="deliver_name" name="deliver_name" 
                           value="${orderDetail.deliverName}" required>
                </div>
                
                <div class="form-group">
                    <label for="deliver_phone">연락처:</label>
                    <input type="tel" id="deliver_phone" name="deliver_phone" 
                           value="${orderDetail.deliverPhone}" required>
                </div>
                
                <div class="form-group">
                    <label for="deliver_address">배송주소:</label>
                    <input type="text" id="deliver_address" name="deliver_address" 
                           value="${orderDetail.deliverAddress}" required>
                </div>
                
                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">수정 완료</button>
                    <a href="order_detail?order_id=${orderDetail.orderId}" class="btn btn-secondary">취소</a>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        // 폼 제출 시 확인
        document.querySelector('form').addEventListener('submit', function(e) {
            if (!confirm('배송지 정보를 수정하시겠습니까?')) {
                e.preventDefault();
            }
        });
    </script>
</body>
</html>