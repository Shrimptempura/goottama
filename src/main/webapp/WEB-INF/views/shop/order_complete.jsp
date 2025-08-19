<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 완료</title>
</head>

<script>

function getuserid(){
	var user_id=${loginMember.user_id};
	
	return user_id;
}

</script>

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
<td>${orderInfo.order_id}</td>
</tr>
<tr>
<td>사용자 ID</td>
<td>${orderInfo.user_id}</td>
</tr>
<tr>
<td>주문일시</td>
<td><fmt:formatDate value="${orderInfo.order_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
<td>주문상태</td>
<td>${orderInfo.order_status}</td>
</tr>
<tr>
<td>총 결제금액</td>
<td>₩<fmt:formatNumber value="${orderInfo.order_totalprice}" pattern="#,###"/></td>
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
<td>${product.product_id}</td>
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
<a href="/shop/cart?user_id=${loginMember.user_id}">장바구니</a> |
<a href="/shop/order_details?user_id=${loginMember.user_id}">주문내역</a>
</div>

</div>

</body>
 <%@ include file="./shoplist.jsp" %>
</html>