/**
 * @fileoverview TUI 에디터 초기화 및 이미지 업로드 훅 설정 스크립트.
 * 공지사항 작성/수정 페이지에서 TUI 에디터의 기능을 활성화하고,
 * 에디터 내 이미지 업로드 시 서버와의 통신 로직을 정의함.
 */

// const colorPicker = toastui.Editor.plugin.colorSyntax;
// const chart = toastui.Editor.plugin.chart;
/// const codeSyntaxHighlight = toastui.Editor.plugin.codeSyntaxHighlight;

/**
 * TUI 에디터 인스턴스를 생성하고 초기 설정 적용함.
 * 이미지 업로드 훅을 통해 에디터 내 이미지 처리 방식을 커스터마이징함.
 * @constant {toastui.Editor} editor TUI 에디터 인스턴스.
 */
const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    height: '500px',
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    hooks: { // 이미지 업로드 로직
        async addImageBlobHook(blob, callback){
            try {
                /*
                * 1. 에디터에 업로드한 이미지를 FormData 객체에 저장
                *    (이때, 컨트롤러 uploadEditorImage 메서드의 파라미터인 'image'와 formData에 append 하는 key('image')값은 동일해야 함)
                */
                const formData = new FormData();
                formData.append("image", blob);
                // 2. FileApiController - uploadEditorImage 메서드 호출
                const response = await fetch("/tui-editor/image-upload", {
                    method: 'POST',
                    body: formData,
                });
                // 3. 컨트롤러에서 전달받은 하드웨어에 저장된 파일명
                const imageUrl  = await response.text();
                console.log("웹에서 접근 가능한 이미지 URL : ", imageUrl );
                // 4. addImageBlobHook의 callback 함수를 통해  하드웨어에 저장된 이미지를 에디터에 렌더링
                callback(imageUrl, "image alt attribute");
            } catch (error) {
                console.log("업로드 실패", error);
            }
        }
    }
    // plugins: [colorPicker, chart, [codeSyntaxHighlight, { highlighter: highlight.highlightAuto }]]
});

const form = document.querySelector('#noticeForm');
if (form) {
    form.addEventListener('submit', function(event) {
        const content = editor.getMarkdown();
        document.querySelector('#editorContent').value = content;
    });
}

document.querySelector("#noticeForm").addEventListener('submit', function(event){
    const editorContent = editor.getHTML();
    document.querySelector("#content").value = editorContent;
});

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