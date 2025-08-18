<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>리뷰 수정 폼</title>
    <style>
        .page-wrap {
            margin-left: 20%;
            margin-right: 20%;
            width: 55%;
        }

        .title {
            font-size: 24px;
            font-weight: 800;
            margin: 20px 0 12px;
        }

        .card {
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            background: #fff;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, .03);
        }

        .divider {
            height: 1px;
            background: #eee;
            margin: 18px 0;
        }

        .form-grid {
            display: grid;
            grid-template-columns:1fr;
            gap: 14px;
        }

        .field {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .field label {
            font-size: 14px;
            color: #374151;
            font-weight: 600;
        }

        .field textarea,
        .field input[type="file"],
        .field select {
            border: 1px solid #d1d5db;
            border-radius: 8px;
            padding: 10px;
            font-size: 14px;
            background: #fff;
            outline: none;
        }

        .field textarea {
            resize: vertical;
            min-height: 140px;
        }

        .rate-grid {
            display: grid;
            grid-template-columns: repeat(2, minmax(0, 1fr));
            gap: 12px;
        }

        .rate-field {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .rate-label {
            font-size: 14px;
            font-weight: 600;
            color: #374151;
        }

        .stars * {
            pointer-events: auto;
        }

        .stars {
            position: relative;
            display: inline-flex;
            font-size: 28px;
            line-height: 1;
            cursor: pointer;
            user-select: none;
            align-items: center;
            gap: 4px;
        }

        .stars .star {
            display: inline-block;
            width: 28px;
            height: 28px;
            margin-right: 4px;
            mask: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 .587l3.668 7.431 8.2 1.192-5.934 5.787 1.401 8.164L12 18.896l-7.335 3.865 1.401-8.164L.132 9.21l8.2-1.192L12 .587z"/></svg>') no-repeat center / contain;
            -webkit-mask: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 .587l3.668 7.431 8.2 1.192-5.934 5.787 1.401 8.164L12 18.896l-7.335 3.865 1.401-8.164L.132 9.21l8.2-1.192L12 .587z"/></svg>') no-repeat center / contain;
            background: #e5e7eb;
            transition: background .12s ease;
            cursor: pointer;
            pointer-events: auto;
        }

        .stars .star.filled {
            background: #fbbf24;
        }

        .rate-note {
            font-size: 12px;
            color: #6b7280;
            margin-left: 8px;
        }

        .actions {
            display: flex;
            justify-content: flex-end;
            gap: 8px;
            margin-top: 12px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            height: 38px;
            padding: 0 16px;
            border-radius: 10px;
            border: 1px solid transparent;
            background: #4f46e5;
            color: #fff;
            font-size: 14px;
            cursor: pointer;
            transition: .15s ease;
            text-decoration: none;
        }

        .btn:hover {
            filter: brightness(.96);
        }

        .btn.cancel {
            background: #6b7280;
        }

        .note {
            font-size: 12px;
            color: #6b7280;
        }

        @media (max-width: 980px) {
            .page-wrap {
                margin: 0 16px;
                width: auto;
            }

            .rate-grid {
                grid-template-columns:1fr;
            }
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="title">리뷰 수정</div>

    <c:if test="${not empty error}">
        <div style="color:red">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div style="color:green">${msg}</div>
    </c:if>

    <div class="card">
        <form action="/interior/myhome/${companyId}/reviews/${form.reviewId}/edit" method="post"
              enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <!-- ⭐ 별점 -->
            <div class="rate-grid">
                <c:forEach var="field" items="${['communicationRate','priceRate','resultRate','scheduleRate']}">
                    <div class="rate-field">
                        <label class="rate-label">
                            <c:choose>
                                <c:when test="${field eq 'communicationRate'}">소통 점수 (1~5)</c:when>
                                <c:when test="${field eq 'priceRate'}">가격 점수 (1~5)</c:when>
                                <c:when test="${field eq 'resultRate'}">결과 점수 (1~5)</c:when>
                                <c:when test="${field eq 'scheduleRate'}">일정 점수 (1~5)</c:when>
                            </c:choose>
                        </label>
                        <div class="stars" tabindex="0" role="slider"
                             aria-valuemin="1" aria-valuemax="5"
                             data-name="${field}"
                             data-initial="${form[field]}">
                            <span class="star" data-value="1"></span>
                            <span class="star" data-value="2"></span>
                            <span class="star" data-value="3"></span>
                            <span class="star" data-value="4"></span>
                            <span class="star" data-value="5"></span>
                            <input type="hidden" name="${field}" value="${form[field]}"/>
                            <span class="rate-note"></span>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="divider"></div>

            <div class="form-grid">
                <!-- 건물유형 -->
                <div class="field">
                    <label for="structureType">건물유형 <span class="note">(필수)</span></label>
                    <select id="structureType" name="structureType" required>
                        <option value="" disabled>선택하세요</option>
                        <option value="아파트" <c:if test="${form.structureType eq '아파트'}">selected</c:if>>아파트</option>
                        <option value="주택" <c:if test="${form.structureType eq '주택'}">selected</c:if>>주택</option>
                        <option value="오피스텔" <c:if test="${form.structureType eq '오피스텔'}">selected</c:if>>오피스텔</option>
                        <option value="원룸 / 투룸" <c:if test="${form.structureType eq '원룸 / 투룸'}">selected</c:if>>원룸 /
                            투룸
                        </option>
                        <option value="빌라" <c:if test="${form.structureType eq '빌라'}">selected</c:if>>빌라</option>
                        <option value="상가 / 사무실" <c:if test="${form.structureType eq '상가 / 사무실'}">selected</c:if>>상가 /
                            사무실
                        </option>
                    </select>
                </div>

                <!-- 평수 -->
                <div class="field">
                    <label for="areaPyeong">평수 <span class="note">(필수)</span></label>
                    <select id="areaPyeong" name="areaPyeong" required>
                        <option value="" disabled>선택하세요</option>
                        <option value="10평 미만" <c:if test="${form.areaPyeong eq '10평 미만'}">selected</c:if>>10평 미만
                        </option>
                        <option value="10~20평" <c:if test="${form.areaPyeong eq '10~20평'}">selected</c:if>>10~20평
                        </option>
                        <option value="20~30평" <c:if test="${form.areaPyeong eq '20~30평'}">selected</c:if>>20~30평
                        </option>
                        <option value="30~40평" <c:if test="${form.areaPyeong eq '30~40평'}">selected</c:if>>30~40평
                        </option>
                        <option value="40평 이상" <c:if test="${form.areaPyeong eq '40평 이상'}">selected</c:if>>40평 이상
                        </option>
                    </select>
                </div>

                <!-- 시공분야 -->
                <div class="field">
                    <label for="constructionField">시공분야 <span class="note">(필수)</span></label>
                    <select id="constructionField" name="constructionField" required>
                        <option value="" disabled>선택하세요</option>
                        <option value="주방 리모델링" <c:if test="${form.constructionField eq '주방 리모델링'}">selected</c:if>>주방
                            리모델링
                        </option>
                        <option value="욕실 리모델링" <c:if test="${form.constructionField eq '욕실 리모델링'}">selected</c:if>>욕실
                            리모델링
                        </option>
                        <option value="거실 인테리어" <c:if test="${form.constructionField eq '거실 인테리어'}">selected</c:if>>거실
                            인테리어
                        </option>
                        <option value="발코니 확장" <c:if test="${form.constructionField eq '발코니 확장'}">selected</c:if>>발코니
                            확장
                        </option>
                        <option value="도배 / 장판" <c:if test="${form.constructionField eq '도배 / 장판'}">selected</c:if>>도배 /
                            장판
                        </option>
                        <option value="방 인테리어" <c:if test="${form.constructionField eq '방 인테리어'}">selected</c:if>>방
                            인테리어
                        </option>
                    </select>
                </div>

                <div class="divider"></div>

                <!-- 내용 -->
                <div class="field">
                    <label>내용 <span class="note">(필수)</span></label>
                    <textarea name="reviewContent" required><c:out value="${fn:trim(form.reviewContent)}"/></textarea>
                </div>

                <!-- 기존 이미지 -->
                <c:if test="${not empty images}">
                    <div class="field">
                        <label>등록된 사진</label>
                        <div style="display:flex; gap:8px; flex-wrap:wrap;">
                            <c:forEach items="${images}" var="img">
                                <img src="/upload/interior_review/${img.file_name}" width="100" height="100"
                                     style="border:1px solid #ddd; border-radius:6px;">
                            </c:forEach>
                        </div>
                    </div>
                </c:if>

                <!-- 사진 첨부 -->
                <div class="field">
                    <label>사진 추가 첨부</label>
                    <input type="file" name="files" accept="image/*" multiple>
                </div>
            </div>

            <div class="actions">
                <a href="/interior/myhome/${companyId}?type=reviews&focus=${form.reviewId}" class="btn cancel">취소</a>
                <button type="submit" class="btn">수정</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script>
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

        function valueFromClick(container, evt){
            const stars = Array.from(container.querySelectorAll('.star'));
            const targetStar = evt.target.closest('.star');
            if (targetStar) return Number(targetStar.dataset.value);
            // 별 사이 공간 클릭해도 동작하도록 x좌표로 인덱스 추론
            const rect = container.getBoundingClientRect();
            const relX = Math.min(Math.max(evt.clientX - rect.left, 0), rect.width);
            const slot = Math.ceil((relX / rect.width) * 5); // 5등분
            return Math.min(Math.max(slot, 1), 5);
        }

        document.querySelectorAll('.stars').forEach(container=>{
            // 초기값 세팅
            const hidden = container.querySelector('input[type="hidden"]');
            const initial = Number(container.dataset.initial || hidden.value || 0);
            if (initial) commit(container, initial);

            // 클릭으로만 확정
            container.addEventListener('click', e=>{
                commit(container, valueFromClick(container, e));
            });

            // 키보드 접근성
            container.addEventListener('keydown', e=>{
                let val = Number(hidden.value || 0);
                if (e.key === 'ArrowRight') { commit(container, Math.min(5, (val||0)+1)); e.preventDefault(); }
                if (e.key === 'ArrowLeft')  { commit(container, Math.max(1, (val||1)-1)); e.preventDefault(); }
                if (e.key === 'Home')       { commit(container, 1);  e.preventDefault(); }
                if (e.key === 'End')        { commit(container, 5);  e.preventDefault(); }
            });

            // 호버 미리보기(클릭해야 확정)
            container.addEventListener('mousemove', e=>{
                const v = valueFromClick(container, e);
                paint(container, v);
            });
            container.addEventListener('mouseleave', ()=>{
                paint(container, Number(hidden.value || 0));
            });
        });
    })();
</script>
