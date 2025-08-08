function checkDuplicateId(){
	const loginId = document.getElementById("loginId").value;
	
	if(!loginId){
		alert("아이디를 입력해주세요.");
		return;
	}
	
	fetch('/checkDuplicateId?loginId='+encodeURIComponent(loginId))
	 .then(response => response.json())
	.then(data => {
		if(data.duplicate){
			alert("이미 사용중인 아이디 입니다.");
		}else{
			alert("사용 가능한 아이디 입니다.");
		}
	})
	.catch(error => {
		console.error('loginIdDuplicate error: ',error)
		alert("오류가 발생했습니다.");
	});
}