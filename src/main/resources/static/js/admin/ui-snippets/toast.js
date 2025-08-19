// /static/js/admin/ui-snippets/toast.js
const urlParams = new URLSearchParams(window.location.search);
const result = urlParams.get('result');
const toast = document.getElementById('toast');

// 메시지와 스타일을 매핑
const toastMap = {
    report_delete_success: { message: '신고 내역 삭제 성공!', type: 'success' },
    report_delete_failure: { message: '신고 내역 삭제 실패!', type: 'failure' },
    change_user_sanctions_until_success: { message: '제재 기간이 성공적으로 변경되었습니다!', type: 'success' },
    change_user_sanctions_until_failure: { message: '제재 기간 변경에 실패했습니다!', type: 'failure' },
    change_user_role_success: { message: '권한 등급이 공적으로 변경되었습니다!', type: 'success' },
    change_user_role_failure: { message: '권한 등급 변경에 실패했습니다!', type: 'failure' },
    create_sanction_success: { message: '신규 재제 부과에 성공했습니다!', type: 'success' },
    create_sanction_failure: { message: '신규 재제 부과에 실패했습니다!', type: 'failure' },
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