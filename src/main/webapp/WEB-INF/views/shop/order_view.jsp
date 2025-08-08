<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>



<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문하기</title>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <%@ include file="subheader.jsp" %>
    
    
<style>
.container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}

table td, table th {
    padding: 10px;
    border: 1px solid #ddd;
    text-align: left;
}

table th {
    background-color: #f5f5f5;
    font-weight: bold;
}

input[type="text"], input[type="email"], input[type="tel"], textarea {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}

.payment-buttons {
    display: flex;
    gap: 10px;
    margin: 20px 0;
    flex-wrap: wrap;
}

.payment-btn {
    padding: 10px 20px;
    border: 1px solid #ddd;
    background: white;
    cursor: pointer;
    border-radius: 4px;
    transition: all 0.3s;
    font-weight: normal;
}

.payment-btn:hover {
    background-color: #f0f0f0;
}

/* 선택된 결제 방식 스타일 - 빨간색 */
.payment-btn.selected {
    background-color: #dc3545;  /* 빨간색 배경 */
    color: white;
    border-color: #dc3545;
    font-weight: bold;
}

.order-btn {
    width: 100%;
    padding: 15px 30px;
    font-size: 18px;
    font-weight: bold;
    background: #28a745;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    margin-top: 20px;
}

.order-btn:hover {
    background: #218838;
}

.total-price {
    font-size: 20px;
    font-weight: bold;
    color: #dc3545;
}
</style>    
</head>
<body>
	
	

<!-- 주문 폼 --> 		<!-- 수정 /shop/kakaopay -->      <!-- /shop/order_write --> 
<form id="orderForm" action="/shop/order_write" method="post">
	
	<!-- 세션에서 user_id 가져오기, 없으면 기본값 1 -->
	<c:set var="currentUserId" value="${not empty sessionScope.user_id ? sessionScope.user_id : '1'}" />
	<input type="hidden" name="user_id" value="${currentUserId}">
	<input type="hidden" name="order_id" value="0">
    
    <!-- 주문자 정보 -->
    <h3>주문자 정보</h3>
    <table border="1">
        <tr>
            <td>이름*</td>
            <td><input type="text" name="order_name" required></td>
        </tr>
        <tr>
            <td>이메일*</td>
            <td><input type="email" name="order_email" required></td>
        </tr>
        <tr>
            <td>전화번호*</td>
            <td><input type="tel" name="order_phone" required placeholder="010-0000-0000"></td>
        </tr>
    </table>
    
    <!-- 배송지 정보 -->
    <h3>배송지 정보</h3>
    <table border="1">
		<tr>
			<td>배송지명</td>
			<td><input type="text" name="order_deliver_name" required/></td>
		</tr>
        <tr>
            <td>받는사람*</td>
            <td><input type="text" name="order_receiver_name" required></td>
        </tr>
        <tr>
            <td>전화번호*</td>
            <td><input type="tel" name="order_receiver_tel" required placeholder="010-0000-0000"></td>
        </tr>
        <tr>
            <td>우편번호</td>
            <td>
                <input type="text" id="zipcode" name="order_zipcode" readonly style="width: 100px;">
                <button type="button" onclick="findAddress()">주소찾기</button>
            </td>
        </tr>
        <tr>
            <td>주소*</td>
            <td><input type="text" id="address" name="order_loc" required readonly style="width: 400px;"></td>
        </tr>
        <tr>
            <td>상세주소</td>
            <td><input type="text" id="detailAddress" name="order_detailloc" style="width: 400px;" placeholder="동/호수, 건물명 등"></td>
        </tr>
        <tr>
            <td>배송 요청사항</td>
            <td><textarea name="order_request" style="width: 400px;" placeholder="배송 시 요청사항을 입력해주세요"></textarea></td>
        </tr>
    </table>
    
    <!-- 주문 상품 -->
    <h3>주문 상품</h3>
    <c:choose>
	    <c:when test="${not empty cart}">
	        <!-- 장바구니 상품들 -->
			<input type="hidden" name="orderType" value="cart" />
	        <table border="1">
	            <tr>
	                <th>상품명</th>
	                <th>수량</th>
	                <th>가격</th>
	                <th>합계</th>
	            </tr>
	            
	            <c:set var="totalPrice" value="0"/>
	            <c:forEach var="item" items="${cart}">
	                <tr>
	                    <td>${item.product_name}</td>
	                    <td>${item.cart_quantity}개</td>
	                    <td>₩<fmt:formatNumber value="${item.discountedPrice}" pattern="#,###"/></td>
	                    <td>₩<fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/></td>
	                </tr>
	                <c:set var="totalPrice" value="${totalPrice + item.totalPrice}"/>
	            </c:forEach>
	            
	            <tr style="font-weight: bold;">
	                <td colspan="3">최종 결제금액</td>
	                <td>₩<fmt:formatNumber value="${totalPrice}" pattern="#,###"/></td>
	            </tr>
	        </table>
	        
	        
	        <input type="hidden" name="totalAmount" value="${totalPrice}">
	        
	    </c:when>
	    
	    <c:when test="${not empty product}">
	        <!-- 단일 상품 주문 -->
	        <input type="hidden" name="orderType" value="direct" />
	        <table border="1">
	            <tr>
	                <th>상품명</th>
	                <th>수량</th>
	                <th>가격</th>
	                <th>합계</th>
	            </tr>
	            <tr>
	                <td>${product.product_name}</td>
	                <td>${product.quantity}개</td>
	                <td>₩<fmt:formatNumber value="${product.discountedPrice}" pattern="#,###"/></td>
	                <td>₩<fmt:formatNumber value="${product.totalPrice}" pattern="#,###"/></td>
	            </tr>
	            <tr style="font-weight: bold;">
	                <td colspan="3">최종 결제금액</td>
	                <td>₩<fmt:formatNumber value="${product.totalPrice}" pattern="#,###"/></td>
	            </tr>
	        </table>
	        
	        
	        <!-- 단일주문 정보 -->
	        <input type="hidden" name="totalAmount" value="${product.totalPrice}">
	        <input type="hidden" name="product_id" value="${product.product_id}">
	        <input type="hidden" name="quantity" value="${product.quantity}">
	        
	        
	    </c:when>
	    
	    <c:otherwise>
	        <div class="empty-order">
	            <p>주문할 상품이 없습니다.</p>
	            <a href="/shop/products">상품 보러가기</a>
	        </div>
	    </c:otherwise>
	</c:choose>
    
    
   
    

   <!-- 주문 완료 버튼 - choose 밖에서 한 번만 -->
	<c:if test="${not empty cart or not empty product}">
	    <c:set var="finalPrice" value="${not empty cart ? totalPrice : product.totalPrice}"/>
	    <button type="submit" class="order-btn">
	        ₩<fmt:formatNumber value="${finalPrice}" pattern="#,###"/> 주문하기
	    </button>
	</c:if>
    
