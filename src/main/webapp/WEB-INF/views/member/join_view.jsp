<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="/js/member/emailChoose.js"></script>
<script src="/js/member/postCode.js"></script>
<script src="/js/member/checkDuplicateId.js"></script>
<script src="/js/member/checkDuplicateNickname.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아마겟돈 회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/join_view.css" />
</head>
<body>
<%@ include file="../common/header_navigation_bar.jsp" %>

  <h3>회원가입</h3>

  <form:form modelAttribute="joinformDto" action="/join" method="post">
    
    <label for="loginId">아이디</label>
    <form:input path="loginId" id="loginId"/>
    <form:errors path="loginId" cssClass="error-msg"/>
    <c:if test="${not empty id_error }"><p>${id_error}</p></c:if>
    <button type="button" onclick="checkDuplicateId()">중복확인</button>   

    <label for="pw">비밀번호</label>
    <form:password path="pw" placeholder="8~20자리,영문/숫자/특수문자 포함"/> 
    <form:errors path="pw" cssClass="error-msg"/> 

    <label for="pw2">비밀번호 확인</label>
    <form:password path="pw2" />
    <form:errors path="pw2" cssClass="error-msg"/> 
    <c:if test="${not empty pw_error }"><p>${pw_error}</p></c:if>

    <label for="name">이름</label>
    <form:input path="name"/>
    <form:errors path="name" cssClass="error-msg"/> 

    <label for="nickname">닉네임</label>
    <form:input path="nickname" id="nickname"/>
    <form:errors path="nickname" cssClass="error-msg"/> 
    <c:if test="${not empty nickname_error }"><p>${nickname_error}</p></c:if>
    <button type="button" onclick="checkDuplicateNickname()">중복확인</button>   

    <label for="gender">성별</label>
    <div class="gender-group">
      <form:radiobutton path="gender" value="M"/> 남
      <form:radiobutton path="gender" value="F"/> 여
    </div>
    <form:errors path="gender" cssClass="error-msg"/> 

    <label for="birth">생년월일</label>
    <form:input path="birth" type="date" min="1900-01-01" max="2099-12-31" />
    <form:errors path="birth" cssClass="error-msg" />

    <label for="tel">연락처</label>
    <form:input path="tel" />
    <form:errors path="tel" cssClass="error-msg"/> 

    <label for="zipcode">우편번호</label>
    <form:input path="zipcode" id="zipcode"/>
    <form:errors path="zipcode" cssClass="error-msg"/>
    <button type="button" onclick="execDaumPostcode()">우편번호 찾기</button>    

    <label for="addr">도로명주소</label>
    <form:input path="addr" id="addr"/>

    <label for="detailAddr">상세주소</label>
    <form:input path="detailAddr" id="detailAddr" />
    <form:errors path="addr" cssClass="error-msg"/>

    <label>이메일</label>
    <div class="email-wrapper">
      <form:input path="emailId" id="emailId"/>@
      <form:input path="emailDomain" id="emailDomain"/>
      <select id="domainSelect">
        <option value="">-- 선택하세요 --</option>
        <option value="naver.com">naver.com</option>
        <option value="gmail.com">gmail.com</option>
        <option value="daum.net">daum.net</option>
        <option value="kakao.com">kakao.com</option>
        <option value="직접입력">직접입력</option>
      </select>
    </div>
    <c:if test="${not empty email_error }"><p>${email_error}</p></c:if>

    <form:hidden path="rolesId" value="100"/>

    <input type="submit" value="가입하기" />
  </form:form>

<%@ include file="../common/footer.jsp" %>
</body>
</html>