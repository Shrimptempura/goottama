function likePost(reviewId) {
	fetch("/community/like/toggle", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: "review_id=" + reviewId
	})
		.then(response => response.text())
		.then(updatedCount => {
			// detail_view.jsp일 경우
			const likeCountStatic = document.getElementById("likeCount");
			if (likeCountStatic) {
				likeCountStatic.textContent = updatedCount;
			}

			// review_view.jsp일 경우
			const likeCountDynamic = document.getElementById("review_like_count_" + reviewId);
			if (likeCountDynamic) {
				likeCountDynamic.textContent = updatedCount;
			}
		})
		.catch(err => {
			console.error("❌ 좋아요 처리 실패:", err);
			alert("좋아요 중 오류가 발생했습니다.");
		});
}