</form>

	<!-- 내가 이해하는 바는 해당 메서드컨트롤러 가면 ㄱ메서드 처리하는것 -->

<script>

//결제 방식 선택 함수
function selectPayment(method) {
    // 모든 버튼의 selected 클래스 제거
    document.querySelectorAll('.payment-btn').forEach(btn => {
        btn.classList.remove('selected');
    });
    
    // 선택된 버튼에 selected 클래스 추가
    event.target.classList.add('selected');
    
    //여기서 결재 방법을 보낸다.
    // hidden input에 값 설정
    document.getElementById('payment_method').value = method;
}

// 주소찾기 함수
function findAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                addr += extraAddr;
            }

            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById('address').value = addr;
            document.getElementById('detailAddress').focus();
        }
    }).open();
}

// 세션에서 user_id 가져오는 함수
function getUserId() {
    let userId = '${sessionScope.user_id}';
    if (!userId || userId.trim() === '' || userId === 'null') {
        userId = '1'; // 기본값으로 1 사용
    }
    return userId;
}

// 폼 제출 이벤트 처리
document.addEventListener('DOMContentLoaded', function() {
    const orderForm = document.getElementById('orderForm');
    
    orderForm.addEventListener('submit', function(e) {
        // 필수 입력 필드 검사
        const requiredFields = [
            {name: 'order_name', label: '주문자 이름'},
            {name: 'order_email', label: '이메일'},
            {name: 'order_phone', label: '주문자 전화번호'},
            {name: 'order_receiver_name', label: '받는사람 이름'},
            {name: 'order_receiver_tel', label: '받는사람 전화번호'}
        ];
        
        for (let field of requiredFields) {
            const element = document.querySelector('input[name="' + field.name + '"]');
            if (!element || !element.value.trim()) {
                alert(field.label + '을(를) 입력해주세요.');
                if (element) element.focus();
                e.preventDefault();
                return false;
            }
        }
        
        // 주소 확인
        const address = document.getElementById('address').value.trim();
        if (!address) {
            alert('주소를 입력해주세요.');
            e.preventDefault();	
            return false;
        }
        
        // 결제 방식 확인
        const paymentMethod = document.getElementById('payment_method').value;
        if (!paymentMethod) {
            alert('결제 방식을 선택해주세요.');
            e.preventDefault();
            return false;
        }
        
    });
});
</script>

</body>
</html>