<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-06
  Time: 오후 5:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 상세정보</title>
</head>
<body>
<h2>review-detail</h2>
<c:forEach var="r" items="${reviews}">
    <article id="review-${r.reviewId}">
        <div>
            유저 닉네임: ${r.userNickname}
            <c:if test="${r.author}">(내 리뷰)</c:if>
        </div>

        <div>
            건물 유형: ${r.structureType} <br/>
            평 수: ${r.areaPyeong} <br/>
            시공 분야: ${r.constructionField} <br/>
        </div>

        <div>
            <c:forEach var="img" items="${r.images}">
                <img src="/upload/interior_review/${img.file_name}" width="300" height="200">
            </c:forEach>
        </div>

        <div>
            리뷰 내용: ${r.reviewContent}
        </div>
        <hr/>
        <div>
            생성일: ${r.reviewDate} <br/>
            수정일: ${r.reviewModify} <br/>
        </div>
    </article>
</c:forEach>

<c:if test="${not empty focus}">
    <script>
        const el = document.getElementById('review-${focus}');
        if (el) el.scrollIntoView({behavior: 'smooth', block: 'start'});
    </script>

</c:if>

</body>
</html>
