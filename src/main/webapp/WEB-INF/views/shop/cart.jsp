<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet" />
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
<style>
.body{
 font-family: "Pretendard Variable","Noto Sans KR",  "Apple SD Gothic Neo", 
 			"맑은 고딕", "Malgun Gothic", sans-serif;
}

.maincontainer{
	position: flex;

}

.container{
	position:relative;
	left:30%;
	flex-grow: 1;
	display: flex;
	flex-wrap: wrap;
	gap:20px;
  	width: calc(25% - 20px); /* 4칸 (100% / 4 - gap) */
 	
 	box-shadow: 0 2px 6px rgba(0,0,0,0.50);
 	padding: 10px;
 	box-sizing: border-box;
}

.delete{
	position:relative;
	left:90%;
}

.productname{
	text-decoration: none;
	color: black;
}

.img{
	width: 100px;
	height: 150px;
}

.buttons{
	position: relative;
	left: 40%;
}

.price{
	position: relative;
	left: 40%;
	font-family : ;
	font-weight: bold;
}

.pricecontainer {
    width: 300px; /* 적당한 너비 지정 */
    position: absolute; 
    top: 30%;         /* 적절히 조절 */
    right: 20%;       /* 오른쪽 붙이기 */
    box-shadow: 0 2px 6px rgba(0,0,0,0.5);
    padding: 10px;
    box-sizing: border-box;
}

.order{
	width: 250px;
	height: 100px;
}

</style>


	
<script>
	//장바구니 개별 상품 삭제
	function deleteCartItem(userId, productId) {
	    if (confirm('이 상품을 장바구니에서 삭제하시겠습니까?')) {
	        location.href = 'cart_delete?user_id=' + userId + '&product_id=' + productId;
	    }
	}
	
	// 방법 1: 각 아이템마다 user_id를 전달하는 방식 (권장)
	function changeCount(cartId, value) {
	    const span = document.getElementById('count_' + cartId);
	    let count = parseInt(span.innerText);
	    const newCount = count + value;

	    if (newCount < 1) {
	        alert('수량은 1개 이상이어야 합니다.');
	        return;
	    }
	    
	 	// 세션에서 user_id 가져오기
	    let userId = '${sessionScope.user_id}';
	    if (!userId || userId.trim() === '' || userId === 'null') {
	        userId = '2'; // 기본값
	    }
	    

	    // 서버에 수량 업데이트 요청
	    if (confirm('수량을 변경하시겠습니까?')) {
	        location.href = 'cart_update?cart_id=' + cartId + '&cart_quantity=' + newCount + '&user_id=' + userId;
	    }
	}
	function goToOrder(){
		// 장바구니에 상품이 있는지 확인
		const cartItems = document.querySelectorAll('.container').length;
		

		if (cartItems === 0) {
		       alert('장바구니에 상품이 없습니다.');
		       return;
		   }

		
		   let userId = '${sessionScope.user_id}';
		   	    if (!userId || userId.trim() === '' || userId === 'null') {
		   	    userId = '2'; // 기본값으로 2 사용
		   } 
		  	
		   location.href = "order_view?user_id=" + userId;
	}
	
	function showAlert(){
			alert("장바구니에 담았습니다.");
			location.href="cart_write?user_id=2&product_id=${product.product_id }&cart_quantity="+count;
		}
</script>	
	
	
	
</head>
<body>

<h2>cart</h2>
 
