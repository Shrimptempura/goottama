<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 신정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/member/mypage/withdrawal_view.css" />
</head>
<body>

<div class="withdrawal-container">
  <h2>회원탈퇴 신청</h2>

  <p class="withdraw-info">
    아래 내용을 반드시 확인해주세요. <br /><br />
    <strong>회원탈퇴 시 처리내용</strong><br />
    ☞ 구매 정보가 삭제됩니다.<br />
    ☞ 소비자보호에 관한 법률 제6조에 따라:<br />
    - 계약 또는 청약철회 등에 관한 기록은 5년,<br />
    - 대금결제 및 재화 등의 공급에 관한 기록은 5년,<br />
    - 소비자 불만 또는 분쟁처리에 관한 기록은 3년 동안 보관됩니다.<br />
    (해당 개인정보는 법률에 의한 보유 목적 외에는 사용되지 않습니다.)
  </p>

  <form action="/mypage/withdrawal" method="post" class="withdrawal-form">

    <label class="checkbox-label">
      <input type="checkbox" name="agree" value="yes" required />
      위 내용을 모두 확인하였습니다. (필수)
    </label>

    <div class="reason-section">
      <p><strong>탈퇴 사유를 선택해 주세요. (필수)</strong></p>
      <label><input type="radio" name="reason" value="1" required /> 이용 빈도 낮음</label><br />
      <label><input type="radio" name="reason" value="2" /> 상품/정보 부족</label><br />
      <label><input type="radio" name="reason" value="3" /> 혜택 부족</label><br />
      <label><input type="radio" name="reason" value="4" /> 기타</label>
    </div>

    <div class="btn-group">
      <input type="submit" class="btn btn-submit" value="탈퇴하기" />
      <button type="button" class="btn btn-cancel" onclick="history.back()">취소</button>
    </div>
  </form>
</div>
</body>
</html>