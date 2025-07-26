<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="subheader.jsp" %>
<meta charset="UTF-8">
<title>주문/결제</title>

<style>
.body {
    font-family: "Pretendard Variable","Noto Sans KR", "Apple SD Gothic Neo", 
                "맑은 고딕", "Malgun Gothic", sans-serif;
}

.maincontainer {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}

.order_person {
    width: 300px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    padding: 20px;
    height: fit-content;
}

.order_person p {
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 15px;
    color: #333;
}

.order_group {
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
}

.order_group label {
    margin-bottom: 5px;
    font-weight: 600;
    color: #555;
}

.order_group input {
    padding: 10px;
    border: 2px solid #e0e0e0;
    border-radius: 6px;
    font-size: 14px;
}

.order_group input:focus {
    outline: none;
    border-color: #0066cc;
}

.deliver {
    width: 300px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    padding: 20px;
    height: fit-content;
}

.deliver p {
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 15px;
    color: #333;
}

.deliver_group {
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
}

.deliver_group label {
    margin-bottom: 5px;
    font-weight: 600;
    color: #555;
}

.deliver_group input {
    padding: 10px;
    border: 2px solid #e0e0e0;
    border-radius: 6px;
    font-size: 14px;
}

.product-section {
    flex: 1;
    min-width: 300px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    padding: 20px;
}

.product-title {
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 20px;
    color: #333;
    border-bottom: 2px solid #f0f0f0;
    padding-bottom: 10px;
}

.product-item {
    display: flex;
    align-items: center;
    gap: 15px;
    padding: 15px 0;
    border-bottom: 1px solid #f0f0f0;
}

.product-item:last-child {
    border-bottom: none;
}

.product-image {
    width: 80px;
    height: 80px;
    border-radius: 8px;
    object-fit: cover;
}

.product-info {
    flex: 1;
}

.product-name {
    font-weight: 600;
    color: #333;
    margin-bottom: 5px;
    font-size: 16px;
}

.product-details {
    color: #666;
    font-size: 14px;
    margin-bottom: 5px;
}

.product-price {
    color: #0066cc;
    font-weight: bold;
    font-size: 16px;
}

.payment {
    width: 100%;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    padding: 20px;
    margin-top: 20px;
}

.payment p {
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 20px;
    color: #333;
}

.payment-options {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
    gap: 15px;
}

.payment-button {
    background: white;
    border: 2px solid #e0e0e0;
    border-radius: 8px;
    padding: 15px;
    cursor: pointer;
    transition: all 0.3s;
    text-align: center;
}

.payment-button:hover {
    border-color: #0066cc;
    transform: translateY(-2px);
}

.payment-button.selected {
    border-color: #0066cc;
    background: #f0f8ff;
}

.payment-button p {
    margin: 0 0 10px 0;
    font-weight: 600;
    color: #333;
    font-size: 14px;
}

.payment-button img {
    width: 50px;
    height: 50px;
    object-fit: contain;
}

.order-summary {
    background: #f8f9fa;
    padding: 20px;
    border-radius: 8px;
    margin-top: 20px;
}

.summary-row {
    display: flex;
    justify-content: space-between;
    margin: 10px 0;
    font-size: 16px;
}

.summary-total {
    border-top: 2px solid #e0e0e0;
    padding-top: 15px;
    font-weight: bold;
    font-size: 20px;
    color: #0066cc;
}

.order-btn {
    width: 100%;
    background: #0066cc;
    color: white;
    border: none;
    padding: 18px;
    border-radius: 8px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
    margin-top: 20px;
}

.order-btn:hover {
    background: #0052a3;
}

.error-message {
    background: #ffebee;
    color: #c62828;
    padding: 15px;
    border-radius: 8px;
    margin-bottom: 20px;
    border-left: 4px solid #f44336;
}

.empty-cart {
    text-align: center;
    padding: 40px;
    color: #666;
}
</style>

<script>
    // 사용자 ID 설정
    <c:if test="${not empty cart}">
        var currentUserId = '<c:out value="${cart[0].user_id}" />';
        console.log('JSP에서 설정된 User ID: ' + currentUserId);
    </c:if>
    
    document.addEventListener('DOMContentLoaded', function() {
        console.log('주문 페이지 로드 완료');
        
        // 장바구니 데이터 확인
        const productItems = document.querySelectorAll('.product-item');
        console.log('표시된 상품 수: ' + productItems.length);
        
        // 결제 방법 선택 이벤트
        const paymentButtons = document.querySelectorAll('.payment-button');
        paymentButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                paymentButtons.forEach(b => b.classList.remove('selected'));
                this.classList.add('selected');
                console.log('선택된 결제 방법: ' + this.textContent.trim());
            });
        });
    });
    
    function completeOrder() {
        alert('주문 기능은 추후 구현될 예정입니다.');
    }
</script>
</head>

<body>

<h2 style="text-align: center; margin: 30px 0;">🛒 주문/결제</h2>

<!-- 에러 메시지 표시 -->
<c:if test="${not empty error}">
    <div class="error-message">
        <strong>⚠️ 오류:</strong> ${error}
    </div>
</c:if>

