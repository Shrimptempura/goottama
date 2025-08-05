<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<form action="/mypage/withdrawal" method="post">

회원탈퇴 신청 <br />
회원 탈퇴 신청에 앞서 아래 내용을 반드시 확인해주세요. <br />
회원탈퇴 시 처리내용 <br />
☞구매 정보가 삭제됩니다. <br />
☞소비자보호에 관한 법률 제6조에 의거,계약 또는 청약철회 등에 관한 기록은 5년, 대금결제 및 재화등의 공급에 관한 기록은 5년, 소비자의 불만 또는 분쟁처리에 관한 기록은 3년 동안 보관됩니다. 동 개인정보는 법률에 의한 보유 목적 외에 다른  목적으로는 이용되지 않습니다.
<br />
<label>
<input type="checkbox" name="agree" value="yes" required />
위 내용을 모두 확인하였습니다.(필수)
</label>

<p> <strong>탈퇴사유를 선택해 주세요.(필수)</strong> </p>
<label><input type="radio" name="reason" value="1" />이용빈도 낮음</label> <br />
<label><input type="radio" name="reason" value="2" />상품/정보 부족</label> <br />
<label><input type="radio" name="reason" value="3" />혜택 부족</label> <br />
<label><input type="radio" name="reason" value="4" />기타</label> <br />

<input type="submit" value="탈퇴하기" />
<button type="button" onclick="history.back()">취소</button>

</form>


</body>
</html>