<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 스크랩북</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/myScrapbook.css" />
</head>
<body>

<div class="nav">
<a href="/mypage/myProfile"> 프로필 </a> 
<a href="/mypage/myOrderList"> <strong>나의쇼핑</strong> </a> 
<a href="/mypage/myComment">나의활동</a> 
<a href="/mypage/editProfile_view">설정</a> 
</div>

<div class="sub-nav">
<a href="/mypage/myOrderList"> 주문배송목록 </a>
<a href="/mypage/myScrapbook"> <strong>상품스크랩북</strong> 
<a href="/mypage/myInquiry"> 상품문의내역 </a>
</div>

<div class="product-grid">
    <c:forEach var="product_like" items="${product_like}">
        <div class="product-card" onclick="location.href='${pageContext.request.contextPath}/shop/product_detail?product_id=${product_like.product_id}'">
            <img src="/static/uploads/shop/${product_like.product_imgurl}" alt="${product_like.product_name}" />
            <div class="product-info">
                <div class="product-mall">${product_like.product_mall_name}</div>
                <div class="product-name">${product_like.product_name}</div>
                <div class="product-discount">
                    <fmt:formatNumber value="${product_like.product_discountrate * 100}" pattern="#"/>% 할인
                </div>
                <div class="product-price">
                    <fmt:formatNumber value="${product_like.product_price * product_like.product_discountrate}" pattern="#,###"/>원
                </div>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>