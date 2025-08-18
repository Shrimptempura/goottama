		<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <nav class="header-navbar-container">
        <div class="header-navbar-logo">
            <a href="/">ProjectLogo</a>
        </div>
        <ul class="header-navbar-links">
            <li><a href="/interior/ihome" data-page="interior">인테리어</a></li>
            <li><a href="/shop/home" data-page="shopping">쇼핑</a></li>
            <li><a href="/community_home" data-page="community">커뮤니티</a></li>
        </ul>
        <div class="header-navbar-auth">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal != null}">
                        <a href="/mypage/myProfile" data-page="mypage">마이페이지</a>
                        <a href="/logout" data-page="logout">로그아웃</a>
                    </c:when>
                    <c:otherwise>
                        <a href="login_view" data-page="login">로그인</a>
                        <a href="join_view" data-page="join">회원가입</a>
                    </c:otherwise>
                </c:choose>
            </div>
    </nav>
</header>

<style>
    body {
        padding-top: 60px;
    }

    header {
        border-bottom: 1px solid #e0e0e0;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        width: 100%;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 1000;
        background-color: #ffffff;
    }

    .header-navbar-container {
        width: 100%;
        max-width: 60%;
        height: 60px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 20px;
        margin: 0 auto;
    }

    .header-navbar-logo a {
        font-size: 24px;
        font-weight: bold;
        color: #333;
        text-decoration: none;
    }

    .header-navbar-links {
        list-style: none;
        margin: 0 auto 0 0;
        padding: 0;
        display: flex;
        align-items: center;
    }

    .header-navbar-links li {
        margin-left: 20px;
    }

    .header-navbar-links li a {
        color: #555;
        text-decoration: none;
        font-size: 16px;
        padding: 10px 15px;
        transition: color 0.3s ease;
    }

    .header-navbar-auth {
        margin-left: 20px;
    }

    .header-navbar-auth a {
        margin-left: 15px;
        color: #555;
        text-decoration: none;
        font-size: 16px;
    }

    .header-navbar-links li a:hover,
    .header-navbar-auth a:hover,
    .header-navbar-links li a.active,
    .header-navbar-auth a.active {
        color: #007bff;
        font-weight: bold;
    }
</style>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('.header-navbar-links a, .header-navbar-auth a');

        navLinks.forEach(link => {
            const linkPath = link.getAttribute('href').split('/').pop();

            if (currentPath.includes(linkPath) && linkPath !== '') {
                const activeLink = document.querySelector('.active');
                if (activeLink) {
                    activeLink.classList.remove('active');
                }

                link.classList.add('active');
            }
        });
    });
</script>