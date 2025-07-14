<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>write_view</h2>

<form action="write" method="post" enctype="multipart/form-data">
<table width="500" border="1">
	<tr>
		<td>상품이름</td>
		<td><input type="text" name="pname" size="50" /></td>	
	</tr>
	<tr>
		<td>상품가격</td>
		<td><input type="text" name="pprice" size="50" /></td>	
	</tr>
	<tr>
		<td>할인율</td>
		<td><input type="text" name="pdiscountrate" size="50" /></td>	
	</tr>
	<tr>
		<td>상품이미지</td>
		<td><input type="text" name="pimg" size="50" /></td>	
	</tr>
	<tr>
		<td>첨부</td>
		<td><input multiple type="file" name="file" size="50" /></td>	
	</tr>
	<tr>
		<td>쇼핑몰이름</td>
		<td><input type="text" name="pmall_name" size="50" /></td>	
	</tr>
	<tr>
		<td>제조국</td>
		<td><input type="text" name="pmadein" size="50" /></td>	
	</tr>
	<tr>
		<td>상품출시일</td>
		<td><input type="text" name="prelease" size="50" /></td>	
	</tr>
	<tr>
		<td>AS책임자전화번호</td>
		<td><input type="text" name="pasmanager_phone" size="50" /></td>	
	</tr>
	<tr>
		<td>상품종류</td>
		<td><input type="text" name="ptype" size="50" /></td>	
	</tr>
	<tr>
		<td>상품색상</td>
		<td><input type="text" name="pcolor" size="50" /></td>	
	</tr>
	<tr>
		<td>당일출고가능여부</td>
		<td><input type="text" name="pistoday" size="50" /></td>	
		
	</tr>

	<tr>
		<td colspan="2"><input type="submit" value="write" /></td>	
	</tr>
</table>
</form>

</body>
</html>