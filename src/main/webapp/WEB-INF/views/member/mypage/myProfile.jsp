<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/myProfile.css" />
</head>
<body>
<%@ include file="../../common/header_navigation_bar.jsp" %>

<div class="nav">
<a href="/mypage/myProfile"> <strong>프로필</strong> </a> 
<a href="/mypage/myOrderList">나의쇼핑</a> 
<a href="/mypage/myComment">나의활동</a>
<a href="/mypage/editProfile_view">설정</a> 
</div>

<div class="profile-wrapper">
    <div class="profile-header">
        <img src="${pageContext.request.contextPath}${loginMember.profileImgUrl}" alt="프로필 이미지" />
        <div class="profile-info">
            <h2>${loginMember.user_nickname}</h2>
            <p>가입일:${loginMember.user_created_at}</p>
        </div>
    </div>

    <c:choose>
        <c:when test="${not empty review}">
            <div class="review-list">
                <c:forEach var="reviews" items="${review}">
                    <div class="review-card" onclick="location.href='${pageContext.request.contextPath}/shop/product_detail?product_id=${reviews.product_id}'">
                        <img src="/static/uploads/shop/${reviews.product_imgurl}" alt="상품 이미지" />
                        <div class="review-date">
                            <fmt:formatDate value="${reviews.review_date}" pattern="yyyy.MM.dd" />
                        </div>
                    </div>
                </c:forEach>
            </div>
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