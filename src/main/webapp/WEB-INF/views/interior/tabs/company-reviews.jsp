<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 상세정보</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-review.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<!-- 상단: 제목 + (비소유자일 때만) 리뷰 작성 버튼 -->
<div class="rr-top">
    <h2 class="rr-title">업체 리뷰</h2>
    <c:if test="${not isOwner}">
        <c:url var="createUrl" value="/interior/myhome/${companyId}/review-form"/>
        <a class="btn" href="${createUrl}">리뷰 작성</a>
    </c:if>
</div>

<!-- 리뷰 카드 리스트 -->
<div class="rr-list">
    <c:forEach var="r" items="${reviews}">
        <article id="review-${r.reviewId}" class="rr-card">
            <!-- 닉네임 + 본인확인 -->
            <div class="rr-head">
                <div class="rr-nick">${r.userNickname}</div>
                <c:if test="${r.author}">
                    <span class="rr-badge">나의 리뷰</span>
                </c:if>
            </div>

            <div class="rr-meta">
                <span class="rr-chip">건물유형: ${r.structureType}</span>
                <span class="rr-chip">평수: ${r.areaPyeong}</span>
                <span class="rr-chip">시공분야: ${r.constructionField}</span>
            </div>

            <!-- 사진(네모) -->
            <c:if test="${not empty r.images}">
                <div class="rr-photos">
                    <c:forEach var="img" items="${r.images}">
                        <div class="rr-photo">
                            <img src="/upload/interior_review/${img.file_name}" alt="review image">
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <div class="rr-content">${r.reviewContent}</div>

            <div class="rr-foot">
                <div class="rr-dates">
                    <span>생성일: ${r.reviewDate}</span>
                    <span>수정일: ${r.reviewModify}</span>
                </div>

                <c:if test="${r.author}">
                    <div class="rr-actions">
                        <c:url var="editUrl" value="/interior/myhome/${companyId}/reviews/${r.reviewId}/edit"/>
                        <a class="btn btn-outline" href="${editUrl}">수정</a>

                        <form action="/interior/myhome/${companyId}/reviews/${r.reviewId}/delete"
                              method="post" style="display:inline-block;"
                              onsubmit="return confirm('정말 삭제할까요?');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit" class="btn btn-danger">삭제</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </article>
    </c:forEach>
</div>

<c:if test="${not empty focus}">
    <script>
        const el = document.getElementById('review-${focus}');
        if (el) el.scrollIntoView({behavior: 'smooth', block: 'start'});
    </script>
</c:if>

</body>
</html>
