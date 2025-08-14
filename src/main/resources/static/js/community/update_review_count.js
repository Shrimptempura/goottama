function updateReviewCounts() {
	fetch("/community/review_live_counts")
		.then(res => res.json())
		.then(data => {
			data.forEach(item => {
				const reviewId = item.review_id;

				const reviewCountEl = document.getElementById("review_count_" + reviewId);
				const likeCountEl = document.getElementById("review_like_count_" + reviewId);

				if (reviewCountEl) reviewCountEl.textContent = item.review_count;
				if (likeCountEl) likeCountEl.textContent = item.review_like_count;
			});
		})
		.catch(err => console.error("❌ fetch 실패:", err));
}

// 페이지 진입 시 단 1회만 갱신
window.addEventListener("pageshow", () => {
	updateReviewCounts();
});
