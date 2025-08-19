<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세 정보</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-details.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<div class="page-wrap">
    <div class="title">업체 상세 정보</div>

    <c:if test="${empty detail}">
        <div class="card">
            <div class="value empty">업체 상세 정보가 없습니다.</div>
        </div>
    </c:if>

    <c:if test="${not empty detail}">
        <div class="card">
            <div class="row">
                <div class="label">주소</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${empty detail.companyAddr}"><span class="empty">정보 없음</span></c:when>
                        <c:otherwise><c:out value="${detail.companyAddr}"/></c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="row">
                <div class="label">분야</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${empty detail.companyField}"><span class="empty">정보 없음</span></c:when>
                        <c:otherwise><c:out value="${detail.companyField}"/></c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="row">
                <div class="label">면허</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${empty detail.companyLicense}"><span class="empty">정보 없음</span></c:when>
                        <c:otherwise><c:out value="${detail.companyLicense}"/></c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="row">
                <div class="label">AS 기간</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${empty detail.companyAs}"><span class="empty">정보 없음</span></c:when>
                        <c:otherwise><c:out value="${detail.companyAs}"/></c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="row">
                <div class="label">경력</div>
                <div class="value">
                    <c:choose>
                        <c:when test="${empty detail.companyCareer}"><span class="empty">정보 없음</span></c:when>
                        <c:otherwise><c:out value="${detail.companyCareer}"/></c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:if>
</div>

</body>
</html>
