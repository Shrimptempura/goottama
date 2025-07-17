<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<style>

.container{
	width: 200px;
	margin: 0px auto;
	position: absolute;
	right: 20%;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	
	border-style: solid;
	border-width: medium;
	border-color: pink;
}


.title{
	font-size: 20px; 
}

.discountrate{
	color: red;
}

</style>

<body>

<h2>subheader</h2>
	

		<div class="container">
			
			<img src="/uploads/shop/${product.product_mall_name }" alt="" />
			<p class="mallname">${product.product_mall_name }</p>
			<p class="title">${product.product_name }</p>
		
			<p class="discountrate">${product.product_discountrate*100 }%</p>
			<p class="saleprice">${product.product_price*product.product_discountrate }</p>
			<p class="price">${product.product_price }</p>
			
		</div>

		
		
		
		<div>${product.product_madein }</div>
		<div>${product.product_release }</div>
		<div>${product.product_as_manager_phone }</div>
		<div>${product.product_type}</div>
		<div>${product.product_color}</div>
		<div>${product.product_istoday}</div>
		

</body>
</html>