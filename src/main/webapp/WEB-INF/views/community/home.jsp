<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아마겟돈</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>

	<!-- 배너 이미지 -->
	<div class="container mt-4">
		<img
			src="${pageContext.request.contextPath}/resources/images/banner.png"
			class="img-fluid rounded" alt="메인 배너">
	</div>

	<!-- 기능 아이콘 영역 -->
	<div class="container mt-5">
		<div class="row text-center icon-section">
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>추천
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>인기
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>리뷰
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>집사진
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>집꾸미기
			</div>
		</div>
	</div>

</body>
</html>