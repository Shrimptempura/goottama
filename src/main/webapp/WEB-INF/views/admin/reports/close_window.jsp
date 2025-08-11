<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신고 제출 결과</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
        }
        #toast {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 10px 20px;
            color: white;
            border-radius: 5px;
            display: none;
            opacity: 0;
            transition: opacity 0.5s ease-in-out;
            z-index: 1000;
        }
        .success { background-color: #28a745; } /* 녹색 */
        .failure { background-color: #dc3545; } /* 빨간색 */
    </style>
</head>
<body>
    <div id="toast"></div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const result = "<c:out value='${result}'/>";
            const toast = document.getElementById('toast');

            const toastMap = {
                report_success: { message: '신고가 성공적으로 처리되었습니다.', type: 'success' },
                report_failure: { message: '신고 처리에 실패했습니다.', type: 'failure' }
            };

            if (toastMap[result]) {
                const { message, type } = toastMap[result];
                toast.textContent = message;
                toast.className = type;
                toast.style.display = 'block';

                setTimeout(() => {
                    toast.style.opacity = '1';
                }, 50);

                setTimeout(() => {
                    window.close();
                }, 2000);
            }
        });
    </script>
</body>
</html>