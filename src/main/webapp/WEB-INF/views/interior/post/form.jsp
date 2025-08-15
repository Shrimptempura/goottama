<%--
  Created by IntelliJ IDEA.
  User: taejun
  Date: 2025-08-14
  Time: 오전 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 생성 폼</title>
</head>
<body>
    <h2>post-form</h2>

    <form action="${pageContext.request.contextPath}/interior/myhome/${form.companyId}/posts/new"
        method="post" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="hidden" name="companyId" value="${form.companyId}">

        <div>
            <label for="companyPostTitle">제목</label>
            <input id="companyPostTitle" type="text" name="companyPostTitle" value="${form.companyPostTitle}" required/>
        </div>

        <div>
            <label for="companyPostContent">내용</label>
            <textarea id="companyPostContent" name="companyPostContent" rows="10" cols="90" required>${form.companyPostContent}</textarea>
        </div>

        <div>
            <label for="spaceType">공간 유형</label>
            <input id="spaceType" type="text" name="spaceType" value="${form.spaceType}" required/>

            <label for="areaPyeong">평수</label>
            <input id="areaPyeong" type="text" name="areaPyeong" value="${form.areaPyeong}" required/>

            <label for="style">스타일</label>
            <input id="style" type="text" name="style" value="${form.style}" required/>

            <label for="constructionDetail">시공 상세</label>
            <input id="constructionDetail" type="text" name="constructionDetail" value="${form.constructionDetail}" required/>
        </div>

        <div>
            <label for="files">사진 첨부</label>
            <input id="files" type="file" name="files" accept="image/**"  multiple required/>
        </div>

        <button type="submit">등록</button>
    </form>

</body>
</html>
