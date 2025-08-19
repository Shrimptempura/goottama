<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 작성한 리뷰</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/myReview.css" />
</head>
<body>
<%@ include file="../../common/header_navigation_bar.jsp" %>

<div class="nav">
<a href="/mypage/myProfile"> 프로필</a> 
<a href="/mypage/myOrderList">나의쇼핑</a> 
<a href="/mypage/myComment"> <strong>나의활동</strong> </a> 
<a href="/mypage/editProfile_view">설정</a>
</div>

<div class="sub-nav">
<a href="/mypage/myComment">나의게시글</a>
<a href="/mypage/myReview"><strong>내가남긴리뷰</strong></a> 
</div>

<div class="review-list">
    <c:choose>
        <c:when test="${not empty review}">
            <c:forEach var="reviews" items="${review}">
                <div class="review-card" onclick="location.href='${pageContext.request.contextPath}/shop/product_detail?product_id=${reviews.product_id}'">
                    <div class="review-image">
                        <img src="/static/uploads/shop/${reviews.product_imgurl}" alt="상품 이미지">
                    </div>
                    <div class="review-info">
                        <div class="review-header">
                            <span class="review-date"><fmt:formatDate value="${reviews.review_date}" pattern="yyyy.MM.dd" /></span>
                        </div>
                        <div class="review-title">
                            ${reviews.review_title}
                        </div>
                        <div class="review-product">
                            상품명: ${reviews.product_name}
                        </div>
                        <div class="review-content">
                            ${reviews.review_content}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="no-review">
                <h3>작성한 리뷰가 없습니다!</h3>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../../common/footer.jsp" %>
</body>
</html>