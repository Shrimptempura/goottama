// makeModal.js
(function() {
    const overlay = document.getElementById('ModalOverlay');
    const modalContentArea = document.querySelector('#ModalOverlay .modal .modal-body-content');

    if (!overlay || !modalContentArea) {
        console.warn("generalModal.js: 모달 관련 엘리먼트를 찾을 수 없습니다. 모달 기능이 초기화되지 않습니다.");
        return;
    }

    window.openModal = async function(endpoint, data) {
        overlay.style.display = 'flex';
        document.body.style.overflow = 'hidden';
        modalContentArea.innerHTML = '<p>정보를 불러오는 중...</p>';

        try {
            // URLSearchParams 객체 생성
            const params = new URLSearchParams(data).toString();
            const url = `${endpoint}?${params}`;

            const response = await fetch(url);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const htmlContent = await response.text();
            modalContentArea.innerHTML = htmlContent;
        } catch (error) {
            console.error("모달 내용을 불러오는 중 오류 발생:", error);
            modalContentArea.innerHTML = '<p>오류 발생: 내용을 불러올 수 없습니다.</p>';
        }
    };

    window.closeModal = function() {
        overlay.style.display = 'none';
        document.body.style.overflow = 'auto';
        modalContentArea.innerHTML = '';
    };

    overlay.addEventListener('click', window.closeModal);
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape' && overlay.style.display === 'flex') {
            window.closeModal();
        }
    });

    document.addEventListener('click', function(event) {
        const targetButton = event.target.closest('[data-modal-target]');

        if (targetButton) {
            const modalTarget = targetButton.dataset.modalTarget;
            const paramName = targetButton.dataset.paramName; // 수정된 부분
            const paramValue = targetButton.dataset.paramValue; // 수정된 부분

            if (modalTarget && paramName && paramValue) {
                const data = { [paramName]: paramValue };
                window.openModal(modalTarget, data);
            } else {
                console.error("모달을 열기 위한 필수 데이터 속성(data-modal-target, data-param-name, data-param-value)이 누락되었습니다.");
            }
        }
    });
})();