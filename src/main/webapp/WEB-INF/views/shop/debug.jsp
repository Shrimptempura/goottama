<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>베스트 상품</title>


<style>
    .order-card {
        border: 1px solid #ddd;
        border-radius: 10px;
        padding: 16px;
        margin-bottom: 24px;
        background-color: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    }
    .order-header {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 10px;
        border-bottom: 1px solid #eee;
        padding-bottom: 8px;
    }
    .product-card {
        display: flex;
        align-items: center;
        border: 1px solid #f0f0f0;
        border-radius: 8px;
        padding: 10px;
        margin: 8px 0;
        background-color: #fafafa;
    }
    .product-card img {
        border-radius: 6px;
        margin-right: 12px;
    }
    .product-info {
        flex: 1;
    }
    .product-info p {
        margin: 2px 0;
    }
    .order-total {
        font-weight: bold;
        font-size: 16px;
        text-align: right;
        margin-top: 10px;
        color: #333;
    }
</style>
</head>
<body>

   <h2>debug</h2>
    
  <%--   <!-- 이중 c:forEach 구문으로 사용자의 주문에 맞는 상품을 가져올 수 있다.  -->
		<c:forEach var="order" items="${userOrders }">
			<p>사용자의 주문들의 아이디(주문번호):${order.order_id }</p>
			<p>사용자의 주문들의 주문날짜:${order.order_date }</p>
			<p>주문 상품 총갯수:${orderProductsMap[order.order_id].size()}개</p>
			<hr />
			<c:forEach var="product" items="${orderProductsMap[order.order_id] }">
				<img src="/static/uploads/shop/${product.product_imgurl}" width="100px" height="100px"/>
				<p>주문상품 이름:${product.product_name }</p>
				<p>주문상품 수량:${product.op_quantity }</p>
				<p>주문상품 가격:${product.op_price }</p>
					
				<p>주문상품 가격:${product.op_price } * 수량:${product.op_quantity } 개</p>
				<p>수량:${product.op_quantity}개 × ₩<fmt:formatNumber value="${product.op_price}" pattern="#,###"/> = ₩<fmt:formatNumber value="${product.op_totalprice}" pattern="#,###"/></p>

				<p>주문상품 총 가격: ${product.op_totalprice }</p>
				<hr />
			</c:forEach>
			<p>주문 총 가격: ₩${order.order_totalprice }</p>
			<p>주문 총 가격: ₩<fmt:formatNumber value="${order.order_totalprice}" pattern="#,###"/></p>
			<hr />
		</c:forEach> --%>

	<!-- 주문 카드 -->
<c:forEach var="order" items="${userOrders}">
    <div class="order-card">
        
        <div class="order-header">
            주문번호: ${order.order_id}  
            <span style="font-size:14px; font-weight:normal; color:#666;">(${order.order_date})</span>
            
            
            <!-- 배송 정보 -->     
             <c:set var="deliverinfo" value="${orderDeliverMap[order.order_id] }" />
             	<p>배송상태:${deliverinfo.deliver_status }</p>
         	
            
        </div>
        
        
        <p>주문 상품 총갯수: ${orderProductsMap[order.order_id].size()}개</p>

        <!-- 상품 카드 반복 -->
        <c:forEach var="product" items="${orderProductsMap[order.order_id]}">
            <div class="product-card">
                <img src="/static/uploads/shop/${product.product_imgurl}" width="80px" height="80px" alt="${product.product_name}" />
                <div class="product-info">
                    <p><strong>${product.product_name}</strong></p>
                    <p>수량: ${product.op_quantity}개</p>
                    <p>단가: ₩<fmt:formatNumber value="${product.op_price}" pattern="#,###"/></p>
                    <p>총액: ₩<fmt:formatNumber value="${product.op_totalprice}" pattern="#,###"/></p>
                </div>
            </div>
        </c:forEach>

        <!-- 주문 총 가격 -->
        <div class="order-total">
            주문 총 가격: ₩<fmt:formatNumber value="${order.order_totalprice}" pattern="#,###"/>
        </div>
       
    </div>
</c:forEach>
</html>