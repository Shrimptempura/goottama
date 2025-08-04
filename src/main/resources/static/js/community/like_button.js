// 좋아요 버튼 클릭 시 좋아요수 증가
function likePost(postId) {
	fetch(`${contextPath}/community/like/toggle`, {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: "post_id=" + postId
	})
		.then(response => response.text())
		.then(updatedCount => {
			document.getElementById("likeCount").innerText = updatedCount;
		})
		.catch(err => {
			console.error("좋아요 오류:", err);
			alert("좋아요 처리 중 오류가 발생했습니다.");
		});
}
