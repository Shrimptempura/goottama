<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 수정</title>
    <c:url var="cssUrl" value="/css/interior/interior-post-form.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="form-card">
        <h3 class="form-title">게시글 수정</h3>

        <!-- 플래시 메시지 -->
        <c:if test="${not empty msg}">
            <div class="alert success">${msg}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <form id="postForm"
              action="${pageContext.request.contextPath}/interior/posts/${form.companyPostId}/edit"
              method="post" enctype="multipart/form-data">

            <!-- CSRF -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <!-- 식별자 -->
            <input type="hidden" name="companyPostId" value="${form.companyPostId}"/>
            <input type="hidden" name="companyId" value="${form.companyId}"/>

            <!-- 제목 -->
            <div class="field">
                <label for="companyPostTitle" class="label">제목</label>
                <input id="companyPostTitle" type="text" name="companyPostTitle"
                       value="${form.companyPostTitle}" required/>
            </div>

            <!-- 옵션 영역 -->
            <div class="options-row">
                <!-- 스타일: 화면은 다중선택, 전송은 문장(String) -->
                <div class="field">
                    <label for="styleOptions" class="label">스타일 (다중 선택 가능)</label>
                    <!-- 실제 바인딩용 hidden -->
                    <input type="hidden" id="style" name="style" value="${form.style}"/>
                    <!-- 화면 표시/선택용 -->
                    <select id="styleOptions" multiple size="6" class="multi">
                        <option>모던</option><option>미니멀</option><option>빈티지</option>
                        <option>클래식</option><option>내추럴</option><option>북유럽풍</option>
                    </select>
                    <div class="hint">저장은 단일 문자열로 됩니다. (예: “모던, 미니멀”)</div>
                </div>

                <!-- 시공 상세 -->
                <div class="field">
                    <label for="constructionDetailOptions" class="label">시공 상세 (다중 선택 가능)</label>
                    <input type="hidden" id="constructionDetail" name="constructionDetail"
                           value="${form.constructionDetail}"/>
                    <select id="constructionDetailOptions" multiple size="6" class="multi">
                        <option>주방 리모델링</option><option>욕실 리모델링</option><option>거실 인테리어</option>
                        <option>발코니 확장</option><option>도배 / 장판</option><option>방 인테리어</option>
                    </select>
                </div>

                <!-- 공간 유형(단일) -->
                <div class="field">
                    <label for="spaceType" class="label">공간 유형</label>
                    <select id="spaceType" name="spaceType" size="1" required>
                        <c:set var="sp" value="${form.spaceType}"/>
                        <option <c:if test="${sp=='아파트'}">selected</c:if>>아파트</option>
                        <option <c:if test="${sp=='주택'}">selected</c:if>>주택</option>
                        <option <c:if test="${sp=='오피스텔'}">selected</c:if>>오피스텔</option>
                        <option <c:if test="${sp=='원룸 / 투룸'}">selected</c:if>>원룸 / 투룸</option>
                        <option <c:if test="${sp=='빌라'}">selected</c:if>>빌라</option>
                        <option <c:if test="${sp=='상가 / 사무실'}">selected</c:if>>상가 / 사무실</option>
                    </select>
                </div>

                <!-- 평수(단일) -->
                <div class="field">
                    <label for="areaPyeong" class="label">평수</label>
                    <select id="areaPyeong" name="areaPyeong" required>
                        <c:set var="ap" value="${form.areaPyeong}"/>
                        <option value="" disabled>선택하세요</option>
                        <option value="10미만"  <c:if test="${ap=='10미만'}">selected</c:if>>10평 미만</option>
                        <option value="10-19"   <c:if test="${ap=='10-19'}">selected</c:if>>10 ~ 19평</option>
                        <option value="20-29"   <c:if test="${ap=='20-29'}">selected</c:if>>20 ~ 29평</option>
                        <option value="30-39"   <c:if test="${ap=='30-39'}">selected</c:if>>30 ~ 39평</option>
                        <option value="40-49"   <c:if test="${ap=='40-49'}">selected</c:if>>40 ~ 49평</option>
                        <option value="50이상"  <c:if test="${ap=='50이상'}">selected</c:if>>50평 이상</option>
                    </select>
                </div>
            </div>

            <!-- 본문 -->
            <div class="field">
                <label class="label">내용</label>
                <div class="editor-wrap">
                    <div class="toolbar">
                        <button type="button" class="tool-btn" onclick="execCmd('bold')"><b>B</b></button>
                        <button type="button" class="tool-btn" onclick="execCmd('italic')"><i>I</i></button>
                        <button type="button" class="tool-btn" onclick="execCmd('insertUnorderedList')">• List</button>
                    </div>
                    <div id="editor" contenteditable="true"></div>
                </div>
                <!-- 서버 전송용 숨김 필드 -->
                <textarea id="companyPostContent" name="companyPostContent" style="display:none;">
                    ${form.companyPostContent}
                </textarea>
            </div>

            <!-- 기존 이미지 프리뷰 -->
            <div class="field">
                <label class="label">현재 등록된 사진</label>
                <div class="thumb-list">
                    <c:choose>
                        <c:when test="${empty images}">
                            <div class="muted">등록된 이미지가 없습니다.</div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="img" items="${images}">
                                <div class="thumb">
                                    <img src="${img.url}" alt="image" style="width:200px;height:200px;"/>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="hint">* 새 파일을 업로드하면 기존 이미지들은 전부 교체됩니다. 업로드하지 않으면 기존 이미지를 유지합니다.</div>
            </div>

            <!-- 새 파일 업로드 (선택) -->
            <div class="field">
                <label class="label">사진 교체(선택)</label>
                <div class="file-row">
                    <input id="files" type="file" name="files" accept="image/*" multiple />
                </div>
            </div>

            <div class="actions">
                <button type="submit" class="submit-btn">수정 완료</button>
                <a class="btn ghost" href="${pageContext.request.contextPath}/interior/posts/${form.companyPostId}">취소</a>
            </div>
        </form>
    </div>
