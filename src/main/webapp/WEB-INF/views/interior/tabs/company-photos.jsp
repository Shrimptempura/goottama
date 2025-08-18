<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 5:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 내 사진 탭</title>
</head>
<body>
    <h3>company-photos</h3>
    <section>
        <p>사진</p>
        <c:forEach var="p" items="${photoList}">
            <c:url value="/upload/interior/${p.file_name}" var="u"/>
            <img src="${u}" alt="${c.companyName}" style="width: 200px; height: 200px;">
        </c:forEach>

    </section>


</body>
</html>
