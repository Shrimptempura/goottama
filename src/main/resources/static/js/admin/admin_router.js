// 관리자 메인 페이지 주소 할당
function loadContent(menuType) {
    const mainContainer = document.querySelector(".main-content");
    let targetUrl = "";

    switch (menuType) {
        case 'dashboard':
            targetUrl = '/admin/admin_dashboard';
            break;

        case 'notices':
            targetUrl = '/admin/notices/notice_page';
            console.log("notice clicked");
            break;

        case 'users':
            targetUrl = '/admin/users/user_manage';
            break;

        case 'reports':
            targetUrl = '/admin/reports/report_page';
            break;

        case 'search':
            targetUrl = '/admin/search/advanced_search';
            break;

        case 'log':
            targetUrl = '/admin/log/log_viewer';
            break;

        case 'statistics':
            targetUrl = '/admin/statistics/statistics';
            break;

        case 'access_control':
            targetUrl = '/admin/access_control/access_control';
            break;

        case 'permission':
            targetUrl = '/admin/permission/permission_setting';
            break;

        default:
            targetUrl = '/admin/admin_dashboard';
            break;
    }
    // AJAX 요청
    fetch(targetUrl).then(Response => {
        if (!Response.ok) {
            throw new Error('Network response was not ok');
        }
        return Response.text();
    }).then(html => {
        mainContainer.innerHTML = html;
        console.log(html);
    }).catch (error => {
        console.error('Error loading content:', error); // TODO : 나중에 toast로 바꾸기
        mainContainer.innerHTML = '<p>컨텐츠 불러오기 실패</p>'; // TODO : 에러페이지 만들기
    });
}

// 페이지 초기 설정
document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const initialMenu = params.get('menu') || 'dashboard';
    loadContent(initialMenu);

    // 네비게이션 버튼 클릭 이벤트리스너
    const navbar = document.querySelector("#admin-navigation-bar");
    console.log(navbar);
    if (navbar) {
        navbar.addEventListener('click', (event) => {
        console.log(event);
            if (event.target.tagName === 'BUTTON') {
                const menuType = event.target.dataset.menu;
                console.log("menuType : " + menuType);
                if (menuType) {
                    const newURL = window.location.origin + window.location.pathname + '?menu=' + menuType;
                    history.pushState({menu : menuType}, '', newURL);
                    loadContent(menuType);
                }
            }
        });
    };
});

// 뒤로가기 및 앞으로가기 처리
window.addEventListener('popstate', (event) => {
    const params = new URLSearchParams(window.location.search);
    const menu = params.get('menu') || 'dashboard';
    loadContent(menu);
});

