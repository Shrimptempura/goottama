<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-13
  Time: 오전 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 수정 폼</title>
</head>
<body>
    <h2>review-edit</h2>
    <form action="/interior/myhome/${companyId}/reviews/${form.reviewId}/edit"
        method="post" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <div>
            <label>내용</label><br>
            <textarea name="reviewContent" value="${form.reviewContent}" rows="10" cols="50" required></textarea>
        </div>

        <div>
            <label>건물유형</label>
            <input type="text" name="structureType" value="${form.structureType}" required><br/>
            <label>평수</label>
            <input type="text" name="areaPyeong" value="${form.areaPyeong}" required><br/>
            <label>시공분야</label>
            <input type="text" name="constructionField" value="${form.constructionField}" required><br/>
        </div>

        <div>
            <label>소통 점수</label>
            <input type="number" name="communicationRate" min="1" max="10" required>
            <label>가격 점수</label>
            <input type="number" name="priceRate" min="1" max="10" required>
            <label>결과 점수</label>
            <input type="number" name="resultRate" min="1" max="10" required>
            <label>일정 점수</label>
            <input type="number" name="scheduleRate" min="1" max="10" required>
        </div>

        <c:if test="${not empty form.images}">
            <div>
                <c:forEach items="${form.images}" var="img">
                    <img src="/upload/interior_review/${img.file_name}" width="100px" height="100px">
                </c:forEach>
            </div>
        </c:if>

        <div>
            <label>사진 첨부:</label>
            <input type="file" name="files" accept="image/*" ><br/>
        </div>

        <button type="submit">수정</button>
        <a href="/interior/myhome/${companyId}?type=reviews&focus=${form.reviewId}">취소</a>
    </form>


</body>
</html>
