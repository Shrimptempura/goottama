<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 메인 화면</title>
    <style>
        /* 페이지 여백 (좌우 20% 느낌) */
        .page-wrap {
            margin-left: 20%;
            margin-right: 20%;
            width: 60%;
        }

        /* 전체 2열 레이아웃 */
        .layout {
            display: grid;
            grid-template-columns: 260px 1fr; /* 사이드바 고정 + 메인 가변 */
            gap: 20px;
        }

        /* 사이드바: 헤더 아래에 고정되는 sticky */
        .sidebar {
            position: sticky;
            top: 96px; /* 헤더 높이에 맞춰 조절 */
            align-self: start;
            height: fit-content;
        }
        .sidebar-card {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            background: #f6fdff;
            padding: 16px;
            box-shadow: 0 2px 10px rgba(0,0,0,.03);
        }

        /* 메인 */
        .main {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            background: #f8fbff;
            padding: 18px 18px 24px;
            box-shadow: 0 2px 10px rgba(0,0,0,.03);
            min-height: 800px;
        }

        /* 탭 */
        .tabs {
            display: flex;
            gap: 14px;
            list-style: none;
            padding: 0;
            margin: 0 0 8px;
            border-bottom: 1px solid #eee;
        }
        .tabs a {
            display: inline-flex;
            align-items: center;
            height: 40px;
            padding: 0 8px;
            text-decoration: none;
            color: #374151;
            font-size: 14px;
            border-bottom: 2px solid transparent;
        }
        .tabs a:hover { color: #111827; }
        .tabs a.active {
            color: #111827;
            border-bottom-color: #4f46e5;
            font-weight: 700;
        }

        /* 상단 액션 (우측) */
        .main-head {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 12px;
            margin-bottom: 8px;
        }
        .title {
            font-size: 20px;
            font-weight: 800;
            margin: 0;
        }

        /* 버튼 */
        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            height: 36px;
            padding: 0 14px;
            border-radius: 8px;
            font-size: 14px;
            border: 1px solid transparent;
            background: #4f46e5;
            color: #fff;
            text-decoration: none;
            cursor: pointer;
            transition: .15s ease;
        }
        .btn:hover { filter: brightness(.96); }

        .btn-danger {
            background: #ef4444;
        }
        .btn-danger:hover { filter: brightness(.95); }

        /* 반응형 */
        @media (max-width: 1200px){
            .page-wrap { margin: 0 24px; width: auto; }
        }
        @media (max-width: 980px){
            .layout { grid-template-columns: 1fr; }
            .sidebar { position: static; }
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="layout">

        <!-- 좌측 요약 상자 -->
        <aside class="sidebar">
            <div class="sidebar-card">
                <jsp:include page="/WEB-INF/views/interior/fragments/company-summary.jsp" />
            </div>
        </aside>

        <!-- 본문 -->
        <main class="main">
            <div class="main-head">
                <h3 class="title">업체 상세 페이지</h3>
                <c:if test="${isOwner}">
                    <form action="${pageContext.request.contextPath}/interior/company/delete"
                          method="post"
                          onsubmit="return confirm('정말 탈퇴 하시겠습니까?')">
                        <button type="submit" class="btn btn-danger">업체 탈퇴</button>
                    </form>
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
