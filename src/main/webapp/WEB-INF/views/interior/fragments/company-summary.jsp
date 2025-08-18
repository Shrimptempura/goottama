<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<style>
    /* 요약 카드 */
    .cs-card {
        border: 1px solid #e5e7eb;
        border-radius: 12px;
        background: #fff;
        padding: 14px;
        box-shadow: 0 2px 10px rgba(0,0,0,.03);
        font-family: system-ui, -apple-system, Segoe UI, Roboto, Helvetica, Arial, "Apple Color Emoji","Segoe UI Emoji";
    }
    .cs-title {
        font-size: 18px;
        font-weight: 800;
        margin: 2px 0 6px;
        color: #111827;
        line-height: 1.25;
        word-break: keep-all;
    }
    .cs-intro {
        font-size: 13px;
        color: #374151;
        line-height: 1.5;
        margin: 6px 0 10px;
        white-space: pre-line;
    }
    .cs-divider {
        height: 1px;
        background: #f0f0f0;
        margin: 10px 0;
    }
    /* 라벨/값 목록 */
    .cs-dl {
        display: grid;
        grid-template-columns: 64px 1fr;
        gap: 6px 8px;
        font-size: 13px;
        align-items: start;
    }
    .cs-dl dt {
        color: #6b7280;
        font-weight: 600;
        line-height: 1.4;
        word-break: keep-all;
    }
    .cs-dl dd {
        margin: 0;
        color: #111827;
        line-height: 1.45;
        word-break: break-word;
        white-space: pre-line;
    }

    /* 별점 */
    .cs-stars {
        display: inline-flex;
        align-items: center;
        gap: 6px;
        margin: 4px 0 6px;
    }
    .cs-star {
        width: 16px;
        height: 16px;
        display: inline-block;
        background: #e5e7eb;
        mask: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 .587l3.668 7.431 8.2 1.192-5.934 5.787 1.401 8.164L12 18.896l-7.335 3.865 1.401-8.164L.132 9.21l8.2-1.192L12 .587z"/></svg>') no-repeat center / contain;
        -webkit-mask: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 .587l3.668 7.431 8.2 1.192-5.934 5.787 1.401 8.164L12 18.896l-7.335 3.865 1.401-8.164L.132 9.21l8.2-1.192L12 .587z"/></svg>') no-repeat center / contain;
        border-radius: 2px;
    }
    .cs-star.filled { background: #fbbf24; }
    .cs-rate-num {
        font-size: 12px;
        color: #6b7280;
        margin-left: 2px;
    }
</style>

<section class="cs-card">
    <!-- 회사 이름 -->
    <c:if test="${not empty summary.companyName}">
        <div class="cs-title">${summary.companyName}</div>
    </c:if>

    <!-- 별점 (5점 만점 가정) -->
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
