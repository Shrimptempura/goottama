<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<style>



.order_person {
	width: 300px; /* 적당한 너비 지정 */
    box-shadow: 0 2px 6px rgba(0,0,0,0.5);
    padding: 10px;
    box-sizing: border-box;
}

.order_person p{
	font-weight: bold;
	font-size: 18px;
}

.order_group{
	display: flex;
	flex-direction: column;

}

.order_group label{
	margin-bottom: 5px;
}

.order_group input{
	padding: 5px;
	width: 150px;
}

.deliver{
	width: 300px; /* 적당한 너비 지정 */
    box-shadow: 0 2px 6px rgba(0,0,0,0.5);
    padding: 10px;
    box-sizing: border-box;	
}

.deliver p{
	font-weight: bold;
	font-size: 18px;
}

.deliver_group{
	display: flex;
	flex-direction: column;
}

.payment{
	width: wrap; /* 적당한 너비 지정 */
    box-shadow: 0 2px 6px rgba(0,0,0,0.5);
    padding: 10px;
    box-sizing: border-box;	
}

.payment p{
	font-weight: bold;
	font-size: 18px;
}

.payment_group{
	display: flex;
	
}

</style>

<script>
const buttons = document.querySelectorAll('.payment-button');
buttons.forEach(btn => {
	btn.addEventListener('click', () => {
		buttons.forEach(b => b.classList.remove('selected'));
		btn.classList.add('selected');
	});
});
</script>

<body>
<h2>order_view</h2>
<p>하나의 단락입니다.</p>

<div class="maincontainer">

	<div class="order_person">
		<p>주문자</p>
		<hr />
		<div class="order_group">
			<label>이름</label>
			<input type="text" />
		</div>
		<div class="order_group">
			<label>이메일</label>
			<input type="text" />
		</div>
		<div class="order_group">
			<label>전화번호</label>
			<input type="text" />
		</div>
	</div>
	
	
	<div class="deliver">
		<p>배송지</p>
		<hr />
		<div class="deliver_group">
			<label>배송지명</label>
			<input type="text" />
		</div>
		<div class="deliver_group">
			<label>받는사람</label>
			<input type="text" />
		</div>
		<div class="deliver_group">
			<label>전화번호</label>
			<input type="text" />
		</div>
		<div class="deliver_group">
			<label>주소</label><button>주소찾기</button>
			<input type="text" />
		</div>
	</div>
	
	
	
	<div class="product">
		<c:forEach items="${cart }"  var="cart_list">
		    <p>${cart_list.productDto.product_name}</p>
		    <p>${cart_list.productDto.product_price}원</p>
		    <p>${cart_list.productDto.product_mall_name}</p>
		    <p>${cart_list.productDto.cart_quantity}개</p>
		</c:forEach>
	</div>
	
	
	<div class="payment">
		<p>결재하기</p>
		<div class="payment-options">
			<button class="payment-button">
				<p>카카오페이 </p><br />
				<img src="/static/uploads/shop/1752725789664_9주차 시퀀스 쿼리.PNG" alt="" style="width:100px; height: 100px;"/>
			</button >
			<button class="payment-button">
				<p>카드 </p><br />
				<img src="/static/uploads/shop/1752725789664_9주차 시퀀스 쿼리.PNG" alt="" style="width:100px; height: 100px;"/>
			</button>
			<button class="payment-button">
				<p>계좌이체 </p><br />
				<img src="/static/uploads/shop/1752725789664_9주차 시퀀스 쿼리.PNG" alt="" style="width:100px; height: 100px;"/>
			</button>
			<button class="payment-button">
				<p>무통장입금 </p><br />
				<img src="/static/uploads/shop/1752725789664_9주차 시퀀스 쿼리.PNG" alt="" style="width:100px; height: 100px;"/>
			</button>
			<button class="payment-button">
				<p>핸드폰 </p><br />
				<img src="/static/uploads/shop/1752725789664_9주차 시퀀스 쿼리.PNG" alt="" style="width:100px; height: 100px;"/>
			</button>
		</div>
	
	</div>
</div>
</body>
</html>