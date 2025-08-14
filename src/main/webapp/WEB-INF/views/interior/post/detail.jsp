<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-14
  Time: 오전 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 상세보기</title>
</head>
<body>
    <h2>post-detail</h2>

    <div>
        작성일: ${detail.post.postDate}
        조회수: ${detail.post.companyPostCount}
    </div>

    <div>
        내용: ${detail.post.companyPostContent}
    </div>

    <div>
        업체 정보 <br/>
        업체 이름: ${detail.company.companyName} <br/>
        소개: ${detail.company.companyIntro} <br/>
        공간 타입: ${detail.post.spaceType} <br/>
        평수: ${detail.post.areaPyeong} <br/>
        스타일: ${detail.post.style} <br/>
        시공상세: ${detail.post.constructionDetail} <br/>
    </div>

    <c:if test="${not empty detail.images}">
        <div>
            <c:forEach items="${detail.images}" var="img">
                <img src="${img.url}">
            </c:forEach>
        </div>
    </c:if>

</body>
</html>
