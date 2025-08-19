<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 생성 폼</title>
    <c:url var="cssUrl" value="/css/interior/interior-post-form.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="form-card">
        <h3 class="form-title">게시글 작성</h3>

        <form id="postForm"
              action="${pageContext.request.contextPath}/interior/myhome/${form.companyId}/posts/new"
              method="post" enctype="multipart/form-data">

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="companyId" value="${form.companyId}">

            <!-- 제목 -->
            <div class="field">
                <label for="companyPostTitle" class="label">제목</label>
                <input id="companyPostTitle" type="text" name="companyPostTitle"
                       value="${form.companyPostTitle}" required/>
            </div>

            <!-- 선택 옵션 -->
            <div class="options-row">
                <div class="field">
                    <label for="style" class="label">스타일 (다중 선택 가능)</label>
                    <select id="style" name="style" multiple size="6" class="multi" required>
                        <option>모던</option><option>미니멀</option><option>빈티지</option>
                        <option>클래식</option><option>내추럴</option>
                        <option>북유럽풍</option>
                    </select>
                </div>

                <div class="field">
                    <label for="constructionDetail" class="label">시공 상세 (다중 선택 가능)</label>
                    <select id="constructionDetail" name="constructionDetail" multiple size="6" class="multi" required>
                        <option>주방 리모델링</option><option>욕실 리모델링</option><option>거실 인테리어</option>
                        <option>발코니 확장</option><option>도배 / 장판</option>
                        <option>방 인테리어</option>
                    </select>
                </div>

                <div class="field">
                    <label for="spaceType" class="label">공간 유형</label>
                    <select id="spaceType" name="spaceType" size="1" required>
                        <option>아파트</option><option>주택</option><option>오피스텔</option>
                        <option>원룸 / 투룸</option><option>빌라</option>
                        <option>상가 / 사무실</option>
                    </select>
                </div>

                <div class="field">
                    <label for="areaPyeong" class="label">평수</label>
                    <select id="areaPyeong" name="areaPyeong" required>
                        <option value="" disabled selected>선택하세요</option>
                        <option value="10미만">10평 미만</option>
                        <option value="10-19">10 ~ 19평</option>
                        <option value="20-29">20 ~ 29평</option>
                        <option value="30-39">30 ~ 39평</option>
                        <option value="40-49">40 ~ 49평</option>
                        <option value="50이상">50평 이상</option>
                    </select>
                </div>
            </div>

            <!-- 본문 에디터 -->
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

                <textarea id="companyPostContent" name="companyPostContent" style="display:none;">
                    ${form.companyPostContent}
                </textarea>
            </div>

            <!-- 파일 -->
            <div class="field">
                <label class="label">사진 첨부</label>
                <div class="file-row">
                    <input id="files" type="file" name="files" accept="image/*" multiple required/>
                </div>
            </div>

            <div class="actions">
                <button type="submit" class="submit-btn">등록</button>
            </div>
        </form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        // 글자 꾸미기만 유지
        window.execCmd = function(cmd){ document.execCommand(cmd, false, null); };

        const editor = document.getElementById('editor');
        const form = document.getElementById('postForm');
        const hiddenTextarea = document.getElementById('companyPostContent');

        if (!editor || !form || !hiddenTextarea) return;

        // 드래그 파일 차단
        ['dragenter','dragover','drop'].forEach(eName=>{
            editor.addEventListener(eName, (e)=>{
                e.preventDefault();
                e.stopPropagation();
            });
        });

        // 붙여넣기는 텍스트만 허용
        editor.addEventListener('paste', (e)=>{
            const text = e.clipboardData?.getData('text/plain');
            if (text != null) {
                e.preventDefault();
                document.execCommand('insertText', false, text);
            }
        });

        // <img> 차단
        editor.addEventListener('beforeinput', (e)=>{
            if (e.inputType === 'insertFromDrop' || e.inputType === 'insertFromPaste') {

            }
        });

        // img 이중차단
        form.addEventListener('submit', ()=>{
            const doc = editor.cloneNode(true);
            doc.querySelectorAll('img').forEach(img => img.remove());

            // 스크립트 제거 + 본문 전송
            const cleanHtml = doc.innerHTML.replace(/<script[\s\S]*?>[\s\S]*?<\/script>/gi, '');
            hiddenTextarea.value = cleanHtml.trim();
        });
    });
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
