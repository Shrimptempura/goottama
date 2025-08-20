<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <title>인테리어 홈</title>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/interior/interior-post.css">--%>
    <link rel="stylesheet" href="<c:url value='/css/interior/interior-ihome.css'/>">
</head>
<body>
<%@ include file="/WEB-INF/views/common/header_navigation_bar.jsp"%>

<div class="home-wrap" style="margin-left: 20%; margin-right: 20%; width: 60%;">

    <!-- 우상단 액션 버튼 -->
    <div class="top-actions">
        <c:choose>
            <c:when test="${not empty myCompanyId}">
                <c:url var="myPageUrl" value="/interior/myhome/${myCompanyId}">
                    <c:param name="type" value="all"/>
                </c:url>
                <a href="${myPageUrl}" class="btn btn-primary">내 업체 페이지</a>
            </c:when>


            <c:otherwise>
                <c:if test="${loginCheck}">
                    <a href="<c:url value='/interior/new-company'/>" class="btn btn-outline">업체 회원가입</a>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>


    <h3>인테리어 업체</h3>
    <div class="company-wrap" style="position:relative; margin:20px 0;">
        <button type="button" class="nav-btn nav-left" onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div id="company-slider" class="company-slider"
             style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">

            <c:forEach var="c" items="${companyList}">
                <c:url var="myhomeUrl" value="/interior/myhome/${c.companyId}">
                    <c:param name="type" value="all"/>
                </c:url>

                <div class="card">
                    <a href="${myhomeUrl}" style="display:block; text-decoration:none; color:inherit; cursor:pointer;">

                        <%-- 썸네일 --%>
                        <c:if test="${not empty c.thumbnailPath}">
                            <c:url value="${c.thumbnailPath}" var="imgUrl"/>
                            <div class="img-box">
                                <img src="${imgUrl}" alt="${c.companyName}">
                            </div>
                        </c:if>

                        <%-- 업체 이름 --%>
                        <div style="margin-top:8px; margin-left:8px; font-weight:bold; font-size:16px;">
                                ${c.companyName}
                        </div>

                        <%-- 별점 + 리뷰 --%>
                        <div style="font-size:12px; color:#666; margin-left:8px;">
                            <c:if test="${not empty c.companyRate}">
                                <span style="color:dodgerblue;">★</span>
                                ${fn:substringBefore((c.companyRate + 0.05), ".")}.${fn:substring(fn:substringAfter((c.companyRate + 0.05), "."),0,1)}
                            </c:if>
                            &nbsp; 리뷰: ${c.reviewCount}
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <br/>
    <br/>
    <br/>

    <h3>최신 리뷰 리스트</h3>
    <div class="company-wrap" style="position:relative; margin:20px 0;">
        <button type="button" class="nav-btn nav-left" onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div class="company-slider" style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">
            <c:forEach var="r" items="${reviewList}">
                <c:url var="reviewUrl" value="/interior/myhome/${r.companyId}">
                    <c:param name="type" value="reviews"/>
                    <c:param name="focus" value="${r.reviewId}"/>
                </c:url>

                <div class="card">
                    <a href="${reviewUrl}" style="display:block; text-decoration:none; color:inherit; cursor:pointer;">
                        <c:if test="${not empty r.thumbnail}">
                            <c:url var="reviewThumbUrl" value="/upload/interior_review/${r.thumbnail.file_name}"/>
                            <div class="img-box">
                                <img src="${reviewThumbUrl}" alt="리뷰 썸네일">
                            </div>
                        </c:if>

                        <div style="margin-top:8px; margin-left:8px; font-weight:700; font-size:14px;">
                                ${r.structureType} · ${r.areaPyeong}평
                        </div>

                        <div style="font-size:12px; color:#555; margin-left:8px; margin-top:4px;
                            display:-webkit-box; -webkit-line-clamp:3; -webkit-box-orient:vertical;
                            overflow:hidden; text-overflow:ellipsis; line-height:1.3;">
                                ${r.reviewContent}
                        </div>

                        <div style="margin-left:8px; margin-top:6px; font-size:12px; color:#666;">
                                ${r.companyId}
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <br/>
    <br/>
    <br/>

    <h3>최신 게시글 리스트</h3>
    <div class="company-wrap" style="position:relative; margin:20px 0;">
        <button type="button" class="nav-btn nav-left" onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div class="company-slider" style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">
            <c:forEach var="p" items="${latestList}">
                <c:url var="postUrl" value="/interior/posts/${p.companyPostId}"/>
                <div class="card">
                    <a href="${postUrl}" style="display:block; text-decoration:none; color:inherit;">
                        <c:if test="${not empty p.thumbnail}">
                            <c:url var="postThumbUrl" value="/upload/interior_post/${p.thumbnail.file_name}"/>
                            <div class="img-box">
                                <img src="${postThumbUrl}" alt="포스트 썸네일">
                            </div>
                        </c:if>

                        <div style="margin-top:8px; margin-left:8px; font-weight:700; font-size:14px;">
                                ${p.companyPostTitle}
                        </div>

                        <div style="font-size:12px; color:#666; margin-left:8px; margin-top:4px;">
                                ${p.areaPyeong}평 · ${p.style}
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <br/>
    <br/>
    <br/>

    <h3>랜덤 게시글 리스트</h3>
    <div class="company-wrap" style="position:relative; margin:20px 0;">
        <button type="button" class="nav-btn nav-left" onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div class="company-slider" style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">
            <c:forEach var="p" items="${randomList}">
                <c:url var="postUrl2" value="/interior/posts/${p.companyPostId}"/>
                <div class="card">
                    <a href="${postUrl2}" style="display:block; text-decoration:none; color:inherit;">
                        <c:if test="${not empty p.thumbnail}">
                            <c:url var="postThumbUrl2" value="/upload/interior_post/${p.thumbnail.file_name}"/>
                            <div class="img-box">
                                <img src="${postThumbUrl2}" alt="포스트 썸네일">
                            </div>
                        </c:if>

                        <div style="margin-top:8px; margin-left:8px; font-weight:700; font-size:14px;">
                                ${p.companyPostTitle}
                        </div>

                        <div style="font-size:12px; color:#666; margin-left:8px; margin-top:4px;">
                                ${p.areaPyeong}평 · ${p.style}
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
    <br/>
    <br/>
    <br/>

    <script>
        function slide(btn, dir) {
            const wrap = btn.closest('.company-wrap');
            const slider = wrap.querySelector('.company-slider');
            const first = slider.querySelector('.card');

            let step = 400;
            if (first) {
                const rect = first.getBoundingClientRect();
                const gap = parseFloat(getComputedStyle(slider).gap || '0');
                step = (rect.width + gap) * 2;
            }
            slider.scrollBy({left: step * dir, behavior: 'smooth'});
        }
    </script>


</div>

<%-- 푸터 --%>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>

</body>
</html>
