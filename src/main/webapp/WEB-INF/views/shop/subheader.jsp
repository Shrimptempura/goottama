<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../list.jsp" %>

<title>Insert title here</title>

<style>
    .subheader {
        background-color: #f8f9fa;
        border-bottom: 1px solid #ddd;
        padding: 12px 0;
        display: flex;
        justify-content: center;
        gap: 30px;
        font-family: 'Noto Sans KR', sans-serif;
    }

    .subheader a {
        text-decoration: none;
        color: #333;
        font-size: 16px;
        font-weight: 500;
        padding: 8px 12px;
        border-radius: 5px;
        transition: background-color 0.3s, color 0.3s;
    }

    .subheader a:hover {
        background-color: #007bff;
        color: #fff;
    }
</style>
</head>
<body>


<!-- 사용자 정보 표시 -->
<div class="user-info">



	<!-- 로그인된 경우 -->
    <c:choose>
        <c:when test="${not empty loginMember.user_id}">

            <span>현재 사용자: 
                <p>${loginMember.user_nickname }</p> 
            </span>
        </c:when>
       
        <c:otherwise>
            <!-- 비로그인인 경우 -->
            <span>현재 사용자: 
                <span class="current-user" id="currentUserId">비로그인 (기본값: 1)</span>
            </span>
        </c:otherwise>
    </c:choose>
</div>

<h2>subheader</h2>

<script>
// 세션에서 user_id 가져오는 함수
function getUserId() {
    let userId = '${sessionScope.user_id}';
    if (!userId || userId.trim() === '' || userId === 'null') {
        userId = '1'; // 기본값으로 1 사용
    }
    return userId;
}

// 장바구니로 이동
function goToCart() {
    const userId = getUserId();
    location.href = "cart?user_id=" + ${loginMember.user_id};
}

// 주문내역으로 이동
function goToOrderDetails() {
    const userId = getUserId();
    location.href = "order_details?user_id=" + ${loginMember.user_id};
}
</script>

<div class="subheader">
    <a href="home">Home</a>
    <a href="category">Category</a>
    <a href="productmall">ProductMall</a>
    <a href="best">Best</a>
    <a href="todaydeliver">Today Delivery</a>
    <a href="javascript:void(0)" onclick="goToCart()">Cart</a>
    <a href="javascript:void(0)" onclick="goToOrderDetails()">Order Details</a>
    
   
</div>
</body>
</html>