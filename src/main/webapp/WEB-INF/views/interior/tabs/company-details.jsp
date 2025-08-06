<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 5:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지의 정보 탭</title>
</head>
<body>
    <h3>company-details</h3>
    <section>
        <p>상세 정보</p>
        <p>주 소: ${detail.companyAddr}</p>
        <p>분 야: ${detail.companyField}</p>
        <p>면 허: ${detail.companyLicense}</p>
        <p>AS기간: ${detail.companyAs}</p>
        <p>경 력: ${detail.companyCareer}</p>
    </section>


</body>
</html>
