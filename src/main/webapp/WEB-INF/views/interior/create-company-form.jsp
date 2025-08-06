<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 등록</title>
</head>
<body>
    <h3>create-company-form</h3>

    <form action="${paageContext.request.contextPath}/interior/newCompany" method="post" enctype="multipart/form-data">
        <label>업체명:</label>
        <input type="text" name="companyName" value="${detail.companyName}" required><br/>

        <label>주소:</label>
        <input type="text" name="companyAddr" value="${detail.companyAddr}" required><br/>

        <label>활동 주소:</label>
        <input type="text" name="locationAddr" value="${location.locationAddr}" required><br/>

        <label>분야:</label>
        <input type="text" name="companyField" value="${detail.companyField}" required><br/>

        <label>면허:</label>
        <input type="text" name="companyLicense" value="${detail.companyLicense}" required><br/>

        <label>AS기간:</label>
        <input type="text" name="companyAs" value="${detail.companyAs}" required><br/>

        <label>경력:</label>
        <input type="text" name="companyCareer" value="${detail.companyCareer}" required><br/>

        <label>소개:</label>
        <input type="text" name="companyIntro" value="${detail.companyIntro}" required><br/>

        <label>대표 이미지:</label>
        <input type="file" name="file" accept="imag/*" required><br/>

        <button type="submit">등록</button>
    </form>

</body>
</html>
