<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 완료</title>
</head>
<body>

<h2>주문 완료</h2>

<!-- 성공/에러 메시지 -->
<c:if test="${not empty message}">
<p>${message}</p>
</c:if>

<c:if test="${not empty error}">
<p style="color: red;">${error}</p>
</c:if>

<!-- 주문 기본 정보 -->
<c:if test="${not empty orderInfo}">
<h3>주문 정보</h3>
<table border="1">
<tr>
<td>주문번호</td>
<td>${orderInfo.orderId}</td>
</tr>
<tr>
<td>사용자 ID</td>
<td>${orderInfo.userId}</td>
</tr>
<tr>
<td>주문일시</td>
<td><fmt:formatDate value="${orderInfo.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
<td>주문상태</td>
<td>${orderInfo.orderStatus}</td>
</tr>
<tr>
<td>총 결제금액</td>
<td>₩<fmt:formatNumber value="${orderInfo.orderTotalprice}" pattern="#,###"/></td>
</tr>
</table>
</c:if>

<!-- 주문 상품 목록 -->
<c:if test="${not empty orderProducts}">
<h3>주문 상품 (${orderProducts.size()}개)</h3>
<table border="1">
<tr>
<th>상품 이미지</th>
<th>상품번호</th>
<th>상품명</th>
<th>수량</th>
<th>단가</th>
<th>합계</th>
</tr>
<c:forEach var="product" items="${orderProducts}">
<tr>
<td>
    <c:choose>
        <c:when test="${not empty product.product_imgurl}">
            <img src="/static/uploads/shop/${product.product_imgurl}" alt="상품 이미지" style="width: 80px; height: 80px;">
        </c:when>
        <c:otherwise>
            <img src="/static/uploads/shop/noimages.png" alt="기본 이미지" style="width: 80px; height: 80px;">
        </c:otherwise>
    </c:choose>
</td>
<td>${product.productId}</td>
<td>${product.product_name}</td>
<td>${product.op_quantity}개</td>
<td>₩<fmt:formatNumber value="${product.op_price}" pattern="#,###"/></td>
<td>₩<fmt:formatNumber value="${product.op_totalprice}" pattern="#,###"/></td>
</tr>
</c:forEach>
</table>
</c:if>

<!-- 기본 정보 (조회 실패 시) -->
<c:if test="${empty orderInfo and not empty order_id}">
<h3>주문 정보</h3>
<table border="1">
<tr>
<td>주문번호</td>
<td>${order_id}</td>
</tr>
<tr>
<td>총 결제금액</td>
<td>₩<fmt:formatNumber value="${total_price}" pattern="#,###"/></td>
</tr>
</table>
</c:if>

<!-- 버튼들 -->
<div style="margin-top: 30px;">
<a href="/shop/home">쇼핑 계속하기</a> |
<a href="/shop/cart?user_id=${sessionScope.user_id != null ? sessionScope.user_id : '2'}">장바구니</a> |
<a href="/shop/orders?user_id=${sessionScope.user_id != null ? sessionScope.user_id : '2'}">주문내역</a>
</div>

<!-- 디버깅 정보 (개발용) -->
<div style="margin-top: 50px; font-size: 12px; color: #666;">
<h4>디버깅 정보</h4>
<p>order_id: ${order_id}</p>
<p>total_price: ${total_price}</p>
<p>orderInfo: ${orderInfo}</p>
<p>orderProducts 개수: ${orderProducts.size()}</p>

<!-- 첫 번째 상품 정보 확인 -->
<c:if test="${not empty orderProducts and orderProducts.size() > 0}">
<h5>첫 번째 상품 디버깅:</h5>
<p>productId: ${orderProducts[0].productId}</p>
<p>product_name: ${orderProducts[0].product_name}</p>
<p>op_quantity: ${orderProducts[0].op_quantity}</p>
<p>op_price: ${orderProducts[0].op_price}</p>
<p>op_totalprice: ${orderProducts[0].op_totalprice}</p>
<p>product_imgurl: ${orderProducts[0].product_imgurl}</p>
</c:if>
</div>

</body>
</html>