<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 상세정보</title>
    <style>
        /* 상단 타이틀 + 버튼 */
        .rr-top {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 12px;
            margin: 4px 0 12px;
        }
        .rr-title {
            margin: 0;
            font-size: 20px;
            font-weight: 800;
            color: #111827;
        }

        /* 카드 리스트 래퍼 */
        .rr-list { display: grid; gap: 16px; }

        /* 카드 */
        .rr-card {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            background: #fff;
            padding: 14px;
            box-shadow: 0 2px 10px rgba(0,0,0,.03);
        }

        /* 상단: 닉네임 + 배지 */
        .rr-head {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 8px;
        }
        .rr-nick { font-weight: 700; color: #111827; }
        .rr-badge {
            font-size: 12px;
            padding: 2px 8px;
            border-radius: 999px;
            background: #eef2ff;
            color: #4338ca;
            border: 1px solid #c7d2fe;
        }

        /* 메타(건물유형/평수/시공분야) */
        .rr-meta {
            display: flex;
            flex-wrap: wrap;
            gap: 10px 12px;
            font-size: 13px;
            color: #374151;
            margin-bottom: 10px;
        }
        .rr-chip {
            background: #f9fafb;
            border: 1px solid #e5e7eb;
            border-radius: 999px;
            padding: 4px 10px;
        }

        /* 이미지 그리드: 정사각형, 둥근 모서리 */
        .rr-photos {
            display: grid;
            grid-template-columns: repeat(3, minmax(0, 1fr));
            gap: 8px;
            margin: 8px 0 10px;
        }
        .rr-photo {
            position: relative;
            width: 100%;
            aspect-ratio: 1 / 1;
            border-radius: 12px;
            overflow: hidden;
            background: #f3f4f6;
            border: 1px solid #e5e7eb;
        }
        .rr-photo img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }

        /* 본문 */
        .rr-content {
            white-space: pre-line;
            line-height: 1.6;
            font-size: 14px;
            color: #111827;
            margin: 6px 0 10px;
        }

        /* 하단 정보 + 버튼 */
        .rr-foot {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 8px;
            font-size: 12px;
            color: #6b7280;
        }
        .rr-dates { display: flex; gap: 10px; flex-wrap: wrap; }

        /* 버튼 */
        .rr-actions { display: flex; gap: 6px; }
        .btn {
            display: inline-flex; align-items: center; justify-content: center;
            height: 32px; padding: 0 12px; border-radius: 8px;
            font-size: 13px; border: 1px solid transparent;
            background: #4f46e5; color: #fff; text-decoration: none; cursor: pointer;
            transition: .15s ease;
        }
        .btn:hover { filter: brightness(.96); }
        .btn-outline { background: #fff; color: #374151; border-color: #d1d5db; }
        .btn-outline:hover { background: #f9fafb; }
        .btn-danger { background: #ef4444; }
        .btn-danger:hover { filter: brightness(.95); }

        /* 반응형 */
        @media (max-width: 900px) {
            .rr-photos { grid-template-columns: repeat(2, minmax(0, 1fr)); }
        }
        @media (max-width: 520px) {
            .rr-photos { grid-template-columns: 1fr; }
            .rr-foot { flex-direction: column; align-items: flex-start; gap: 8px; }
        }
    </style>
</head>
<body>

<!-- 상단: 제목 + (비소유자일 때만) 리뷰 작성 버튼 -->
<div class="rr-top">
    <h2 class="rr-title">review-detail</h2>
    <c:if test="${not isOwner}">
        <c:url var="createUrl" value="/interior/myhome/${companyId}/review-form"/>
        <a class="btn" href="${createUrl}">리뷰 작성</a>
    </c:if>
</div>

<!-- 리뷰 카드 리스트 -->
<div class="rr-list">
    <c:forEach var="r" items="${reviews}">
        <article id="review-${r.reviewId}" class="rr-card">
            <!-- 1) 닉네임 + 배지 -->
            <div class="rr-head">
                <div class="rr-nick">${r.userNickname}</div>
                <c:if test="${r.author}">
                    <span class="rr-badge">나의 리뷰</span>
                </c:if>
            </div>

            <!-- 2) 건물유형 / 평수 / 시공분야 -->
            <div class="rr-meta">
                <span class="rr-chip">건물유형: ${r.structureType}</span>
                <span class="rr-chip">평수: ${r.areaPyeong}</span>
                <span class="rr-chip">시공분야: ${r.constructionField}</span>
            </div>

            <!-- 3) 사진 -->
            <c:if test="${not empty r.images}">
                <div class="rr-photos">
                    <c:forEach var="img" items="${r.images}">
                        <div class="rr-photo">
                            <img src="/upload/interior_review/${img.file_name}" alt="review image">
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- 4) 리뷰 내용 -->
            <div class="rr-content">${r.reviewContent}</div>

            <!-- 5) 생성/수정일 + 수정/삭제 -->
            <div class="rr-foot">
                <div class="rr-dates">
                    <span>생성일: ${r.reviewDate}</span>
                    <span>수정일: ${r.reviewModify}</span>
                </div>

                <c:if test="${r.author}">
                    <div class="rr-actions">
                        <c:url var="editUrl" value="/interior/myhome/${companyId}/reviews/${r.reviewId}/edit"/>
                        <a class="btn btn-outline" href="${editUrl}">수정</a>

                        <form action="/interior/myhome/${companyId}/reviews/${r.reviewId}/delete"
                              method="post" style="display:inline-block;"
                              onsubmit="return confirm('정말 삭제할까요?');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit" class="btn btn-danger">삭제</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </article>
    </c:forEach>
</div>

<!-- (하단의 '리뷰 작성' 버튼 블록은 제거됨) -->

<c:if test="${not empty focus}">
    <script>
        const el = document.getElementById('review-${focus}');
        if (el) el.scrollIntoView({behavior: 'smooth', block: 'start'});
    </script>
</c:if>

</body>
</html>
