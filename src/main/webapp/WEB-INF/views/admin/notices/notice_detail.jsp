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
    <hr />
    </div>
    <button type="button" onclick="location.href='./notice_modify_view?notices_id=${notice.notices_id}'">공지 수정</button>
    <button type="button" onclick="location.href='./notice_page'">목록 보기</button>
    <button type="button" onclick="location.href='./notice_detail?notices_id=${notice.notices_id - 1}'">이전 글 보기</button>
    <button type="button" onclick="location.href='./notice_detail?notices_id=${notice.notices_id + 1}'">다음 글 보기</button>
    <button type="button" onclick="if(confirm('정말 삭제하시겠습니까?')) location.href='./notice_delete?notices_id=${notice.notices_id}'">공지 삭제</button>
</body>
</html>