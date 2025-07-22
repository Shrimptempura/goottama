<%-- src/main/webapp/WEB-INF/views/admin/notices/notice_modify_view.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
    <link rel="stylesheet" href="/static/css/admin/toastui-editor.min.css">
    <title>공지 수정</title>
</head>
<body>
    <h1>공지 수정</h1>
    <form id="noticeModifyForm" action="/admin/notices/notice_modify" method="post" enctype="multipart/form-data">
        <input type="hidden" name="notices_id" value="${notice.notices_id}" />

        <div>
            <label for="title">제목:
                <textarea id="title" name="title" rows="1" required>${notice.notices_title}</textarea></label>
        </div>
        <div id="editor"></div>
        <textarea name="content" id="content" style="display: none;"></textarea>
        <br />
        <div><fieldset>
            <label for="isPinned"> 상단 고정 여부
                <input type="checkbox" name="isPinned"
                       <c:if test="${notice.notices_is_pinned}">checked</c:if> /></label>
        </fieldset></div>
        <br />
        <div><fieldset>
            <label for="attachedFiles"> 첨부파일
                <input multiple type="file" name="attachedFiles" size="50" /></label>
            <c:if test="${not empty notice.notices_file_path}">
                <p>현재 파일: <a href="${notice.notices_file_path}" target="_blank">${notice.notices_file_path}</a></p>
            </c:if>
        </fieldset></div>
        <br />
        <button type="submit">수정 완료</button>
        <a href="/admin/notices/notice_detail?noticeId=${notice.notices_id}"><button type="button">취소</button></a>
    </form>

    <script src="/static/js/admin/toastui-editor-all.min.js"></script>

    <script>
        // TUI 에디터 초기화 및 기존 내용 로드
        const editor = new toastui.Editor({
            el: document.querySelector('#editor'),
            height: '500px',
            initialEditType: 'markdown',
            previewStyle: 'vertical',
            // 기존 TUI 에디터 설정 (이미지 업로드 훅 등)
            hooks: {
                async addImageBlobHook(blob, callback){
                    try {
                        const formData = new FormData();
                        formData.append("image", blob);
                        const response = await fetch("/tui-editor/image-upload", {
                            method: 'POST',
                            body: formData,
                        });
                        const imageUrl = await response.text();
                        console.log("웹에서 접근 가능한 이미지 URL : ", imageUrl);
                        callback(imageUrl, "image alt attribute");
                    } catch (error) {
                        console.log("업로드 실패", error);
                    }
                }
            },
            // TUI 에디터에 기존 내용 로드
            initialValue: `${notice.notices_content}` // JSP 변수를 JavaScript 문자열로 삽입
        });

        // '수정 완료' 버튼 클릭 시 TUI 에디터 내용을 hidden textarea에 저장
        document.querySelector('#noticeModifyForm').addEventListener('submit', function(event) {
            const editorContent = editor.getHTML();
            document.querySelector('#content').value = editorContent;
            // console.log("전송될 내용:", document.querySelector('#content').value); // 디버깅용
        });
    </script>
</body>
</html>