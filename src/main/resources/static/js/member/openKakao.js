/*컨트롤러에서 주소 리터받아 새창 열기*/
function sendKakaoInquiry() {
 
  // 컨트롤러에 요청 보내기
  fetch('/kakaoInquiry', {
    method: 'POST',
  })
  .then(response => response.text())  
  .then(kakaoUrl => {
    // 새 창으로 카카오톡 오픈채팅 열기
    window.open(kakaoUrl, '_blank');
  })
  .catch(err => {
    alert('오류가 발생했습니다.');
    console.error(err);
  });
}