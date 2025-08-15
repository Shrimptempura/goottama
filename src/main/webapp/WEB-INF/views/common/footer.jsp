<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<footer>
    <div class="footer-container">
        <div class="footer-section">
            <h4>회사 정보</h4>
            <p>
                서울특별시 마포구 토정로 265 (용강동)
                <br>
                대표자: 김도희
                <br>
                사업자등록번호: 123-45-67890
            </p>
        </div>
        <div class="footer-section">
            <h4>고객 센터</h4>
            <p>
                전화: 02-1234-5678
                <br>
                이메일: Armageddon@Armageddon.com
                <br>
                운영시간: 평일 09:00 ~ 18:00
            </p>
        </div>
        <div class="footer-section">
            <h4>소셜 미디어</h4>
            <ul class="footer-social-links">
                <li><a href="#">유튜브</a></li>
                <li><a href="#">X</a></li>
                <li><a href="#">인스타그램</a></li>
            </ul>
        </div>
    </div>
    <div class="footer-bottom">
        <p>&copy; 2025 Goott Armageddon All Rights Reserved.</p>
    </div>
</footer>
<style>
    footer {
        background-color: #F7F9FA;
        color: #555;
        padding: 40px 20px;
        font-family: Arial, sans-serif;
        border-top: 1px solid #e0e0e0;
    }

    .footer-container {
        display: flex;
        justify-content: space-around;
        flex-wrap: wrap;
        max-width: 1200px;
        margin: 0 auto;
        gap: 30px;
    }

    .footer-section {
        flex: 1;
        min-width: 200px;
    }

    .footer-section h4 {
        font-size: 18px;
        color: #333;
        margin-bottom: 15px;
    }

    .footer-section p {
        font-size: 14px;
        line-height: 1.6;
    }

    .footer-social-links {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .footer-social-links li {
        margin-bottom: 8px;
    }

    .footer-social-links a {
        text-decoration: none;
        color: #555;
        font-size: 14px;
        transition: color 0.3s ease;
    }

    .footer-social-links a:hover {
        color: #007bff;
    }

    .footer-bottom {
        text-align: center;
        margin-top: 30px;
        padding-top: 20px;
        border-top: 1px solid #e0e0e0;
    }

    .footer-bottom p {
        margin: 0;
        font-size: 13px;
        color: #777;
    }
</style>