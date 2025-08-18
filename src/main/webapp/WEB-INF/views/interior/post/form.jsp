<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 생성 폼</title>
    <style>
        /* 레이아웃 */
        .page-wrap { margin-left:20%; margin-right:20%; width:55%; }
        .form-card {
            max-width: 1000px;
            margin: 24px auto; padding: 24px;
            border:1px solid #eee; border-radius:12px; background:#fafafa;
            text-align: left;
        }
        .form-title { text-align:left; margin:0 0 8px; font-size:20px; font-weight:700; }

        /* 옵션 행 조절 */
        .options-row {
            display:grid; gap:12px;
            grid-template-columns: 1fr 1fr 1fr 0.5fr;
            align-items:start;
        }

        .field { width:100%; }
        .label { display:block; font-size:14px; color:#555; margin:8px 0 6px; }

        input[type="text"], select {
            width:100%; box-sizing:border-box;
            padding:10px 12px; border:1px solid #ddd; border-radius:8px; background:#fff;
            font-size:15px; font-family: Arial, Helvetica, sans-serif;
        }

        /* 옵션 선택 높이 */
        .multi { height: 160px; }

        /* 에디터 */
        .editor-wrap { border:1px solid #ddd; border-radius:10px; background:#fff; }
        .toolbar {
            display:flex; gap:8px; padding:8px;
            border-bottom:1px solid #eee; background:#f7f7f7;
            justify-content:flex-start;   /* 왼쪽 정렬 */
        }
        .tool-btn {
            border:1px solid #ddd; background:#fff;
            padding:6px 10px; border-radius:8px; cursor:pointer; font-size:13px;
        }
        .tool-btn:hover { background:#f0f0f0; }
        #editor {
            min-height:480px;   /* 내용 높이 */
            padding:12px; outline:none;
            line-height:1.6; font-size:14px;
        }
        #editor img { max-width:100%; height:auto; border-radius:6px; display:block; margin:8px 0; }

        /* 파일 선택 */
        .file-row { display:flex; align-items:center; gap:12px; }

        /* 제출 버튼 */
        .actions { text-align:left; margin-top:18px; }
        .submit-btn {
            width:240px;
            padding:12px 18px; font-size:16px;
            border:none; border-radius:10px; cursor:pointer;
            color:#fff; background:#3b82f6;
        }
        .submit-btn:hover { background:#2563eb; }

        /* 반응형 */
        @media (max-width: 1024px) {
            .page-wrap { width:70%; }
            .options-row { grid-template-columns: 1fr 1fr; }
        }
        @media (max-width: 720px) {
            .page-wrap { width:90%; margin-left:5%; margin-right:5%; }
            .options-row { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>

<%-- 헤더 --%>
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
                        <button type="button" class="tool-btn" onclick="triggerImagePicker()">이미지 추가</button>
                    </div>
                    <div id="editor" contenteditable="true"></div>
                </div>
                <textarea id="companyPostContent" name="companyPostContent" style="display:none;">
                    ${form.companyPostContent}
                </textarea>
            </div>

            <!-- 파일 입력 -->
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
    // 툴바 동작
    function execCmd(cmd) { document.execCommand(cmd, false, null); }
    function triggerImagePicker() { document.getElementById('files').click(); }

    // 이미지 삽입, submit 처리
    (function () {
        const editor = document.getElementById('editor');
        const filesInput = document.getElementById('files');
        const form = document.getElementById('postForm');
        const hiddenTextarea = document.getElementById('companyPostContent');

        filesInput.addEventListener('change', (e) => {
            const files = Array.from(e.target.files || []);
            files.forEach(file => { if (file?.type.startsWith('image/')) insertImagePreview(file); });
        });

        ['dragenter', 'dragover'].forEach(type => {
            editor.addEventListener(type, (e) => { e.preventDefault(); e.stopPropagation(); });
        });
        editor.addEventListener('drop', (e) => {
            e.preventDefault();
            const droppedFiles = Array.from(e.dataTransfer.files || []);
            const imageFiles = droppedFiles.filter(f => f.type?.startsWith('image/'));
            mergeFilesToInput(imageFiles);
            imageFiles.forEach(insertImagePreview);
        });

        editor.addEventListener('paste', (e) => {
            const items = Array.from(e.clipboardData?.items || []);
            const imageItems = items.filter(it => it.type?.startsWith('image/'));
            if (imageItems.length === 0) return;
            e.preventDefault();
            const files = imageItems.map(it => it.getAsFile()).filter(Boolean);
            mergeFilesToInput(files);
            files.forEach(insertImagePreview);
        });

        form.addEventListener('submit', () => {
            const cleanHtml = editor.innerHTML.replace(/<script[\s\S]*?>[\s\S]*?<\/script>/gi, '');
            hiddenTextarea.value = cleanHtml.trim();
        });

        function mergeFilesToInput(newFiles) {
            const dt = new DataTransfer();
            Array.from(filesInput.files || []).forEach(f => dt.items.add(f));
            newFiles.forEach(f => dt.items.add(f));
            filesInput.files = dt.files;
        }

        function insertImagePreview(file) {
            const url = URL.createObjectURL(file);
            insertImageAtCursor(url);
            setTimeout(() => URL.revokeObjectURL(url), 60000);
        }

        function insertImageAtCursor(src) {
            const img = document.createElement('img');
            img.src = src;
            const sel = window.getSelection();
            if (sel && sel.rangeCount > 0) {
                const range = sel.getRangeAt(0);
                range.deleteContents();
                range.insertNode(img);
                range.setStartAfter(img);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            } else {
                editor.appendChild(img);
            }
        }
    })();
</script>

<%-- 푸터 --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
