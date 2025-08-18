<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 문의 내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/myInquiry.css" />
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
<a href="/mypage/myScrapbook"> 상품스크랩북 </a>
<a href="/mypage/myInquiry"> <strong>상품문의내역</strong> </a>
</div>
<c:choose>
    <c:when test="${not empty inquiry}">
        <div class="inquiry-list">
            <c:forEach var="inquiry" items="${inquiry}">
                <div class="inquiry-card" onclick="location.href='${pageContext.request.contextPath}/shop/product_detail?product_id=${inquiry.product_id}'">
                    <div class="inquiry-image">
                        <img src="/static/uploads/shop/${inquiry.product_imgurl}" alt="상품 이미지" />
                    </div>
                    <div class="inquiry-info">
                        <p class="inquiry-id">문의 번호: ${inquiry.pinquiry_id}</p>
                        <p class="inquiry-product"><strong>상품명:</strong> ${inquiry.product_name}</p>
                        <p class="inquiry-date"><strong>문의 날짜:</strong> <fmt:formatDate value="${inquiry.pinquiry_date}" pattern="yyyy.MM.dd" /></p>
                        <p class="inquiry-content"><strong>문의 내용:</strong> ${inquiry.pinquiry_content}</p>
                        <c:if test="${not empty inquiry.preply_content}">
                            <p class="inquiry-reply">💬 답글: ${inquiry.preply_content}</p>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="no-inquiry">
            <h3>문의 내역이 없습니다.</h3>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>