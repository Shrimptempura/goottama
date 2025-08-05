(function() { // 즉시 실행 함수로 스코프 분리
    const overlay = document.getElementById('modalOverlay');

    // overlay가 존재할 때만 함수를 정의하고 이벤트 리스너를 추가
    if (overlay) {
        function openModal() {
            overlay.style.display = 'flex';
            document.body.style.overflow = 'hidden';
        }

        function closeModal() {
            overlay.style.display = 'none';
            document.body.style.overflow = 'auto';
        }

        overlay.addEventListener('click', closeModal);

        // ESC 키로 모달 닫기
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && overlay.style.display === 'flex') {
                closeModal();
            }
        });

        // JSP에서 onclick으로 호출하기 위한 전역 스코프에 함수 노출
        window.openModal = openModal;
        window.closeModal = closeModal;

    } else {
        console.warn("makeModal.js: Element with ID 'modalOverlay' not found. Modal functionality will not be initialized.");
    }
})(); // 즉시 실행