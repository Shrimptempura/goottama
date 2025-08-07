<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 5:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 내 사진 탭</title>
</head>
<body>
    <h3>company-photos</h3>
    <section>
        <p>dddddddd</p>
        <c:forEach items="${photoList}" var="photo">
            <img src="${pageContext.request.contextPath}/upload/interior/${photo.file_path}" alt="업체 사진">
        </c:forEach>

    </section>


</body>
</html>
