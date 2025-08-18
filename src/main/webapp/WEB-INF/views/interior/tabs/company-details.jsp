<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세 정보</title>
    <style>
        /* 레이아웃: 좌20 / 우20 / 본문 55 */
        .page-wrap {
            margin-left: 20%;
            margin-right: 20%;
            width: 55%;
        }

        .title {
            font-size: 24px;
            font-weight: 800;
            margin: 20px 0 12px;
        }

        /* 카드 */
        .card {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            background: #fff;
            padding: 32px;
            min-height: 400px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, .05);
        }

        /* 행(라벨/값) */
        .row {
            display: grid;
            grid-template-columns: 140px 1fr;
            gap: 12px;
            align-items: start;
            padding: 8px 0;
        }

        .row + .row {
            border-top: 1px dashed #eee;
        }

        .label {
            font-size: 13px;
            color: #6b7280;
            font-weight: 600;
        }

        .value {
            font-size: 14px;
            color: #111827;
        }

        .empty {
            color: #9ca3af;
        }

        /* 반응형 */
        @media (max-width: 980px) {
            .page-wrap {
                margin: 0 16px;
                width: auto;
            }

            .row {
                grid-template-columns: 1fr;
                gap: 4px;
            }

            .label {
                color: #374151;
            }
        }
    </style>
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
