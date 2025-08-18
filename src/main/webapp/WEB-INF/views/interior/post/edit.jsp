<%--
  Created by IntelliJ IDEA.
  User: taejun
  Date: 2025-08-15
  Time: 오전 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
<h2>post-edit</h2>

<c:if test="${not empty error}">
    <div style="color:red">${error}</div>
</c:if>
<c:if test="${not empty msg}">
    <div style="color:green">${msg}</div>
</c:if>

<form action="<c:url value='/interior/posts/${form.companyPostId}/edit'/>"
      method="post" enctype="multipart/form-data">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="hidden" name="companyPostId" value="${form.companyPostId}"/>

    <div>
        <label>제목</label>
        <input type="text" name="companyPostTitle" value="${form.companyPostTitle}"/>
    </div>

    <div>
        <label>내용</label>
        <textarea name="companyPostContent" rows="10" cols="90" required>${form.companyPostContent}</textarea>
    </div>

    <div>
        업체 정보 <br/>
        <label>공간 타입</label>
        <input type="text" name="spaceType" value="${form.spaceType}"/>

        <label>평수</label>
        <input type="text" name="areaPyeong" value="${form.areaPyeong}"/>

        <label>스타일</label>
        <input type="text" name="style" value="${form.style}"/>

        <label>시공 상세</label>
        <input type="text" name="constructionDetail" value="${form.constructionDetail}"/>
    </div>
    <hr/>

    <c:if test="${not empty images}">
        <div>
            <c:forEach items="${images}" var="img">
                <img src="${img.url}"/>
            </c:forEach>
        </div>
    </c:if>

    <label>이미지 교체</label>
    <input type="file" name="files" multiple>

    <div>
        <button type="submit">수정 완료</button>
        <a href="<c:url value='/interior/posts/${form.companyPostId}'/>">취소</a>
    </div>

</form>







</body>
</html>
