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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/community/community_home.css">
</head>
<body>
	<div class="container">
		<h1 class="page-title">커뮤니티 홈</h1>

		<!-- Hero -->
		<section class="hero">
			<img src="/static/img/sample/hero.jpg" alt="메인 배너">
			<div class="hero-content">
				<span class="hero-badge">오늘의픽</span>
				<div class="hero-title">광@@@@@@@@@@@@@@@@@@@@@@@@@@@고</div>
				<div class="hero-meta">닉네임 · 리뷰</div>
			</div>
		</section>

		<!-- Quick categories -->
		<div class="quick" aria-label="빠른 메뉴">
			<a href="${pageContext.request.contextPath}/community/suggest_view"
				class="quick-item">
				<div class="quick-icon">★</div>
				<div class="quick-label">추천</div>

			</a> <a
				href="${pageContext.request.contextPath}/community/popularity_view"
				class="quick-item">
				<div class="quick-icon">🔥</div>
				<div class="quick-label">인기</div>

			</a> <a href="${pageContext.request.contextPath}/community/review_view"
				class="quick-item">
				<div class="quick-icon">✍</div>
				<div class="quick-label">리뷰</div>

			</a> <a
				href="${pageContext.request.contextPath}/community/house_photo_view"
				class="quick-item">
				<div class="quick-icon">📷</div>
				<div class="quick-label">집사진</div>

			</a> <a
				href="${pageContext.request.contextPath}/community/house_decoration_view"
				class="quick-item">
				<div class="quick-icon">🧰</div>
				<div class="quick-label">집꾸미기</div>
			</a>
		</div>

		<!-- Section: 추천 + 인기 탭 -->
		<section class="section">
			<div class="section-head">
				<div class="section-tabs">
					<span class="section-tab is-active">오늘의 추천</span> <a
						class="section-tab"
						href="${pageContext.request.contextPath}/community/popularity_view">인기</a>
				</div>
				<a class="section-more"
					href="${pageContext.request.contextPath}/community/suggest_view">더보기</a>
			</div>

			<div class="scroller">
				<article class="card">
					<a
						href="${pageContext.request.contextPath}/community/post?post_id=1">
						<div class="thumb">
							<img src="/static/img/sample/card-1.jpg" alt="">
						</div>
						<div class="body">
							<!-- 텍스트/메타 제거 -->
						</div>
					</a>
				</article>

				<article class="card">
					<a href="#">
						<div class="thumb">
							<img src="/static/img/sample/card-2.jpg" alt="">
						</div>
						<div class="body">
							<!-- 텍스트/메타 제거 -->
						</div>
					</a>
				</article>
			</div>
		</section>

		<!-- Section: 지금 인기 (원하면 유지) -->
		<section class="section">
			<div class="section-head">
				<h2 class="section-title">지금 인기</h2>
				<a class="section-more"
					href="${pageContext.request.contextPath}/community/popularity_view">더보기</a>
			</div>

			<div class="scroller">
				<article class="card">
					<a href="#">
						<div class="thumb">
							<img src="/static/img/sample/card-3.jpg" alt="">
						</div>
						<div class="body">
							<!-- 텍스트/메타 제거 -->
						</div>
					</a>
				</article>
			</div>
		</section>

		<div class="footer-space"></div>
	</div>
</body>
</html>
