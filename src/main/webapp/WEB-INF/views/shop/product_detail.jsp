<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>subheader</h2>
	

		<div class="card-container">
			
				<div class="card">
			        <img src="${product.image}" alt="${product.name}">
			        <div class="card-body">
			          <div class="card-title">${product.name}제목</div>
			          <div class="card-price">${product.price}원</div>
			          
			          
			          
			          <a href="#" class="card-button">장바구니</a>
			        </div>
		     	</div>
			
		</div>
		<div class="main-container">
			<div class="product-list">
				<img src="${product.img }" alt="" />
				<div class="list">
					
					<div class="card-title">${product.mall_name}제목</div>
					<div class="card-title">${product.name}제목</div>
			        <div class="card-price">${product.price}원</div>
				</div>
				
			</div>
		</div>

</body>
</html>