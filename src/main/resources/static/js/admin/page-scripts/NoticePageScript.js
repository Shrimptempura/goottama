// NoticePageScript.js
function initNoticePage() {
    const form = document.getElementById("noticeSearchForm");
    const container = document.getElementById("noticeListContainer");

    // 초기 로드 시점에도 페이지 버튼에 이벤트 바인딩
    bindPageButtons();

    // 검색 폼 제출 (AJAX)
    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const formData = new FormData(form);
        formData.set("page", 1); // 검색 시 항상 1페이지로

        await fetchNoticeList(formData);
    });

    // 공지 목록을 비동기적으로 가져오는 함수
    async function fetchNoticeList(formData) {
        try {
            const response = await fetch("/admin/notices/notice_list", {
                method: "POST",
                body: formData,
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const html = await response.text();
            container.innerHTML = html;
            bindPageButtons(); // 새롭게 로드된 HTML의 페이지 버튼에 이벤트 다시 연결
        } catch (error) {
            console.error("공지 목록을 불러오는 중 오류 발생:", error);
            alert("공지 목록을 불러오는 데 실패했습니다. 잠시 후 다시 시도해주세요."); // TODO: toast로 바꿀 것
        }
    }

    // 페이지 버튼 클릭 핸들러 연결 함수
    function bindPageButtons() {
        // .pagination-controls 내의 모든 <a> 태그에 이벤트를 연결
        const buttons = container.querySelectorAll(".pagination-controls a");
        buttons.forEach((btn) => {
            if (!btn.classList.contains('disabled')) { // 비활성화된 버튼은 클릭 방지
                btn.onclick = async function (e) {
                    e.preventDefault();
                    const page = btn.dataset.page;
                    if (!page) return; // data-page가 없는 경우 방지

                    const formData = new FormData(form);
                    formData.set("page", page); // 숨겨진 페이지 input 값을 직접 업데이트하는 대신 formData에 설정

                    await fetchNoticeList(formData);
                };
            }
        });
    }
}