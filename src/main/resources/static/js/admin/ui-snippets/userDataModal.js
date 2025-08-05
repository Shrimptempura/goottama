//userModalOverlay.js
(function() {
    const overlay = document.getElementById('userModalOverlay');
    const modalContentArea = document.querySelector('#userModalOverlay .modal .modal-body-content');

    const userListContainer = document.getElementById('userListContainer');

    if (overlay && userListContainer) {
        window.openModal = async function(userId) {
            overlay.style.display = 'flex';
            document.body.style.overflow = 'hidden';
            if (modalContentArea) {
                modalContentArea.innerHTML = '<p>사용자 정보를 불러오는 중...</p>';
            }

            try {
                const response = await fetch(`/admin/users/user_data_modal?userId=${userId}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const htmlContent = await response.text();
                if (modalContentArea) {
                    modalContentArea.innerHTML = htmlContent;
                }
            } catch (error) {
                console.error("사용자 상세 모달 내용을 불러오는 중 오류 발생:", error);
                if (modalContentArea) {
                    modalContentArea.innerHTML = '<p>오류 발생: 사용자 상세 정보를 불러올 수 없습니다.</p>';
                }
            }
        };

        window.closeModal = function() {
            overlay.style.display = 'none';
            document.body.style.overflow = 'auto';
            if (modalContentArea) {
                modalContentArea.innerHTML = '';
            }
        };

        overlay.addEventListener('click', window.closeModal);
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && overlay.style.display === 'flex') {
                window.closeModal();
            }
        });

        // --- 이벤트 위임 적용 시작 ---
        userListContainer.addEventListener('click', function(event) {
            const targetButton = event.target.closest('.open-modal-btn');

            if (targetButton) {
                const userId = targetButton.dataset.userId;
                if (userId) {
                    window.openModal(userId);
                }
            }
        });
        // --- 이벤트 위임 적용 끝 ---

    } else {
        console.warn("makeModal.js: Element with ID 'modalOverlay' or 'userListContainer' not found. Modal functionality will not be initialized.");
    }
})();