</div>

<script>
    // 단순 리치 텍스트
    function execCmd(cmd){ document.execCommand(cmd, false, null); }

    document.addEventListener('DOMContentLoaded', function () {
        const editor = document.getElementById('editor');
        const formEl = document.getElementById('postForm');
        const hiddenContent = document.getElementById('companyPostContent');

        // 본문 초기화
        editor.innerHTML = (hiddenContent.value || '').trim();

        // 옵션 프리셀렉트(문장 포함 여부 기반)
        const presetByIncludes = (selectEl, sourceText) => {
            const text = (sourceText || '').trim();
            if (!selectEl || !text) return;
            Array.from(selectEl.options).forEach(opt => {
                // 옵션 값이 문장에 포함되어 있으면 선택
                if (text.indexOf(opt.value) !== -1) opt.selected = true;
            });
        };

        presetByIncludes(document.getElementById('styleOptions'), document.getElementById('style').value);
        presetByIncludes(document.getElementById('constructionDetailOptions'), document.getElementById('constructionDetail').value);

        // 붙여넣기: 텍스트만
        editor.addEventListener('paste', (e)=>{
            const t = e.clipboardData?.getData('text/plain');
            if (t != null) { e.preventDefault(); document.execCommand('insertText', false, t); }
        });

        // 제출 시: img/script 제거 + 다중선택을 단일 문자열로 합쳐 hidden에 세팅
        formEl.addEventListener('submit', ()=>{
            // 본문 정리
            const doc = editor.cloneNode(true);
            doc.querySelectorAll('script').forEach(s => s.remove());
            doc.querySelectorAll('img').forEach(img => img.remove());
            hiddenContent.value = doc.innerHTML.replace(/<script[\s\S]*?>[\s\S]*?<\/script>/gi, '').trim();

            // 다중선택 -> 문장화
            const joinSelected = (sel) => Array.from(sel.selectedOptions).map(o=>o.value).join(', ');
            const styleJoined = joinSelected(document.getElementById('styleOptions'));
            const consJoined  = joinSelected(document.getElementById('constructionDetailOptions'));

            // 선택 없으면 기존 값 유지, 선택 있으면 덮어씀
            if (styleJoined) document.getElementById('style').value = styleJoined;
            if (consJoined)  document.getElementById('constructionDetail').value = consJoined;
        });
    });
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
