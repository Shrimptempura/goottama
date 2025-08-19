<%-- comment_data_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="comment-details-body">
        <h2>${commentsDto.commentId}번 댓글</h2>

        <h3>댓글 정보</h3>
        <p>작성자 : <a style="text-decoration: none;" href="/admin/users/user_data_detail?user_id=${commentsDto.userId}" target="_blank">${commentsDto.userId}번</a></p>
        <p>댓글 내용: ${commentsDto.commentContent}</p>
        <p>작성 시간: <fmt:formatDate value="${commentsDto.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
        <p>수정 시간: <fmt:formatDate value="${commentsDto.modifiedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
        <p>삭제 여부:
            <c:choose>
                <c:when test="${commentsDto.isDeleted}">삭제됨</c:when>
                <c:otherwise>정상</c:otherwise>
            </c:choose>
        </p>

        <h3>타겟 정보</h3>
        <p>타겟 타입: ${commentsDto.targetType}</p>
        <p>타겟 아이디: ${commentsDto.targetId}</p>

    </div>
</div>