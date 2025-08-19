<%-- post_data_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="post-details-body">
        <h2>${postForAdminDTO.post_id}번 게시글</h2>

        <h3>게시글 정보</h3>
        <p>작성자 : ${postForAdminDTO.user_id}번</p>
        <p>게시글 제목: ${postForAdminDTO.post_title}</p>
        <p>게시글 내용: ${postForAdminDTO.post_content}</p>
        <p>작성 시간:
            <c:if test="${not empty postForAdminDTO.post_date}">
                <fmt:parseDate value="${postForAdminDTO.post_date}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </c:if>
        </p>
        <p>타겟 타입: ${postForAdminDTO.targetType}</p>
        <p>타겟 아이디: ${postForAdminDTO.targetId}</p>

    </div>
</div>