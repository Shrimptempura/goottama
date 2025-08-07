<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
피신고자 : ${reported.title} <br/>
피신고자 : ${reported.authorNickname} <br/>
피신고자 가입일 : ${reported.createdAt} <br/>
<br />
<button onclick="window.open('/admin/users/user_data_detail?user_id=${reported.id}', '_blank')">유저 정보 새 창에서 자세히 보기</button>