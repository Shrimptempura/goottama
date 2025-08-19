<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 내 사진 탭</title>
    <c:url var="cssUrl" value="/css/interior/interior-company-photos.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<h3 class="ph-title">업체 사진</h3>
<br/>

<div class="ph-wrap">
    <c:choose>
        <c:when test="${empty photoList}">
            <div class="ph-empty">등록된 사진이 아직 없습니다.</div>
        </c:when>

        <c:otherwise>
            <div class="ph-grid">
                <c:forEach var="p" items="${photoList}">
                    <c:url value="/upload/interior/${p.file_name}" var="u"/>
                    <div class="ph-card" data-src="${u}" onclick="phOpenLightbox(this)">
                        <div class="ph-thumb">
                            <img src="${u}" loading="lazy" alt="company photo">
                        </div>
                            <%-- 캡션
                            <div class="ph-cap">${p.file_name}</div>
                            --%>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- 라이트박스 -->
<div class="ph-lightbox" id="phLightbox" onclick="phClose(event)">
    <div class="ph-viewer">
        <button class="ph-close" type="button" onclick="phHide()">닫기</button>
        <img id="phLightImg" src="" alt="preview">
    </div>
</div>

<script>
    function phOpenLightbox(card){
        var src = card.getAttribute('data-src');
        var img = document.getElementById('phLightImg');
        var box = document.getElementById('phLightbox');
        img.src = src;
        box.classList.add('open');
        document.body.style.overflow = 'hidden';
    }
    function phHide(){
        var box = document.getElementById('phLightbox');
        var img = document.getElementById('phLightImg');
        box.classList.remove('open');
        img.src = '';
        document.body.style.overflow = '';
    }
    function phClose(e){
        // 배경 클릭 시 닫기 (이미지/버튼 클릭은 무시)
        if(e.target.id === 'phLightbox'){ phHide(); }
    }
</script>

</body>
</html>
