<!-- leftNavigationBar.jsp -->
    <div id="admin-navigation-bar">
        <button data-menu="dashboard">Dash Board</button>
        <button data-menu="users">Manage Users</button>
        <button data-menu="notices">Notices</button>
        <button data-menu="reports">Reports</button>
        <button data-menu="logs">User Log</button>
        <button data-menu="comments">Comments</button>
        <button data-menu="posts">Posts</button>
        <button data-menu="sanctions">Sanctions</button>
        <button data-menu="withdrawals">Withdrawal Reason</button>
    </div>
    <style>
            #admin-navigation-bar {
                display: grid;
                grid-template-columns: repeat(3, 1fr);
                gap: 10px; /* 버튼 사이의 간격 */
                width: 100%;
                max-width: 600px; /* 전체 너비 제한 */
                margin: 0 auto; /* 중앙 정렬 */
                padding: 20px;
                box-sizing: border-box;
            }

            #admin-navigation-bar button {
                width: 100%;
                padding: 15px;
                font-size: 16px;
                border: none;
                border-radius: 8px;
            }
    </style>