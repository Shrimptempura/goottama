<%--
  Created by IntelliJ IDEA.
  User: taejun
  Date: 2025-08-09
  Time: 오후 8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>인테리어 홈</title>
</head>
<body>

<%-- 헤더 --%>
<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div style="margin-left: 20%; margin-right: 20%; width: 60%;">


    <h3>인테리어 업체</h3>
    <div class="company-wrap" style="position:relative; margin:20px 0;">
        <button type="button" class="nav-btn nav-left"  onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div id="company-slider" class="company-slider" style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">

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

                    <%-- 업체 명 --%>
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
        <button type="button" class="nav-btn nav-left"  onclick="slide(this,-1)">&#8249;</button>
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
                            <div class="img-box">
                                <img src="/upload/interior_review/${r.thumbnail.file_name}" alt="리뷰 썸네일">
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
        <button type="button" class="nav-btn nav-left"  onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div class="company-slider" style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">
            <c:forEach var="p" items="${latestList}">
                <c:url var="postUrl" value="/interior/posts/${p.companyPostId}"/>
                <div class="card">
                    <a href="${postUrl}" style="display:block; text-decoration:none; color:inherit;">
                        <c:if test="${not empty p.thumbnail}">
                            <div class="img-box">
                                <img src="/upload/interior_post/${p.thumbnail.file_name}" alt="포스트 썸네일">
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
        <button type="button" class="nav-btn nav-left"  onclick="slide(this,-1)">&#8249;</button>
        <button type="button" class="nav-btn nav-right" onclick="slide(this, 1)">&#8250;</button>

        <div class="company-slider" style="display:flex; overflow-x:hidden; scroll-behavior:smooth; gap:20px;">
            <c:forEach var="p" items="${randomList}">
                <c:url var="postUrl2" value="/interior/posts/${p.companyPostId}"/>
                <div class="card">
                    <a href="${postUrl2}" style="display:block; text-decoration:none; color:inherit;">
                        <c:if test="${not empty p.thumbnail}">
                            <div class="img-box">
                                <img src="/upload/interior_post/${p.thumbnail.file_name}" alt="포스트 썸네일">
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


    <style>
        .nav-btn {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            z-index: 10;

            width: 48px;
            height: 48px;
            border-radius: 50%;
            border: none;
            background-color: #eee;
            color: white;
            font-size: 28px;
            cursor: pointer;
            display: none;
            align-items: center;
            justify-content: center;
        }

        .nav-left {
            left: -20px;
        }

        .nav-right {
            right: -20px;
        }

        .nav-btn:hover {
            background-color: #ddd;
        }

        .company-wrap:hover .nav-btn {
            display: flex;
        }

        .card {
            flex: 0 0 260px;
            border: 1px solid #f3f3f3;
            border-radius: 8px;
            padding: 10px;
            background-color: #f9f9f9;
            text-align: left;
        }

        .img-box {
            width: 100%;
            aspect-ratio: 4/3;
            overflow: hidden;
            border-radius: 6px;
            margin: 0 auto;
        }

        .img-box > img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }
    </style>


    <script>
        function slide(btn, dir) {
            const wrap   = btn.closest('.company-wrap');
            const slider = wrap.querySelector('.company-slider');
            const first  = slider.querySelector('.card');

            let step = 400;
            if (first) {
                const rect = first.getBoundingClientRect();
                const gap  = parseFloat(getComputedStyle(slider).gap || '0');
                step = (rect.width + gap) * 2;
            }
            slider.scrollBy({ left: step * dir, behavior: 'smooth' });
        }
    </script>


</div>

<%-- 푸터 --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
