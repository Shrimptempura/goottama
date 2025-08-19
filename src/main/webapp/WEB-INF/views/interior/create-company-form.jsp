<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 등록</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-create-form.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="title">업체 등록</div>
    <div class="card">
        <form action="<c:url value='/interior/new-company'/>" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <div class="form-grid">
                <div class="field">
                    <label>업체명 <span class="note">(필수)</span></label>
                    <input type="text" name="companyName" value="${detail.companyName}" required>
                </div>

                <div class="field">
                    <label>주소 <span class="note">(필수)</span></label>
                    <input type="text" name="companyAddr" value="${detail.companyAddr}" required>
                </div>

                <div class="field">
                    <label>활동 주소 <span class="note">(필수)</span></label>
                    <input type="text" name="locationAddr" value="${location.locationAddr}" required>
                </div>

                <div class="field">
                    <label>분야 <span class="note">(필수)</span></label>
                    <input type="text" name="companyField" value="${detail.companyField}" required>
                </div>

                <div class="field">
                    <label>면허 <span class="note">(필수)</span></label>
                    <input type="text" name="companyLicense" value="${detail.companyLicense}" required>
                </div>

                <div class="field">
                    <label>AS기간 <span class="note">(필수)</span></label>
                    <input type="text" name="companyAs" value="${detail.companyAs}" required>
                </div>

                <div class="field">
                    <label>경력 <span class="note">(필수)</span></label>
                    <input type="text" name="companyCareer" value="${detail.companyCareer}" required>
                </div>

                <div class="field">
                    <label>소개 <span class="note">(필수)</span></label>
                    <textarea name="companyIntro" required>${detail.companyIntro}</textarea>
                </div>

                <div class="field">
                    <label>대표 이미지 <span class="note">(필수, 이미지 파일)</span></label>
                    <input type="file" name="file" accept="image/*" required>
                </div>
            </div>

            <div class="divider"></div>

            <div class="actions">
                <button type="submit" class="btn">등록</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
