<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 리뷰 폼</title>
    <style>
        /* 레이아웃: 좌우 20%, 본문 55% */
        .page-wrap { margin-left:20%; margin-right:20%; width:55%; }
        .title { font-size:24px; font-weight:800; margin:20px 0 12px; }
        .card {
            border:1px solid #e5e7eb; border-radius:12px; background:#fff;
            padding:20px; box-shadow:0 2px 10px rgba(0,0,0,.03);
        }
        .divider { height:1px; background:#eee; margin:18px 0; }

        /* 폼 */
        .form-grid { display:grid; grid-template-columns:1fr; gap:14px; }
        .field { display:flex; flex-direction:column; gap:6px; }
        .field label { font-size:14px; color:#374151; font-weight:600; }
        .field input[type="text"],
        .field textarea,
        .field input[type="file"],
        .field select{
            border:1px solid #d1d5db; border-radius:8px; padding:10px;
            font-size:14px; background:#fff; outline:none;
        }
        .field textarea{ resize:vertical; min-height:140px; }

        /* 별점 묶음 */
        .rate-grid{
            display:grid; grid-template-columns: repeat(2, minmax(0,1fr)); gap:12px;
        }
        .rate-field{ display:flex; flex-direction:column; gap:6px; }
        .rate-label { font-size:14px; font-weight:600; color:#374151; }

        /* Star rating (5점) - 클릭 전용 */
        .stars {
            position: relative;
            display: inline-block;
            font-size: 28px; /* 별 크기 */
            line-height: 1;
            cursor: pointer;
            user-select: none;
        }
        .stars .star {
            display: inline-block;
            width: 28px;
            height: 28px;
            margin-right: 4px;
            mask: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 .587l3.668 7.431 8.2 1.192-5.934 5.787 1.401 8.164L12 18.896l-7.335 3.865 1.401-8.164L.132 9.21l8.2-1.192L12 .587z"/></svg>') no-repeat center / contain;
            -webkit-mask: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 .587l3.668 7.431 8.2 1.192-5.934 5.787 1.401 8.164L12 18.896l-7.335 3.865 1.401-8.164L.132 9.21l8.2-1.192L12 .587z"/></svg>') no-repeat center / contain;
            background: #e5e7eb; /* 빈 별 */
            transition: background .12s ease;
        }
        .stars .star.filled { background: #fbbf24; } /* 채운 별 */
        .stars .star:hover ~ .star { background: #e5e7eb !important; } /* 호버 미리보기 */
        .stars:focus { outline: 2px solid #c7d2fe; outline-offset: 2px; border-radius:6px; }
        .rate-note { font-size:12px; color:#6b7280; margin-left:8px; }

        /* 버튼 */
        .actions{ display:flex; justify-content:flex-end; gap:8px; margin-top:12px; }
        .btn{
            display:inline-flex; align-items:center; justify-content:center;
            height:38px; padding:0 16px; border-radius:10px;
            border:1px solid transparent; background:#4f46e5; color:#fff;
            font-size:14px; cursor:pointer; transition:.15s ease;
            text-decoration:none;
        }
        .btn:hover{ filter:brightness(.96); }

        /* 작은 안내 */
        .note{ font-size:12px; color:#6b7280; }

        /* 반응형 */
        @media (max-width: 980px){
            .page-wrap{ margin:0 16px; width:auto; }
            .rate-grid{ grid-template-columns:1fr; }
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="title">업체 리뷰 작성</div>

    <div class="card">
        <form action="/interior/myhome/${form.companyId}/review-form" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="companyId" value="${form.companyId}">

            <!-- ⭐ 별점(5점) 섹션 : 맨 위 -->
            <div class="rate-grid">
                <!-- 소통 점수 -->
                <div class="rate-field">
                    <label class="rate-label">소통 점수 (1~5)</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="communicationRate"
                         data-initial="${form.communicationRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="communicationRate" value="${form.communicationRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>

                <!-- 가격 점수 -->
                <div class="rate-field">
                    <label class="rate-label">가격 점수 (1~5)</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="priceRate"
                         data-initial="${form.priceRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="priceRate" value="${form.priceRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>

                <!-- 결과 점수 -->
                <div class="rate-field">
                    <label class="rate-label">결과 점수 (1~5)</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="resultRate"
                         data-initial="${form.resultRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="resultRate" value="${form.resultRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>

                <!-- 일정 점수 -->
                <div class="rate-field">
                    <label class="rate-label">일정 점수 (1~5)</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="scheduleRate"
                         data-initial="${form.scheduleRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="scheduleRate" value="${form.scheduleRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>
            </div>

            <div class="divider"></div>

            <!-- 옵션 느낌으로 변경된 필드들 -->
            <div class="form-grid">
                <!-- 건물유형: 단일 선택 -->
                <div class="field">
                    <label for="structureType">건물유형 <span class="note">(필수)</span></label>
                    <select id="structureType" name="structureType" required>
                        <option value="" disabled ${empty form.structureType ? 'selected' : ''}>선택하세요</option>
                        <option value="아파트" <c:if test="${form.structureType eq '아파트'}">selected</c:if>>아파트</option>
                        <option value="주택" <c:if test="${form.structureType eq '주택'}">selected</c:if>>주택</option>
                        <option value="오피스텔" <c:if test="${form.structureType eq '오피스텔'}">selected</c:if>>오피스텔</option>
                        <option value="원룸 / 투룸" <c:if test="${form.structureType eq '원룸 / 투룸'}">selected</c:if>>원룸 / 투룸</option>
                        <option value="빌라" <c:if test="${form.structureType eq '빌라'}">selected</c:if>>빌라</option>
                        <option value="상가 / 사무실" <c:if test="${form.structureType eq '상가 / 사무실'}">selected</c:if>>상가 / 사무실</option>
                    </select>
                </div>

                <!-- 평수: 단일 선택(범위) -->
                <div class="field">
                    <label for="areaPyeong">평수 <span class="note">(필수)</span></label>
                    <select id="areaPyeong" name="areaPyeong" required>
                        <option value="" disabled ${empty form.areaPyeong ? 'selected' : ''}>선택하세요</option>
                        <option value="10평 미만" <c:if test="${form.areaPyeong eq '10평 미만'}">selected</c:if>>10평 미만</option>
                        <option value="10~20평" <c:if test="${form.areaPyeong eq '10~20평'}">selected</c:if>>10~20평</option>
                        <option value="20~30평" <c:if test="${form.areaPyeong eq '20~30평'}">selected</c:if>>20~30평</option>
                        <option value="30~40평" <c:if test="${form.areaPyeong eq '30~40평'}">selected</c:if>>30~40평</option>
                        <option value="40평 이상" <c:if test="${form.areaPyeong eq '40평 이상'}">selected</c:if>>40평 이상</option>
                    </select>
                </div>

                <!-- 시공분야: 단일 선택 -->
                <div class="field">
                    <label for="constructionField">시공분야 <span class="note">(필수)</span></label>
                    <select id="constructionField" name="constructionField" required>
                        <option value="" disabled ${empty form.constructionField ? 'selected' : ''}>선택하세요</option>
                        <option value="주방 리모델링" <c:if test="${form.constructionField eq '주방 리모델링'}">selected</c:if>>주방 리모델링</option>
                        <option value="욕실 리모델링" <c:if test="${form.constructionField eq '욕실 리모델링'}">selected</c:if>>욕실 리모델링</option>
                        <option value="거실 인테리어" <c:if test="${form.constructionField eq '거실 인테리어'}">selected</c:if>>거실 인테리어</option>
                        <option value="발코니 확장" <c:if test="${form.constructionField eq '발코니 확장'}">selected</c:if>>발코니 확장</option>
                        <option value="도배 / 장판" <c:if test="${form.constructionField eq '도배 / 장판'}">selected</c:if>>도배 / 장판</option>
                        <option value="방 인테리어" <c:if test="${form.constructionField eq '방 인테리어'}">selected</c:if>>방 인테리어</option>
                    </select>
                </div>

                <div class="divider"></div>

                <!-- 내용 & 사진 -->
                <div class="field">
                    <label>내용 <span class="note">(필수)</span></label>
                    <textarea name="reviewContent" required>${form.reviewContent}</textarea>
                </div>

                <div class="field">
                    <label>사진 첨부 <span class="note">(여러 장 가능, 필수)</span></label>
                    <input type="file" name="files" accept="image/*" multiple required>
                </div>
            </div>

            <div class="actions">
                <button type="submit" class="btn">작성</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script>
    /* 별점 1~5: 클릭만 반영 */
    (function(){
        function paint(container, value){
            const stars = container.querySelectorAll('.star');
            stars.forEach(st => st.classList.toggle('filled', Number(st.dataset.value) <= value));
            const note = container.querySelector('.rate-note');
            if (note) note.textContent = value ? (value + '/5') : '';
            container.setAttribute('aria-valuenow', value || 0);
        }
        function commit(container, value){
            value = Math.max(1, Math.min(5, value||0));
            const hidden = container.querySelector('input[type="hidden"]');
            hidden.value = value;
            paint(container, value);
        }
        document.querySelectorAll('.stars').forEach(container=>{
            const initial = Number(container.dataset.initial || 0);
            if (initial) commit(container, initial);

            container.addEventListener('click', e=>{
                const star = e.target.closest('.star');
                if (!star) return;
                commit(container, Number(star.dataset.value));
            });

            // 키보드 접근성
            container.addEventListener('keydown', e=>{
                const hidden = container.querySelector('input[type="hidden"]');
                let val = Number(hidden.value || 0);
                if (e.key === 'ArrowRight') { commit(container, Math.min(5, (val||0)+1)); e.preventDefault(); }
                if (e.key === 'ArrowLeft')  { commit(container, Math.max(1, (val||1)-1)); e.preventDefault(); }
                if (e.key === 'Home')       { commit(container, 1);  e.preventDefault(); }
                if (e.key === 'End')        { commit(container, 5);  e.preventDefault(); }
            });

            // 호버 미리보기(클릭해야 확정)
            container.addEventListener('mousemove', e=>{
                const star = e.target.closest('.star');
                if (!star) return;
                paint(container, Number(star.dataset.value));
            });
            container.addEventListener('mouseleave', ()=>{
                const hidden = container.querySelector('input[type="hidden"]');
                paint(container, Number(hidden.value || 0));
            });
        });
    })();
</script>

</body>
</html>
