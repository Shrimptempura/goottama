<!-- admin_index.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/static/css/admin/simple.min.css">
<link rel="stylesheet" href="/static/css/admin/adminCSS.css">
<link rel="stylesheet" href="/static/css/admin/ui-snippets/modal.css">
<link rel="stylesheet" href="/static/css/admin/ui-snippets/toast.css">
<title>Admin Dashboard</title>
</head>
<body>
    <h1>Admin Dashboard</h1>
    <div id="toast"></div>
    <div class="leftNavigationBar">
        <jsp:include page="${leftNavigationBar}" />
        <hr />
    </div>
    <div class="main-content">
        <!-- AJAX로 컨텐츠가 로드될 영역 -->
    </div>

    <!-- 모달 -->
    <div id="ModalOverlay" class="modal-overlay">
        <div class="modal" onclick="event.stopPropagation()">
            <button class="modal-close" onclick="closeModal()">&times;</button>
            <div class="modal-body-content"></div>
        </div>
    </div>
</body>
<script src="/static/js/admin/admin_router.js"></script>
<script src="/static/js/admin/ui-snippets/makeModal.js"></script>
<script src="/static/js/admin/ui-snippets/toast.js"></script>
</html>