<div class="maincontainer">
    
    <!-- 장바구니 아이템들 -->
    <div class="cart-items-container">
        <c:choose>
            <c:when test="${not empty cart}">
                <c:forEach var="item" items="${cart}">
                    <div class="container">
                        <!-- 삭제 버튼 -->
                        <button class="delete" onclick="deleteCartItem(${item.user_id},${item.product_id })">x</button>
                        
                        <!-- 상품 이미지 -->
                        <div class="image-section">
                            <c:choose>
                                <c:when test="${not empty item.product_imgurl}">
                                    <img class="img" src="/static/uploads/shop/${item.product_imgurl}" alt="${item.product_name}">
                                </c:when>
                                <c:otherwise>
                                    <img class="img" src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        <!-- 상품 정보 -->
                        <div class="product-info">
                            <p class="mall-name">${item.product_mall_name}</p>
                            <a class="productname" href="product_detail?product_id=${item.product_id}">${item.product_name}</a>
                            
                            <!-- 수량 조절 -->
                            <div class="quantity-controls">
                                <button type="button" class="quantity-btn" onclick="changeCount(${item.cart_id}, -1)">-</button>
                                <span id="count_${item.cart_id}" class="quantity-display">${item.cart_quantity}</span>
                                <button type="button" class="quantity-btn" onclick="changeCount(${item.cart_id}, 1)">+</button>
                            </div>
                            
                            <!-- 당일배송 여부 -->
                            <c:if test="${item.product_istoday == 'Y'}">
                                <div class="today-delivery">당일배송</div>
                            </c:if>
                        </div>
                        
                        <!-- 가격 정보 -->
                        <div class="price-section">
                            <!-- 할인 정보 -->
                            <c:if test="${item.discountText != ''}">
                                <div>
                                    <span class="discount-badge">${item.discountText}</span>
                                </div>
                                <div class="original-price">
                                    ₩<fmt:formatNumber value="${item.product_price}" pattern="#,###"/>
                                </div>
                            </c:if>
                            
                            <!-- 판매가 -->
                            <div class="sale-price">
                                ₩<fmt:formatNumber value="${item.discountedPrice}" pattern="#,###"/>
                            </div>
                            
                            <!-- 아이템 총액 -->
                            <div class="item-total">
                                소계: ₩<fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <h3>장바구니가 비어있습니다.</h3>
                    <p>원하는 상품을 장바구니에 담아보세요!</p>
                    <a href="products">쇼핑하러 가기</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <!-- 주문 요약 -->
    <c:if test="${not empty cart}">
        <div class="pricecontainer">
            <div class="summary-title">주문 요약</div>
            
            <!-- 총합 계산 -->
            <c:set var="totalItems" value="0"/>
            <c:set var="totalQuantity" value="0"/>
            <c:set var="totalOriginal" value="0"/>
            <c:set var="totalDiscount" value="0"/>
            <c:set var="totalFinal" value="0"/>
            
            <c:forEach var="item" items="${cart}">
                <c:set var="totalItems" value="${totalItems + 1}"/>
                <c:set var="totalQuantity" value="${totalQuantity + item.cart_quantity}"/>
                <c:set var="totalOriginal" value="${totalOriginal + (item.product_price * item.cart_quantity)}"/>
   
                <c:set var="totalFinal" value="${totalFinal + item.totalPrice}"/>
            </c:forEach>
            
            <!-- 요약 정보 표시 -->
            <div class="summary-row">
                <span>상품 종류</span>
                <span>${totalItems}개</span>
            </div>
            <div class="summary-row">
                <span>총 수량</span>
                <span>${totalQuantity}개</span>
            </div>
            <div class="summary-row">
                <span>상품금액</span>
                <span>₩<fmt:formatNumber value="${totalOriginal}" pattern="#,###"/></span>
            </div>
            <c:if test="${totalDiscount > 0}">
                <div class="summary-row" style="color: #ff4444;">
                    <span>할인금액</span>
                    <span>-₩<fmt:formatNumber value="${totalDiscount}" pattern="#,###"/></span>
                </div>
            </c:if>
            <div class="summary-row summary-total">
                <span>최종결제금액</span>
                <span>₩<fmt:formatNumber value="${totalFinal}" pattern="#,###"/></span>
            </div>
            
            <button class="order-btn" onclick="goToOrder()">주문하기</button>
        </div>
    </c:if>
</div>
			
			
		
		
		
		

</body>
</html>