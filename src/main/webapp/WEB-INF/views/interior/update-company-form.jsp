<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>업체 수정</title>
  <style>
    .page-wrap { margin-left:20%; margin-right:20%; width:55%; }
    .title { font-size:24px; font-weight:800; margin:20px 0 12px; }
    .card {
      border:1px solid #e5e7eb; border-radius:12px; background:#fff;
      padding:20px; box-shadow: 0 2px 10px rgba(0,0,0,0.03);
    }

    .form-grid { display:grid; grid-template-columns: 1fr; gap:14px; }
    .field { display:flex; flex-direction:column; gap:6px; }
    .field label { font-size:14px; color:#374151; font-weight:600; }
    .field input[type="text"],
    .field textarea,
    .field input[type="file"] {
      border:1px solid #d1d5db; border-radius:8px; padding:10px;
      font-size:14px; background:#fff; outline:none;
    }
    .field textarea { resize:vertical; min-height:120px; }

    .actions { display:flex; justify-content:flex-end; margin-top:12px; }
    .btn {
      display:inline-flex; align-items:center; justify-content:center;
      height:38px; padding:0 16px; border-radius:10px;
      border:1px solid transparent; background:#4f46e5; color:#fff;
      font-size:14px; cursor:pointer; transition:.15s ease;
    }
    .btn:hover { filter:brightness(0.96); }

    .note { font-size:12px; color:#6b7280; }
    .divider { height:1px; background:#eee; margin:18px 0; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
  <div class="title">업체 수정</div>
  <div class="card">
    <form action="${pageContext.request.contextPath}/interior/update-company" method="post" enctype="multipart/form-data">
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
