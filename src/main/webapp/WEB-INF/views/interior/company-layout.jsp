<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 메인 화면</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-layout.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="layout">

        <!-- 좌측 요약 상자 -->
        <aside class="sidebar">
            <div class="sidebar-card">
                <jsp:include page="/WEB-INF/views/interior/fragments/company-summary.jsp"/>
            </div>
        </aside>

        <!-- 본문 -->
        <main class="main">
            <div class="main-head">
                <h3 class="title">업체 상세 페이지</h3>

                <c:if test="${isOwner}">
                    <div class="action-right">
                        <a href="${pageContext.request.contextPath}/interior/update-company"
                           class="btn btn-outline">업체 정보 수정</a>

                        <form action="${pageContext.request.contextPath}/interior/company/delete"
                              method="post"
                              onsubmit="return confirm('정말 탈퇴 하시겠습니까?')">
                            <button type="submit" class="btn btn-danger">업체 탈퇴</button>
                        </form>
                    </div>
                </c:if>
            </div>

            <!-- 탭 네비게이션 -->
            <c:set var="activeType" value="${empty param.type ? 'all' : param.type}"/>
            <ul class="tabs">
                <li>
                    <a class="${activeType eq 'all' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=all">모두보기</a>
                </li>
                <li>
                    <a class="${activeType eq 'reviews' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=reviews">리뷰</a>
                </li>
                <li>
                    <a class="${activeType eq 'posts' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=posts">게시글</a>
                </li>
                <li>
                    <a class="${activeType eq 'photos' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=photos">사진</a>
                </li>
                <li>
                    <a class="${activeType eq 'details' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/interior/myhome/${companyId}?type=details">상세정보</a>
                </li>
            </ul>

            <!-- 탭 내용 주입 -->
            <jsp:include page="${tabName}"/>
        </main>

    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
