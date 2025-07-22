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
		<td>상품카테고리</td>
		<td>
		  <select name="pcategory">
		    <optgroup label="가구">
		      <option value="1">침대</option>
		      <option value="2">소파</option>
		      <option value="3">선반</option>
		      <option value="4">의자</option>
		      <option value="5">거울</option>
		    </optgroup>
		    <optgroup label="가전,디지털">
		      <option value="6">냉장고</option>
		      <option value="7">TV</option>
		      <option value="8">세탁기</option>
		      <option value="9">청소기</option>
		      <option value="10">에어컨</option>
		    </optgroup>
		    <optgroup label="주방용품">
		      <option value="11">그릇</option>
		      <option value="12">냄비</option>
		      <option value="13">컵</option>
		      <option value="14">수저</option>
		      <option value="15">주방수납,정리</option>
		    </optgroup>
		  </select>
		</td>
	</tr>
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
		<td><input multiple type="file" name="file" size="50" onchange="checkFileSize(this.files)"/></td>	
	</tr>
	<script>
	  function checkFileSize(files) {
	    let totalSize = 0;
	    for (let i = 0; i < files.length; i++) {
	      totalSize += files[i].size; // bytes
	    }
	    console.log("총 파일 용량: " + totalSize + " bytes");
	    alert("총 파일 용량: " + (totalSize / (1024 * 1024)).toFixed(2) + " MB");
	  }
	</script>
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
		<td><input type="date" name="prelease" /></td>
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