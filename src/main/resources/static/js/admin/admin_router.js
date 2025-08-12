// admin_router.js
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
            break;

        case 'users':
            targetUrl = '/admin/users/user_manage';
            break;

        case 'reports':
            targetUrl = '/admin/reports/report_page';
            break;

        case 'sanctions':
            targetUrl = '/admin/sanctions/sanctions_page';
            break;

        case 'logs':
            targetUrl = '/admin/logs/logs_page';
            break;

        case 'statistics':
            targetUrl = '/admin/statistics/statistics_page';
            break;

        case 'posts':
            targetUrl = '/admin/posts/posts_page';
            break;

        case 'reviews':
            targetUrl = '/admin/reviews/reviews_page';
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
        callSpecificPageJSFunction(menuType);

    }).catch(error => {
        console.error('Error loading content:', error); // TODO : 나중에 toast로 바꾸기
        mainContainer.innerHTML = '<p>컨텐츠 불러오기 실패</p>'; // TODO : 에러페이지 만들기
    });
}

// 페이지 별 JS 파일 실행 함수
async function callSpecificPageJSFunction(menuType) {
    const scriptMap = {
        'notices' : '/static/js/admin/NoticePageScript.js',
        'users' : '/static/js/admin/UserPageScript.js',
        'reports' : '/static/js/admin/ReportPageScript.js',
        'dashboard' : '/static/js/admin/DashboardPageScript.js',
        'sanctions' : '/static/js/admin/SanctionPageScript.js',
        'posts' : '/static/js/admin/PostPageScript.js',
        'reviews' : '/static/js/admin/ReviewPageScript.js',
        'statistics' : '/static/js/admin/StatisticsPageScript.js',
        'logs' : '/static/js/admin/LogsPageScript.js'
    };

    const scriptPath = scriptMap[menuType];

    // 이미 로드 된 파일인지 확인 하고, 로드 되었다면 초기화 함수 재호출
    if (scriptPath) {
        const existingScript = document.querySelector(`script[src="${scriptPath}"]`);
        if (existingScript) {
            callSpecificPageInitFunction(menuType);
            return;
        }
        const script = document.createElement('script');
        script.src = scriptPath;
        script.onload = () => { // 로드 되면 초기화
            callSpecificPageInitFunction(menuType);
        };
        script.onerror = () => {
            console.error(`Error loading JS file : ${scriptPath}`);
        };
        document.head.appendChild(script);
    } else {
        console.warn(`No JS file for ${menuType}`);
    };
}

// 페이지 별 초기화 함수 실행 함수
function callSpecificPageInitFunction(menuType) {
    const initFunctions = {
        'notices': () => typeof initNoticePage === 'function' ? initNoticePage() : console.error("initNoticePage not found"),
        'users': () => typeof initUserPage === 'function' ? initUserPage() : console.error("initUserPage not found"),
        'reports': () => typeof initReportPage === 'function' ? initReportPage() : console.error("initReportPage not found"),
        'reviews': () => typeof initReviewPage === 'function' ? initReviewPage() : console.error("initReviewPage not found"),
        'statistics': () => typeof initStatisticsPage === 'function' ? initStatisticsPage() : console.error("initStatisticsPage not found"),
        'posts': () => typeof initPostPage === 'function' ? initPostPage() : console.error("initPostPage not found"),
        'dashboard': () => typeof initDashboardPage === 'function' ? initDashboardPage() : console.error("initDashboardPage not found"),
        'logs': () => typeof initLogPage === 'function' ? initLogPage() : console.error("initLogPage not found"),
        'sanctions': () => typeof initSanctionPage === 'function' ? initSanctionPage() : console.error("initSanctionPage not found")
    };

    const runInitFunction = initFunctions[menuType];

    if (runInitFunction) {
        runInitFunction();
    }
}

// 페이지 초기 설정
document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const initialMenu = params.get('menu') || 'dashboard';
    loadContent(initialMenu);

    // 네비게이션 버튼 클릭 이벤트리스너
    const navbar = document.querySelector("#admin-navigation-bar");
    if (navbar) {
        navbar.addEventListener('click', (event) => {
            if (event.target.tagName === 'BUTTON') {
                const menuType = event.target.dataset.menu;
                if (menuType) {
                    const newURL = window.location.origin + window.location.pathname + '?menu=' + menuType;
                    history.pushState({ menu: menuType }, '', newURL);
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

