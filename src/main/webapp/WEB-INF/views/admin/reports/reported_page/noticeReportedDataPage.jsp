<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3>공지 제목 : ${reported.title}</h3>
<span>공지 작성일 : ${reported.createdAt}</span>
<p>
${reported.content}
</p>
<button onclick="window.open('/admin/notices/notice_detail?notices_id=${report.targetId}', '_blank')">공지 새 창에서 자세히 보기</button>