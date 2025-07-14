<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
}
.item {
  border: 1px solid black;
  padding: 10px;
}
</style>
<h2>shopping home</h2>

<table width="500" border="1">
	<tr>
		<td>번호</td>
		<td>이름</td>
		<td>가격</td>
		<td>쇼핑몰이름</td>
	</tr>
	<c:forEach items="${list }" var="dto">
	<tr>
		<td>${dto.product_id }</td>
		<td>${dto.product_name }</td>
		<td>${dto.product_price }</td>
		<td>${dto.product_mall_name }</td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="5"><a href="write_view">글작성</a></td>
	</tr>
</table>

<a href="write_view">상품 작성</a>

</body>
</html>