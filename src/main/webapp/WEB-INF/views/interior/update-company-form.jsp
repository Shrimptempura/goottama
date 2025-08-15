<%--
  Created by IntelliJ IDEA.
  User: taejun
  Date: 2025-08-08
  Time: 오전 3:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>업체 수정 폼</title>
</head>
<body>
<h3>create-company-form</h3>

<form action="${pageContext.request.contextPath}/interior/update-company" method="post" enctype="multipart/form-data">
  <label>업체명:</label>
  <input type="text" name="companyName" value="${updateDto.companyName}" required><br/>

  <label>주소:</label>
  <input type="text" name="companyAddr" value="${updateDto.companyAddr}" required><br/>

  <label>분야:</label>
  <input type="text" name="companyField" value="${updateDto.companyField}" required><br/>

  <label>면허:</label>
  <input type="text" name="companyLicense" value="${updateDto.companyLicense}" required><br/>

  <label>AS기간:</label>
  <input type="text" name="companyAs" value="${updateDto.companyAs}" required><br/>

  <label>경력:</label>
  <input type="text" name="companyCareer" value="${updateDto.companyCareer}" required><br/>

  <label>소개:</label>
  <input type="text" name="companyIntro" value="${updateDto.companyIntro}" required><br/>

  <label>대표 이미지:</label>
  <input type="file" name="file" accept="image/*"><br/>

  <button type="submit">수정</button>
</form>

</body>
</html>

