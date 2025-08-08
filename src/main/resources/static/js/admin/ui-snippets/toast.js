// /static/js/admin/ui-snippets/toast.js
const urlParams = new URLSearchParams(window.location.search);
const result = urlParams.get('result');
const toast = document.getElementById('toast');

// 메시지와 스타일을 매핑
const toastMap = {
    write_success: { message: '글 작성 성공!', type: 'success' },
    write_failure: { message: '글 작성 실패!', type: 'failure' },
    delete_success: { message: '글 삭제 성공!', type: 'success' },
    delete_failure: { message: '글 삭제 실패!', type: 'failure' },
    reply_success: { message: '답변 작성 성공!', type: 'success' },
    reply_failure: { message: '답변 작성 실패!', type: 'failure' },
    modify_success: { message: '글 수정 성공!', type: 'success' },
    modify_failure: { message: '글 수정 실패!', type: 'failure' },
    report_delete_success: { message: '신고 내역 삭제 성공!', type: 'success' },
    report_delete_failure: { message: '신고 내역 삭제 실패!', type: 'failure' },
    error: { message: 'error', type: 'failure' }
};

// 해당 result 값이 toastMap에 존재할 경우만 표시
if (toastMap[result]) {
    const { message, type } = toastMap[result];

    toast.textContent = message;
    toast.className = type;
    toast.style.display = 'block';

    setTimeout(() => {
        toast.style.opacity = '1';
    }, 50);

    setTimeout(() => {
        toast.style.opacity = '0';
        setTimeout(() => {
            toast.style.display = 'none';
            const newUrl = new URL(window.location.href);
            newUrl.searchParams.delete('result');
            history.replaceState(null, '', newUrl.toString());
        }, 500);
    }, 3000);
}