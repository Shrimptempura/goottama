<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
<title>${notice.notices_title}</title>
</head>
<body>
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
    <button type="button" onclick="location.href='./notice_modify_view?notices_id=${notice.notices_id}'">공지 수정</button>
    <button type="button" onclick="location.href='/admin/admin_index?menu=notices'">목록 보기</button>
    <button type="button" onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='./notice_delete?notices_id=${notice.notices_id}'">공지 삭제</button>
</body>
</html>