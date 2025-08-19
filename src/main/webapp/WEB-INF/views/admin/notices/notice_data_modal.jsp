<%-- user_detail_modal.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-content-wrapper">
    <div class="notice-details-body">
        <h1>${notice.notices_title}</h1>
            <div>
                ${notice.notices_created_at}
            </div>
            <div class="contents-container">
            <hr />
                ${notice.notices_content}
                <c:if test="${not empty notice.attachedFiles}">
                    <fieldset>
                    <legend>첨부파일</legend>
                    <c:forEach var="file" items="${notice.attachedFiles}">
                        <a href="/admin/attachments/download?fileId=${file.file_id}">${file.file_name}</a><br />
                    </c:forEach>
                    </fieldset>
                </c:if>
            <hr />
            </div>
    </div>
    <button onclick="window.open('/admin/notices/notice_detail?notices_id=${notice.notices_id}', '_blank')">새 창에서 자세히 보기</button>
</div>

