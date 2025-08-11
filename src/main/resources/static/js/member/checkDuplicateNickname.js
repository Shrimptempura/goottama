function checkDuplicateNickname(){
	const nickname = document.getElementById("nickname").value;
	
	if(!nickname){
		alert("닉네임을 입력해주세요.");
		return;
	}
	
	fetch('/checkDuplicateNickname?nickname='+encodeURIComponent(nickname))
	 .then(response => response.json())
	.then(data => {
		if(data.duplicate){
			alert("이미 사용중인 닉네임 입니다.");
		}else{
			alert("사용 가능한 닉네임 입니다.");
		}
	})
	.catch(error => {
		console.error('loginIdDuplicate error: ',error)
		alert("오류가 발생했습니다.");
	});
}