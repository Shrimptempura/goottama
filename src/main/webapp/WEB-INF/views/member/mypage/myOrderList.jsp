<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/mypageCategory.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/myOrderList.css" />
</head>
<body>

<div class="nav">
<a href="/mypage/myProfile"> 프로필 </a> 
<a href="/mypage/myOrderList"> <strong>나의쇼핑</strong> </a> 
<a href="/mypage/myReview">나의활동</a> 
<a href="/mypage/editProfile_view">설정</a>
</div>

<div class="sub-nav">
<a href="/mypage/myOrderList"> <strong>주문배송목록</strong></a>
<a href="/mypage/myScrapbook">상품스크랩북</a>
<a href="/mypage/myInquiry">나의문의내역</a>
</div>

<div>
    <c:choose>
        <c:when test="${not empty userOrders}">
            <c:forEach var="order" items="${userOrders}">
                <div class="order-card">
                    <div class="order-header">
                        <p><strong>주문번호:</strong> ${order.order_id}</p>
                        <p><strong>주문날짜:</strong> <fmt:formatDate value="${order.order_date}" pattern="MM/dd HH:mm"/></p>
                        <p><strong>배송상태:</strong> ${order.order_status}</p>
                    </div>

                    <c:forEach var="product" items="${orderProductsMap[order.order_id]}">
                        <div class="product-item">
                            <img src="/static/uploads/shop/${product.product_imgurl}" />
                            <div class="product-info">
                                <p><strong>상품명:</strong> ${product.product_name}</p>
                                <p><strong>금액:</strong> ₩<fmt:formatNumber value="${product.op_price}" pattern="#,###"/></p>
                                <p><strong>수량:</strong> ${product.op_quantity}</p>
                            </div>
                        </div>
                    </c:forEach>

                    <div class="order-total">
                        총 금액: ₩<fmt:formatNumber value="${order.order_totalprice}" pattern="#,###"/>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="no-orders">
                <h3>주문 내역이 없습니다</h3>
                <p>첫 주문을 시작해보세요!</p>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="back-to-shop">
        <a href="/shop/home">← 쇼핑 하러가기</a>
    </div>
</div>

</body>
</html>