<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<c:url var="cssUrl" value="/css/interior/interior-company-summary.css"/>
<link rel="stylesheet" href="${cssUrl}">

<section class="cs-card">
    <!-- 회사 이름 -->
    <c:if test="${not empty summary.companyName}">
        <div class="cs-title">${summary.companyName}</div>
    </c:if>

    <!-- 별점 (1 ~ 5별점) -->
    <c:if test="${not empty summary.companyRate}">
        <div class="cs-stars" title="${summary.companyRate}">
            <c:set var="rate" value="${summary.companyRate}" />
            <c:forEach var="i" begin="1" end="5">
                <span class="cs-star ${i <= rate ? 'filled' : ''}"></span>
            </c:forEach>
            <span class="cs-rate-num">
        <fmt:formatNumber value="${summary.companyRate}" maxFractionDigits="1" />
      </span>
        </div>
        <div class="cs-divider"></div>
    </c:if>

    <!-- 소개 -->
    <c:if test="${not empty summary.companyIntro}">
        <div class="cs-intro">${summary.companyIntro}</div>
        <div class="cs-divider"></div>
    </c:if>

    <!-- 상세 정보 (companyId 제외) -->
    <dl class="cs-dl">
        <c:if test="${not empty summary.companyAddr}">
            <dt>주소</dt>
            <dd>${summary.companyAddr}</dd>
        </c:if>

        <c:if test="${not empty summary.companyField}">
            <dt>분야</dt>
            <dd>${summary.companyField}</dd>
        </c:if>

        <c:if test="${not empty summary.companyLicense}">
            <dt>면허</dt>
            <dd>${summary.companyLicense}</dd>
        </c:if>
    </dl>
</section>
