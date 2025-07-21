<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.simplecss.org/simple.min.css">
    <link rel="stylesheet" href="/static/css/admin/toastui-editor.min.css">
    <!--
    <link rel="stylesheet" href="/static/css/admin/plugins/color-picker.min.css">
    <link rel="stylesheet" href="/static/css/admin/plugins/code-syntax-highlight.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.16.11/dist/katex.min.css">
    -->
    <title>공지 작성</title>
</head>
<body>
    <h1>공지 작성</h1>
    <form id="noticeForm" action="/admin/notices/notice_write" method="post">
        <div>
            <label for="title">제목:
            <textarea id="title" name="title" rows="1" required></textarea></label>
        </div>
        <div id="editor"></div>
        <textarea name="content" id="content" style="display: none;"></textarea>
        <br />
        <button type="submit">작성 완료</button>
    </form>

    <script src="/static/js/admin/toastui-editor-all.min.js"></script>
    <script src="/static/js/admin/plugins/color-picker.min.js"></script>

    <!--
    <script src="/static/js/admin/plugins/chart.min.js"></script>
    <script src="/static/js/admin/plugins/code-syntax-highlight.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script> <script defer src="https://cdn.jsdelivr.net/npm/katex@0.16.11/dist/katex.min.js"></script>
    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.16.11/dist/contrib/auto-render.min.js"></script>
    -->

    <script src="/static/js/admin/editor_setup.js"></script>
</body>
</html>