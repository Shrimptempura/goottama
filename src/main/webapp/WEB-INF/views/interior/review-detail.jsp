<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 상세정보</title>
</head>
<body>
    <h2>리뷰 정보</h2>

    <div>
        작성자 : ${review.userNickname} <br/>
        <c:if test="${review.author}">내가 작성한 리뷰</c:if>
    </div>

    <div>
        생성일: ${review.reviewDate} <br/>
        수정일: ${review.reviewModify} <br/>
    </div>

    <div>
        <c:forEach var="img" items="${review.images}">
            <img src="/upload/interior_review/${img.file_name}" width="300" height="200">
        </c:forEach>
    </div>

    <div>
        건물 유형: ${review.structureType} <br/>
        평 수: ${review.areaPyeong} <br/>
        시공 분야: ${review.constructionField} <br/>
    </div>

    <div>
        리뷰 내용: ${review.reviewContent}
    </div>

    <hr/>
</body>
</html>
