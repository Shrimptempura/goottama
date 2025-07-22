<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>cart</h2>

<div>
	<p>하나의 단락입니다.</p>
	<img src="" alt="" />	
	
	
	<div>장바구니 버튼을 누르면 발생하는 alert</div>
	<button onclick="showAlert()">클릭</button>
	<script>
	   alert("${msg}");
		function showAlert(){
			alert("버튼이 눌렸습니다.");
			location.href='/cart_write';
		}
	</script>
	
	
	<%-- 	
 	 <div>
 		<p>cart: ${cart.cart_id }</p>
		<p>cart: ${cart.user_id }</p> 		
		<p>cart: ${cart.product_id }</p> 		
		<p>cart: ${cart.product_id }</p>		
 	
 	</div>
 --%>
 
	<div>
		<p>cart_id: ${cart.cart_id }</p>
		<p>user_id: ${cart.user_id }</p>
		<p>product_id: ${cart.product_id }</p>
		<p>cart_quantity: ${cart.cart_quantity }</p>
		<p>cart_date: ${cart.cart_date }</p>
	
	</div>
 

</body>
</html>