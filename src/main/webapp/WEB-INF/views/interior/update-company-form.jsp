<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 수정</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-edit-form.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="title">업체 수정</div>
    <div class="card">
        <form action="${pageContext.request.contextPath}/interior/update-company" method="post"
              enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <div class="form-grid">
                <div class="field">
                    <label>업체명 <span class="note">(필수)</span></label>
                    <input type="text" name="companyName" value="${updateDto.companyName}" required>
                </div>

                <div class="field">
                    <label>주소 <span class="note">(필수)</span></label>
                    <input type="text" name="companyAddr" value="${updateDto.companyAddr}" required>
                </div>

                <div class="field">
                    <label>분야 <span class="note">(필수)</span></label>
                    <input type="text" name="companyField" value="${updateDto.companyField}" required>
                </div>

                <div class="field">
                    <label>면허 <span class="note">(필수)</span></label>
                    <input type="text" name="companyLicense" value="${updateDto.companyLicense}" required>
                </div>

                <div class="field">
                    <label>AS기간 <span class="note">(필수)</span></label>
                    <input type="text" name="companyAs" value="${updateDto.companyAs}" required>
                </div>

                <div class="field">
                    <label>경력 <span class="note">(필수)</span></label>
                    <input type="text" name="companyCareer" value="${updateDto.companyCareer}" required>
                </div>

                <div class="field">
                    <label>소개 <span class="note">(필수)</span></label>
                    <textarea name="companyIntro" required>${updateDto.companyIntro}</textarea>
                </div>

                <div class="field">
                    <label>대표 이미지 <span class="note">(선택, 새로 업로드 시 교체됨)</span></label>
                    <input type="file" name="file" accept="image/*">
                </div>
            </div>

            <div class="divider"></div>

            <div class="actions">
                <button type="submit" class="btn">수정</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
