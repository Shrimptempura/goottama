<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 상세페이지 내 사진 탭</title>
    <style>
        /* 래퍼 */
        .ph-wrap{ display:flex; flex-direction:column; gap:12px; }
        .ph-title{ font-size:18px; font-weight:800; margin:4px 0 0; }

        /* 비어있을 때 */
        .ph-empty{
            border:1px dashed #e5e7eb; border-radius:12px; padding:24px;
            background:#fafafa; color:#6b7280; text-align:center; font-size:14px;
        }

        /* 그리드 */
        .ph-grid{
            display:grid;
            grid-template-columns:repeat(4, minmax(0, 1fr));
            gap:10px;
        }
        @media (max-width: 1100px){ .ph-grid{ grid-template-columns:repeat(3, 1fr); } }
        @media (max-width: 700px){  .ph-grid{ grid-template-columns:repeat(2, 1fr); } }
        @media (max-width: 460px){  .ph-grid{ grid-template-columns:1fr; } }

        /* 카드 */
        .ph-card{
            border:1px solid #e5e7eb; border-radius:12px; background:#fff;
            padding:8px; box-shadow:0 2px 8px rgba(0,0,0,.03);
            transition: transform .12s ease, box-shadow .12s ease;
            cursor: zoom-in;
        }
        .ph-card:hover{ transform:translateY(-2px); box-shadow:0 6px 16px rgba(0,0,0,.06); }

        .ph-thumb{
            width:100%; aspect-ratio:1/1; border-radius:10px; overflow:hidden;
            background:#f3f4f6;
        }
        .ph-thumb img{
            width:100%; height:100%; object-fit:cover; display:block;
        }

        /* 라이트박스 */
        .ph-lightbox{
            position:fixed; inset:0; display:none; align-items:center; justify-content:center;
            background:rgba(0,0,0,.6); z-index:1000; padding:20px;
        }
        .ph-lightbox.open{ display:flex; }
        .ph-viewer{
            max-width:min(96vw, 1100px); max-height:90vh;
            border-radius:12px; overflow:hidden; background:#000;
            box-shadow:0 10px 30px rgba(0,0,0,.35);
            position:relative;
        }
        .ph-viewer img{ display:block; max-width:100%; max-height:90vh; object-fit:contain; }
        .ph-close{
            position:absolute; top:8px; right:8px;
            background:rgba(255,255,255,.85); border:1px solid #e5e7eb;
            border-radius:8px; height:36px; padding:0 12px; cursor:pointer;
        }
        .ph-close:hover{ background:#fff; }

        /* 작은 캡션 영역(옵션) */
        .ph-cap{
            font-size:12px; color:#6b7280; text-align:center; margin-top:6px;
        }
    </style>
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
