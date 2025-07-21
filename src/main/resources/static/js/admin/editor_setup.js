// const colorPicker = toastui.Editor.plugin.colorSyntax;
// const chart = toastui.Editor.plugin.chart;
/// const codeSyntaxHighlight = toastui.Editor.plugin.codeSyntaxHighlight;

const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    // plugins: [colorPicker, chart, [codeSyntaxHighlight, { highlighter: highlight.highlightAuto }]]
});

const form = document.querySelector('#noticeForm');
if (form) {
    form.addEventListener('submit', function(event) {
        const content = editor.getMarkdown();
        document.querySelector('#editorContent').value = content;
    });
}

// KaTeX 렌더링을 위한 설정 (auto-render 플러그인 사용)
// 에디터 내용이 변경될 때마다 수학 공식을 다시 렌더링합니다.
//editor.on('change', () => {
//    const previewElement = document.querySelector('.tui-editor-contents');
//    if (previewElement) {
//        renderMathInElement(previewElement, {
//            delimiters: [
//                {left: '$$', right: '$$', display: true},
//                {left: '$', right: '$', display: false},
//            ]
//        });
//    }
//});