<div class="maincontainer">
    
    <!-- 주문자 정보 -->
    <div class="order_person">
        <p>👤 주문자</p>
        <hr />
        <div class="order_group">
            <label>이름</label>
            <input type="text" placeholder="주문자 이름" />
        </div>
        <div class="order_group">
            <label>이메일</label>
            <input type="email" placeholder="이메일 주소" />
        </div>
        <div class="order_group">
            <label>전화번호</label>
            <input type="tel" placeholder="010-0000-0000" />
        </div>
    </div>
    
    <!-- 배송지 정보 -->
    <div class="deliver">
        <p>🚚 배송지</p>
        <hr />
        <div class="deliver_group">
            <label>배송지명</label>
            <input type="text" placeholder="예: 우리집" />
        </div>
        <div class="deliver_group">
            <label>받는사람</label>
            <input type="text" placeholder="받는사람 이름" />
        </div>
        <div class="deliver_group">
            <label>전화번호</label>
            <input type="tel" placeholder="010-0000-0000" />
        </div>
        <div class="deliver_group">
            <label>주소</label>
            <div style="display: flex; gap: 10px;">
                <input type="text" placeholder="주소를 입력하세요" style="flex: 1;" />
                <button type="button" style="background: #0066cc; color: white; border: none; padding: 10px 15px; border-radius: 6px;">주소찾기</button>
            </div>
        </div>
    </div>
    
    <!-- 주문 상품 -->
    <div class="product-section">
        <div class="product-title">📦 주문 상품</div>
        
        <c:choose>
            <c:when test="${not empty cart}">
                <!-- 총합 계산을 위한 변수 초기화 -->
                <c:set var="totalItems" value="0"/>
                <c:set var="totalQuantity" value="0"/>
                <c:set var="totalOriginal" value="0"/>
                <c:set var="totalDiscount" value="0"/>
                <c:set var="totalFinal" value="0"/>
                
                <c:forEach var="item" items="${cart}">
                    <div class="product-item">
                        <c:choose>
                            <c:when test="${not empty item.product_imgurl}">
                                <img class="product-image" src="/static/uploads/shop/${item.product_imgurl}" alt="${item.product_name}">
                            </c:when>
                            <c:otherwise>
                                <img class="product-image" src="/static/uploads/shop/noimages.png" alt="기본 이미지">
                            </c:otherwise>
                        </c:choose>
                        
                        <div class="product-info">
                            <div class="product-name">${item.product_name}</div>
                            <div class="product-details">
                                ${item.product_mall_name} | 수량: ${item.cart_quantity}개
                                <c:if test="${item.product_istoday == 'Y'}">
                                    | 🚚 당일배송
                                </c:if>
                            </div>
                            <div class="product-price">
                                <c:if test="${item.discountText != ''}">
                                    <span style="text-decoration: line-through; color: #999; font-size: 14px;">
                                        ₩<fmt:formatNumber value="${item.product_price}" pattern="#,###"/>
                                    </span>
                                    <span style="color: #ff4444; font-size: 12px; margin-left: 5px;">
                                        ${item.discountText}
                                    </span><br>
                                </c:if>
                                ₩<fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 총합 계산 -->
                    <c:set var="totalItems" value="${totalItems + 1}"/>
                    <c:set var="totalQuantity" value="${totalQuantity + item.cart_quantity}"/>
                    <c:set var="totalOriginal" value="${totalOriginal + (item.product_price * item.cart_quantity)}"/>
                    <c:set var="totalDiscount" value="${totalDiscount + item.discountAmount}"/>
                    <c:set var="totalFinal" value="${totalFinal + item.totalPrice}"/>
                </c:forEach>
                
                <!-- 주문 요약 -->
                <div class="order-summary">
                    <div class="summary-row">
                        <span>상품 금액 (${totalItems}개)</span>
                        <span>₩<fmt:formatNumber value="${totalOriginal}" pattern="#,###"/></span>
                    </div>
                    <c:if test="${totalDiscount > 0}">
                        <div class="summary-row" style="color: #ff4444;">
                            <span>할인 금액</span>
                            <span>-₩<fmt:formatNumber value="${totalDiscount}" pattern="#,###"/></span>
                        </div>
                    </c:if>
                    <div class="summary-row">
                        <span>배송비</span>
                        <span>무료</span>
                    </div>
                    <div class="summary-row summary-total">
                        <span>최종 결제금액</span>
                        <span>₩<fmt:formatNumber value="${totalFinal}" pattern="#,###"/></span>
                    </div>
                </div>
                
            </c:when>
            <c:otherwise>
                <div class="empty-cart">
                    <h3>주문할 상품이 없습니다</h3>
                    <p>장바구니에 상품을 추가해주세요.</p>
                    <a href="cart" style="color: #0066cc;">장바구니로 이동</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <!-- 결제 방법 -->
    <c:if test="${not empty cart}">
        <div class="payment">
            <p>💳 결제하기</p>
            <div class="payment-options">
                <button class="payment-button">
                    <p>카카오페이</p>
                    <div style="background: #ffeb00; padding: 5px; border-radius: 4px;">Pay</div>
                </button>
                <button class="payment-button">
                    <p>카드</p>
                    <div style="font-size: 24px;">💳</div>
                </button>
                <button class="payment-button">
                    <p>계좌이체</p>
                    <div style="font-size: 24px;">🏦</div>
                </button>
                <button class="payment-button">
                    <p>무통장입금</p>
                    <div style="font-size: 24px;">💰</div>
                </button>
                <button class="payment-button">
                    <p>핸드폰</p>
                    <div style="font-size: 24px;">📱</div>
                </button>
            </div>
            
            <button class="order-btn" onclick="completeOrder()">
                ₩<fmt:formatNumber value="${totalFinal}" pattern="#,###"/> 결제하기
            </button>
        </div>
    </c:if>
</div>

</body>
</html>