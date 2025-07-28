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
	<h4>커뮤니티 홈</h4>

	<!-- 배너 이미지 -->
	<div class="container mt-4">
		<img
			src="${pageContext.request.contextPath}/resources/images/banner.png"
			class="img-fluid rounded" alt="메인 배너">
	</div>

	<!-- 기능 아이콘 -->
	<div class="container mt-5">
		<div class="row text-center icon-section">
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>
				<a href="${pageContext.request.contextPath}/community/suggest_view">추천</a>
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>
				<a
					href="${pageContext.request.contextPath}/community/popularity_view">인기</a>
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>
				<a href="${pageContext.request.contextPath}/community/review_view">리뷰</a>
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>
				<a
					href="${pageContext.request.contextPath}/community/house_photo_view">집사진</a>
			</div>
			<div class="col">
				<img
					src="${pageContext.request.contextPath}/images/community/images.png"><br>
				<a
					href="${pageContext.request.contextPath}/community/house_decoration_view">집꾸미기</a>
			</div>
		</div>
	</div>

	<div class="container mt-4">
		<tr>
			<td colspan="4"><a
				href="${pageContext.request.contextPath}/community/suggest_view">추천</a></td>
		</tr>
	</div>
	<div class="container mt-4">
		<tr>
			<td colspan="4"><a
				href="${pageContext.request.contextPath}/community/popularity_view">인기</a></td>
		</tr>
	</div>

	<div class="container mt-4">
		<tr>
			<td colspan="4"><a
				href="${pageContext.request.contextPath}/community/review_view">리뷰</a></td>
		</tr>
	</div>

	<div class="container mt-4">
		<tr>
			<td colspan="4"><a
				href="${pageContext.request.contextPath}/community/house_photo_view">집사진</a></td>
		</tr>
	</div>

	<div class="container mt-4">
		<tr>
			<td colspan="4"><a
				href="${pageContext.request.contextPath}/community/house_decoration_view">집꾸미기</a></td>
		</tr>
	</div>

</body>
</html>
