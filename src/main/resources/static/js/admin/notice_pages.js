// 검색 AJAX
const noticeSearchForm = document.getElementById("noticeSearchForm");
if (noticeSearchForm) {
    noticeSearchForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(this);
        const searchParms = new URLSearchParams(formData).toString();

        fetch('/admin/notices/notice_list', {
            method: 'POST',
            body: searchParms,
            headers: {
                'Content-Type' : 'application/x-www-form-urlencoded'
            }
        })
        .then(response => response.text())
        .then(html => {
            document.querySelector('.main-content').innerHTML = html;
        })
        .catch(error => console.log("Error:", error));
    });
}

// 페지네이션 AJAX
const paginationWrapper = document.querySelector('.pagination-wrapper');
if (paginationWrapper) {
    paginationWrapper.addEventListener('click', function(event) {
        if (event.target.tagName === 'A' && event.target.classList.contains('page-btn')) {
            event.preventDefault();

            const newPage = event.target.dataset.page;
            const noticeSearchForm = document.getElementById("noticeSearchForm");
            const formData = new FormData(noticeSearchForm);
            formData.set('page', newPage);

            const searchParms = new  URLSearchParams(formData).toString();

            fetch('/admin/notices/notice_list', {
                method: 'POST',
                body: searchParms,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
            .then(response => response.text())
            .then(html => {
                document.querySelector('.main-content').innerHTML = html;
            })
            .catch(error => console.log('Error: ', error));
        }
    });
}